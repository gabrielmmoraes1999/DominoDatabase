package util;

import database.Firebird;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import modelo.Conversao;
import modelo.EmpresaDAO;
import modelo.Rubrica;
import modelo.RubricaConfiguracao;
import modelo.RubricaDAO;
import modelo.RubricaRef;

/**
 *
 * @author Gabriel Moraes
 */
public class Rubricas {
    
    public ArrayList<RubricaRef> returno(int idConversao, ArrayList<String> listaR) {
        ArrayList<RubricaRef> lista = new ArrayList<>();
        try {
            String in = "";
            for(String r : listaR) {
                if(listaR.indexOf(r) != 0) {
                    in = in+", "+"'"+r+"'";
                } else {
                    in = in+"'"+r+"'";
                }
            }
            String sql = "SELECT DISTINCT (SELECT COD_EMPRESA_EVENTO\n" +
"				   FROM EMPRESA\n" +
"				  WHERE ID_CONVERSAO = ? AND LANCAMENTO.CNPJ = CNPJ) COD_EMPRESA,\n" +
"				COD_RUBRICA,\n" +
"				(SELECT DISTINCT COD_DOMINIO\n" +
"							FROM RUBRICA_REF\n" +
"						   WHERE ID_CONVERSAO = ? AND COD_CONCORRENTE = LANCAMENTO.COD_RUBRICA) COD_DOMINIO\n" +
"		   FROM LANCAMENTO\n" +
"		  WHERE ID_CONVERSAO = ? AND COD_RUBRICA IN ("+in+")\n" +
"	   ORDER BY COD_EMPRESA, COD_RUBRICA";
            Firebird fb = new Firebird();
            fb.connect();
            PreparedStatement pstm = Firebird.conn.prepareStatement(sql);
            pstm.setInt(1, idConversao);
            pstm.setInt(2, idConversao);
            pstm.setInt(3, idConversao);
            ResultSet rs = pstm.executeQuery();
            
            while (rs.next()) {
                RubricaRef rr = new RubricaRef();
                rr.setCnpj(rs.getString("COD_EMPRESA"));
                rr.setCodConcorrente(rs.getString("COD_RUBRICA"));
                rr.setCodDominio(rs.getString("COD_DOMINIO"));
                rr.setTipo("Criar");
                lista.add(rr);
            }
            fb.disconnect();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return lista;
    }
    
    public ArrayList<RubricaConfiguracao> gerar(Conversao c,  ArrayList<RubricaRef> listaRR) {
        ArrayList<RubricaConfiguracao> lista = new ArrayList<>();
        
        EmpresaDAO eDAO = new EmpresaDAO();
        RubricaDAO rDAO = new RubricaDAO();
        
        //Selecionar o sistema da conversão
        switch(c.getSistema()) {
            case 1:
                //Nasajon
                break;
            case 2:
                //MasterMaq
                //Ler tabela de Referencia da Rubrica
                for(RubricaRef rr : listaRR) {
                    if("Criar".equals(rr.getTipo())) {
                        Rubrica r = rDAO.carregarPorConversaoEmpresaCodigo(null, rr.getCodConcorrente(), c.getId());
                        //1 - Provento || 2 - Desconto || 3 - Informação
                        if("1".equals(r.getTipo())) {
                            RubricaConfiguracao rc = new RubricaConfiguracao();
                            rc.setCodigo(rr.getCodDominio());
                            rc.setCodEmpresa(Integer.valueOf(rr.getCnpj()));//CNPJ é o codigo da empresa
                            rc.setDescricao(r.getDescricao());
                            rc.setInicioData(eDAO.retornaData(Integer.valueOf(rr.getCnpj()), c.getId()));
                            rc.setSituacao(true);
                            rc.setSituacaoData(null);
                            rc.setTipo(1);
                            rc.setAvisoPrevio(false);
                            rc.setSalarioFerias(false);
                            rc.setLicencaPremio(false);
                            
                            switch(r.getNatureza()) {
                                case "3"://Configuração Horas Extras
                                    rc.setUnidade("H");
                                    rc.setPercentual(r.getPercentual());
                                    rc.setAvisoPrevioMedias(true);
                                    rc.setSalarioMedias(true);
                                    rc.setFeriasMedias(true);
                                    rc.setLicencaPremioMedias(false);
                                    rc.setSaldoSalarioMedias(true);
                                    rc.setHoras(true);
                                    break;
                                case "16"://Configuração Adicional Norturno
                                    rc.setUnidade("H");
                                    rc.setPercentual(r.getPercentual());
                                    rc.setAvisoPrevioMedias(true);
                                    rc.setSalarioMedias(true);
                                    rc.setFeriasMedias(true);
                                    rc.setLicencaPremioMedias(false);
                                    rc.setSaldoSalarioMedias(true);
                                    rc.setHoras(true);
                                    break;
                                case "95"://Configuração Horas Extras
                                    rc.setUnidade("H");
                                    rc.setPercentual(r.getPercentual());
                                    rc.setAvisoPrevioMedias(true);
                                    rc.setSalarioMedias(true);
                                    rc.setFeriasMedias(true);
                                    rc.setLicencaPremioMedias(false);
                                    rc.setSaldoSalarioMedias(true);
                                    rc.setHoras(true);
                                    break;
                                default:
                                    rc.setUnidade("V");
                                    rc.setAvisoPrevioMedias(false);
                                    rc.setSalarioMedias(false);
                                    rc.setFeriasMedias(false);
                                    rc.setLicencaPremioMedias(false);
                                    rc.setSaldoSalarioMedias(false);
                                    rc.setHoras(false);
                                    break;
                            }
                            
                            lista.add(rc);
                        } else if("2".equals(r.getTipo())) { //Desconto
                            RubricaConfiguracao rc = new RubricaConfiguracao();
                            rc.setCodigo(rr.getCodDominio());
                            rc.setCodEmpresa(Integer.valueOf(rr.getCnpj()));
                            rc.setDescricao(r.getDescricao());
                            rc.setInicioData(eDAO.retornaData(Integer.valueOf(rr.getCnpj()), c.getId()));
                            rc.setSituacao(true);
                            rc.setSituacaoData(null);
                            rc.setTipo(1);
                            rc.setAvisoPrevio(false);
                            rc.setSalarioFerias(false);
                            rc.setLicencaPremio(false);
                            
                            //Medias
                            rc.setAvisoPrevioMedias(false);
                            rc.setSalarioMedias(false);
                            rc.setFeriasMedias(false);
                            rc.setLicencaPremioMedias(false);
                            rc.setSaldoSalarioMedias(false);
                            rc.setHoras(false);
                            lista.add(rc);
                        }
                    }
                }
                break;
            case 3:
                //SCI
                break;
        }
        
        return lista;
    }
    
}
