package sistemas;

import database.PostgreSQL;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Lancamento;
import modelo.Rubrica;
import org.apache.commons.io.IOUtils;
import org.postgresql.util.PSQLException;
import util.Sistema;

/**
 *
 * @author Gabriel Moraes
 */
public class AlterData {

    String raiz = System.getProperty("user.dir");

    public ArrayList<String> empresa() throws SQLException, IOException, Exception {
        ArrayList<String> lista = new ArrayList<>();

        String sql = IOUtils.toString(new FileInputStream(raiz + "\\sql\\alterdata\\empresa.sql"), "UTF-8");
        PreparedStatement pstm = PostgreSQL.conn.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();

        while (rs.next()) {
            String cnpj = rs.getString("nrcgc");
            cnpj = cnpj.replaceAll("\\.", "");
            cnpj = cnpj.replaceAll("/", "");
            cnpj = cnpj.replaceAll("-", "");
            lista.add(cnpj);
        }

        return lista;
    }

    public ArrayList<Lancamento> movimento(String ano, String mes) throws SQLException, IOException, Exception {
        ArrayList<Lancamento> lista = new ArrayList<>();

        Sistema s = new Sistema();
        String sql = IOUtils.toString(new FileInputStream(raiz + "\\sql\\alterdata\\empresa.sql"), "UTF-8");

        PreparedStatement pstmP = PostgreSQL.conn.prepareStatement(sql);
        ResultSet rsP = pstmP.executeQuery();

        while (rsP.next()) {
            String codEmpresa = s.completeToLeft(rsP.getString("cdempresa"), '0', 5);
            String cnpj = rsP.getString("nrcgc");
            cnpj = cnpj.replaceAll("\\.", "");
            cnpj = cnpj.replaceAll("/", "");
            cnpj = cnpj.replaceAll("-", "");
            
            sql = "select (select '"+cnpj+"' as cnpj),"
                    + "   (select f.cdchamada as cod_empregado "
                    + "      from wdp.f"+codEmpresa+" f "
                    + "     where r.idfuncionario = f.idfuncionario), "
                    + "   (select e.cdchamada as cod_rubrica "
                    + "      from wdp.evento e "
                    + "     where r.idevento = e.idevento), "
                    + "dtfinal, "
                    + "vlevento, "
                    + "qtevento, "
                    + "nmevento, "
                    + "sttipoevento "
                    + "tpaviso "
                    + "from wdp.r"+codEmpresa+" r\n"
                + " where r.tpprocesso = 'M' and r.stunidade  = 'M' and r.dtfinal > '"+ano+"-"+mes+"-01'";
            
            PreparedStatement pstm = PostgreSQL.conn.prepareStatement(sql);
            try {
                ResultSet rs = pstm.executeQuery();
            
                while (rs.next()) {
                    Lancamento la = new Lancamento();
                    la.setCnpj(rs.getString("cnpj"));
                    la.setCodEmpregado(rs.getString("cod_empregado"));
                    String dataCompleta = rs.getString("dtfinal");
                    la.setCompetencia(dataCompleta.substring(5, 7) + "/" + dataCompleta.substring(0, 4));
                    la.setCodRubrica(rs.getString("cod_rubrica"));
                    
                    String referencia = rs.getString("qtevento");
                    String valor = rs.getString("vlevento");
                    
                    //Se a referencia for igual ao valor, não será salvo a referencia
                    if(referencia.equals(valor)) {
                        la.setValor(valor);
                    } else {
                        if(referencia.contains(",")) {
                            la.setValor(valor);
                        } else {
                            if(referencia.contains(".")) {
                                la.setReferencia(referencia);
                                la.setValor(valor);
                            } else {
                                la.setReferencia(AlterData.converterMinRef(Integer.valueOf(referencia)));
                                la.setValor(valor);
                            }
                        }
                    }
                    lista.add(la);
                }
            } catch (PSQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

        return lista;
    }

    public ArrayList<Rubrica> rubrica() throws SQLException, IOException, Exception {
        ArrayList<Rubrica> lista = new ArrayList<>();

        String sql = IOUtils.toString(new FileInputStream(raiz + "\\sql\\alterdata\\rubrica.sql"), "UTF-8");
        PreparedStatement pstm = PostgreSQL.conn.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();

        while (rs.next()) {
            Rubrica r = new Rubrica();
            r.setCnpj(rs.getString("cnpj"));
            r.setCodigo(rs.getString("cdchamada"));
            r.setDescricao(rs.getString("nmevento")/* + " " + rs.getString("nmevento")*/);
            r.setNatureza(rs.getString("cdcampotrct"));
            switch (rs.getString("sttipo")) {
                case "A": //1 - Desconto
                    r.setTipo("1");
                    break;
                case "D": //2 - Desconto
                    r.setTipo("2");
                    break;
                default:
                    r.setTipo("99");
                    break;
            }
            r.setPercentual(rs.getInt("vlpercentualtrct"));
            lista.add(r);
        }

        return lista;
    }
    
    public static String converterMinRef(int minutos) {
        Double ref = 0.0;
        int repeticao = minutos / 60;
        
        for(int i=0; i <= repeticao; i++) {
            if(minutos >= 60) {
                minutos = minutos - 60;
                ref++;
            } else {
                Double test = Double.valueOf(minutos)/Double.valueOf(60);
                BigDecimal bd = new BigDecimal(test).setScale(2, RoundingMode.HALF_UP);
                ref = ref + bd.doubleValue();
            }
        }
        
        return String.valueOf(ref);
    }

}
