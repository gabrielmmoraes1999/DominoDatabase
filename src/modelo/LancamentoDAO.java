package modelo;

import database.Firebird;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import view.TelaErro;

/**
 *
 * @author Gabriel Moraes
 */
public class LancamentoDAO {
    public void inserir(ArrayList<Lancamento> listaLancamento, int idConversao) {
        try {
            String sql = "INSERT INTO LANCAMENTO (ID_CONVERSAO, CNPJ, COD_EMPREGADO, COMPETENCIA, COD_RUBRICA, REFERENCIA, REFERENCIA_INFO, VALOR, VALOR_INFO) VALUES(?,?,?,?,?,?,?,?,?)";
            Firebird fb = new Firebird();
            fb.connect();
            PreparedStatement pstm = Firebird.conn.prepareStatement(sql);
            
            for(Lancamento l : listaLancamento) {
                pstm.setInt(1, idConversao);
                pstm.setString(2, l.getCnpj());
                pstm.setString(3, l.getCodEmpregado());
                pstm.setString(4, l.getCompetencia());
                pstm.setString(5, l.getCodRubrica());
                pstm.setString(6, l.getReferencia());
                pstm.setString(7, l.getReferenciaInfo());
                pstm.setString(8, l.getValor());
                pstm.setString(9, l.getValorInfo());
                pstm.execute();
            }
            
            Firebird.conn.commit();
            fb.disconnect();
        } catch (Exception ex) {
            new TelaErro(1, ex.getStackTrace()).setVisible(true);
        }
    }
    
    public ArrayList<Lancamento> carregarPorConversao(int idConversao) {
        ArrayList<Lancamento> lista = new ArrayList<>();
        try {
            String sql = "SELECT * FROM LANCAMENTO WHERE ID_CONVERSAO = ? ORDER BY ID ASC";
            Firebird fb = new Firebird();
            fb.connect();
            PreparedStatement pstm = Firebird.conn.prepareStatement(sql);
            pstm.setInt(1, idConversao);
            ResultSet rs = pstm.executeQuery();
            
            while (rs.next()) {
                Lancamento l = new Lancamento();
                l.setId(rs.getInt("ID"));
                l.setCnpj(rs.getString("CNPJ"));
                l.setCodEmpregado(rs.getString("COD_EMPREGADO"));
                l.setCompetencia(rs.getString("COMPETENCIA"));
                l.setCodRubrica(rs.getString("COD_RUBRICA"));
                l.setReferencia(rs.getString("REFERENCIA"));
                l.setReferenciaInfo(rs.getString("REFERENCIA_INFO"));
                l.setValor(rs.getString("VALOR"));
                l.setValorInfo(rs.getString("VALOR_INFO"));
                lista.add(l);
            }
            fb.disconnect();
        } catch (Exception ex) {
            new TelaErro(1, ex.getStackTrace()).setVisible(true);
        }
        return lista;
    }
    
