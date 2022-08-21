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
public class ConversaoDAO {
    public int inserir(Conversao c) {
        int ultimoID = 0;
        try {
            String sql = "INSERT INTO conversao (numero, sistema, dns, usuario, senha, filtro_mes, filtro_ano) VALUES(?,?,?,?,?,?,?)";
            Firebird fb = new Firebird();
            fb.connect();
            PreparedStatement pstm = Firebird.conn.prepareStatement(sql);
            pstm.setString(1, c.getNumero());
            pstm.setInt(2, c.getSistema());
            pstm.setString(3, c.getDns());
            pstm.setString(4, c.getUsuario());
            pstm.setString(5, c.getSenha());
            pstm.setString(6, c.getFiltroMes());
            pstm.setString(7, c.getFiltroAno());
            pstm.execute();
            Firebird.conn.commit();
            fb.disconnect();
            ultimoID = retornaUltimoID();
        } catch (Exception ex) {
            new TelaErro(1, ex.getStackTrace()).setVisible(true);
        }
        return ultimoID;
    }
    
    public ArrayList<Conversao> lerTodos() {
        ArrayList<Conversao> conversoes = new ArrayList<>();
        try {
            String sql = "SELECT * FROM conversao ORDER BY id ASC";
            Firebird fb = new Firebird();
            fb.connect();
            PreparedStatement pstm = Firebird.conn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            
            while (rs.next()) {
                Conversao c = new Conversao();
                c.setId(rs.getInt("id"));
                c.setNumero(rs.getString("numero"));
                c.setSistema(rs.getInt("sistema"));
                c.setDns(rs.getString("dns"));
                c.setUsuario(rs.getString("usuario"));
                c.setSenha(rs.getString("senha"));
                c.setFiltroMes(rs.getString("filtro_mes"));
                c.setFiltroAno(rs.getString("filtro_ano"));
                conversoes.add(c);
            }
            fb.disconnect();
        } catch (Exception ex) {
            new TelaErro(1, ex.getStackTrace()).setVisible(true);
        }
        return conversoes;
    }
    
    public Conversao carregarPorID(int id) {
        Conversao c = new Conversao();
        try {
            String sql = "SELECT * FROM conversao WHERE id = ?";
            Firebird fb = new Firebird();
            fb.connect();
            PreparedStatement pstm = Firebird.conn.prepareStatement(sql);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            
            if (rs.next()) {
                c.setId(rs.getInt("id"));
                c.setNumero(rs.getString("numero"));
                c.setSistema(rs.getInt("sistema"));
                c.setDns(rs.getString("dns"));
                c.setUsuario(rs.getString("usuario"));
                c.setSenha(rs.getString("senha"));
                c.setFiltroMes(rs.getString("filtro_mes"));
                c.setFiltroAno(rs.getString("filtro_ano"));
            }
            fb.disconnect();
        } catch (Exception ex) {
            new TelaErro(1, ex.getStackTrace()).setVisible(true);
        }
        return c;
    }
    
    public Conversao carregarPorNumero(String numero) {
        Conversao c = new Conversao();
        try {
            String sql = "SELECT * FROM conversao WHERE numero = ?";
            Firebird fb = new Firebird();
            fb.connect();
            PreparedStatement pstm = Firebird.conn.prepareStatement(sql);
            pstm.setString(1, numero);
            ResultSet rs = pstm.executeQuery();
            
            if (rs.next()) {
                c.setId(rs.getInt("id"));
                c.setNumero(rs.getString("numero"));
                c.setSistema(rs.getInt("sistema"));
                c.setDns(rs.getString("dns"));
                c.setUsuario(rs.getString("usuario"));
                c.setSenha(rs.getString("senha"));
                c.setFiltroMes(rs.getString("filtro_mes"));
                c.setFiltroAno(rs.getString("filtro_ano"));
            }
            fb.disconnect();
        } catch (Exception ex) {
            new TelaErro(1, ex.getStackTrace()).setVisible(true);
        }
        return c;
    }
    
    public int retornaUltimoID() {
        int generatedKey = 0;
        try {
            String sql = "SELECT MAX(id) FROM conversao "; //WHERE numero NOT IN ('99999')
            Firebird fb = new Firebird();
            fb.connect();
            PreparedStatement pstm = Firebird.conn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            rs.next();
            generatedKey = rs.getInt(1);
            fb.disconnect();
        } catch (Exception ex) {
            new TelaErro(1, ex.getStackTrace()).setVisible(true);
        }
        return generatedKey;
    }
    
    public void deletar(int idConversao) {
        try {
            String sql = "DELETE FROM conversao WHERE id = ?";
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
