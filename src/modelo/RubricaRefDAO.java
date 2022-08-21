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
public class RubricaRefDAO {
    public void inserir(ArrayList<RubricaRef> lista, int idConversao) {
        try {
            String sql = "INSERT INTO RUBRICA_REF (ID_CONVERSAO, CNPJ, COD_CONCORRENTE, COD_DOMINIO, TIPO) VALUES(?,?,?,?,?)";
            Firebird fb = new Firebird();
            fb.connect();
            PreparedStatement pstm = Firebird.conn.prepareStatement(sql);
            
            for(RubricaRef rr : lista) {
                pstm.setInt(1, idConversao);
                pstm.setString(2, rr.getCnpj());
                pstm.setString(3, rr.getCodConcorrente());
                pstm.setString(4, rr.getCodDominio());
                pstm.setString(5, rr.getTipo());
                pstm.execute();
            }
            
            Firebird.conn.commit();
            fb.disconnect();
        } catch (Exception ex) {
            new TelaErro(1, ex.getStackTrace()).setVisible(true);
        }
    }
    
    public ArrayList<RubricaRef> carregarPorConversao(int idConversao) {
        ArrayList<RubricaRef> lista = new ArrayList<>();
        try {
            String sql = "SELECT * FROM RUBRICA_REF WHERE ID_CONVERSAO = ? ORDER BY ID ASC";
            Firebird fb = new Firebird();
            fb.connect();
            PreparedStatement pstm = Firebird.conn.prepareStatement(sql);
            pstm.setInt(1, idConversao);
            ResultSet rs = pstm.executeQuery();
            
            while (rs.next()) {
                RubricaRef rr = new RubricaRef();
                rr.setId(rs.getInt("ID"));
                rr.setCnpj(rs.getString("CNPJ"));
                rr.setCodConcorrente(rs.getString("COD_CONCORRENTE"));
                rr.setCodDominio(rs.getString("COD_DOMINIO"));
                rr.setTipo(rs.getString("TIPO"));
                lista.add(rr);
            }
            fb.disconnect();
        } catch (Exception ex) {
            new TelaErro(1, ex.getStackTrace()).setVisible(true);
        }
        return lista;
    }
    
    public ArrayList<RubricaRef> carregarPorConversaoComFiltroCriar(int idConversao, String tipo) {
        ArrayList<RubricaRef> lista = new ArrayList<>();
        try {
            String sql = "SELECT * FROM RUBRICA_REF WHERE ID_CONVERSAO = ? AND TIPO = ? ORDER BY ID ASC";
            Firebird fb = new Firebird();
            fb.connect();
            PreparedStatement pstm = Firebird.conn.prepareStatement(sql);
            pstm.setInt(1, idConversao);
            pstm.setString(2, tipo);
            ResultSet rs = pstm.executeQuery();
            
            while (rs.next()) {
                RubricaRef rr = new RubricaRef();
                rr.setId(rs.getInt("ID"));
                rr.setCnpj(rs.getString("CNPJ"));
                rr.setCodConcorrente(rs.getString("COD_CONCORRENTE"));
                rr.setCodDominio(rs.getString("COD_DOMINIO"));
                rr.setTipo(rs.getString("TIPO"));
                lista.add(rr);
            }
            fb.disconnect();
        } catch (Exception ex) {
            new TelaErro(1, ex.getStackTrace()).setVisible(true);
        }
        return lista;
    }
    
    public void deletar(int idConversao) {
        try {
            String sql = "DELETE FROM RUBRICA_REF WHERE ID_CONVERSAO = ?";
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