    public ArrayList<LayoutDominio> retornaLayoutDominio(int idConversao) {
        ArrayList<LayoutDominio> lista = new ArrayList<>();
        try {
            String sql = ""
                    + "SELECT (SELECT CODIGO FROM EMPRESA WHERE LANCAMENTO.CNPJ = CNPJ AND ID_CONVERSAO = ?) CODIGO,"
                    + "COD_EMPREGADO,"
                    + "COMPETENCIA,"
                    + "(SELECT COD_DOMINIO "
                                + "FROM RUBRICA_REF "
                               + "WHERE CNPJ IS NULL AND ID_CONVERSAO = ? AND COD_CONCORRENTE = LANCAMENTO.COD_RUBRICA) COD_RUBRICA,"
                    + "(SELECT UNIDADE "
                       + "FROM RUBRICA_CONFIGURACAO "
                      + "WHERE ID_CONVERSAO = 19 AND CODIGO = (SELECT COD_DOMINIO "
                                                              + "FROM RUBRICA_REF "
                                                             + "WHERE CNPJ IS NULL AND ID_CONVERSAO = 19 AND COD_CONCORRENTE = LANCAMENTO.COD_RUBRICA)) UNIDADE,"
                    + "REFERENCIA,"
                    + "REFERENCIA_INFO,"
                    + "VALOR,"
                    + "VALOR_INFO "
                    + "FROM LANCAMENTO "
                    + "WHERE ID_CONVERSAO = ? AND COD_RUBRICA IN (SELECT DISTINCT COD_CONCORRENTE "
                                                                          + "FROM RUBRICA_REF "
                                                                         + "WHERE ID_CONVERSAO = ?)";
            Firebird fb = new Firebird();
            fb.connect();
            PreparedStatement pstm = Firebird.conn.prepareStatement(sql);
            pstm.setInt(1, idConversao);
            pstm.setInt(2, idConversao);
            pstm.setInt(3, idConversao);
            pstm.setInt(4, idConversao);
            ResultSet rs = pstm.executeQuery();
            
            while (rs.next()) {
                LayoutDominio ld = new LayoutDominio();
                ld.setCodEmpregado(Integer.valueOf(rs.getString("COD_EMPREGADO")));
                ld.setCompetencia(rs.getString("COMPETENCIA"));
                ld.setCodRubrica(rs.getString("COD_RUBRICA"));
                
                String unidade = rs.getString("UNIDADE");
                
                if(unidade == null) {
                    //Lista de Rubricas
                    ArrayList<String> rubricaHoras = new ArrayList<>();
                    rubricaHoras.add("25");
                    rubricaHoras.add("150");
                    rubricaHoras.add("160");
                    rubricaHoras.add("270");
                    rubricaHoras.add("200");
                    
                    if(rubricaHoras.contains(ld.getCodRubrica())) {
                        unidade = "H";
                    } else {
                        unidade = "V";
                    }
                }
                
                switch(unidade) {
                    case "H":
                        if(!"0.0".equals(rs.getString("REFERENCIA"))) {
                            ld.setValor(rs.getString("REFERENCIA"));
                        } else if(!"0.0".equals(rs.getString("REFERENCIA_INFO"))) {
                            ld.setValor(rs.getString("REFERENCIA_INFO"));
                        } else {
                            ld.setValor("0.0");
                        }
                        break;
                    case "V":
                        String valor = rs.getString("VALOR");
                        String valorInfo = rs.getString("VALOR_INFO");
                        
                        if(Double.valueOf(valor) > Double.valueOf(valorInfo)) {
                            ld.setValor(valor);
                        } else {
                            ld.setValor(valorInfo);
                        }
                        break;
                    default:
                        String valorD = rs.getString("VALOR");
                        String valorInfoD = rs.getString("VALOR_INFO");
                        
                        if(!"0.0".equals(rs.getString("REFERENCIA"))) {
                            ld.setValor(rs.getString("REFERENCIA"));
                        } else if(!"0.0".equals(rs.getString("REFERENCIA_INFO"))) {
                            ld.setValor(rs.getString("REFERENCIA_INFO"));
                        } else if(Double.valueOf(valorD) > Double.valueOf(valorInfoD)) {
                            ld.setValor(valorD);
                        } else if(Double.valueOf(valorD) < Double.valueOf(valorInfoD)) {
                            ld.setValor(valorInfoD);
                        } else {
                            ld.setValor("0.0");
                        }
                        break;
                }
                ld.setCodEmpresa(rs.getInt("CODIGO"));
                
                lista.add(ld);
            }
            fb.disconnect();
        } catch (Exception ex) {
            new TelaErro(1, ex.getStackTrace()).setVisible(true);
        }
        return lista;
    }
    
    public int retornaQuantidadeLancamentoPorConversaoPorEmpresa(int idConversao, int codEmpresa) {
        int total = 0;
        try {
            String sql = "SELECT COUNT(*) FROM LANCAMENTO WHERE ID_CONVERSAO = ? AND COD_EMPRESA = ?";
            Firebird fb = new Firebird();
            fb.connect();
            PreparedStatement pstm = Firebird.conn.prepareStatement(sql);
            pstm.setInt(1, idConversao);
            pstm.setInt(2, codEmpresa);
            ResultSet rs = pstm.executeQuery();
            
            if (rs.next()) {
                total = rs.getInt("COUNT");
            }
            fb.disconnect();
        } catch (Exception ex) {
            new TelaErro(1, ex.getStackTrace()).setVisible(true);
        }
        return total;
    }
    
    public void deletar(int idConversao) {
        try {
            String sql = "DELETE FROM LANCAMENTO WHERE ID_CONVERSAO = ?";
            Firebird fb = new Firebird();
            fb.connect();
            PreparedStatement pstm = Firebird.conn.prepareStatement(sql);
            pstm.setInt(1, idConversao);
            pstm.execute();
            Firebird.conn.commit();
            fb.disconnect();
        } catch (Exception ex) {
            new TelaErro(1, ex.getStackTrace()).setVisible(true);
        }
    }
}
