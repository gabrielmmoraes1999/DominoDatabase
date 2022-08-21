package sistemas;

import database.MSSQLServer;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Lancamento;
import modelo.Rubrica;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Gabriel Moraes
 */
public class MasterMaq {

    String raiz = System.getProperty("user.dir");
    
    public ArrayList<String> empresa(String filtroAno) throws SQLException, IOException {
        ArrayList<String> lista = new ArrayList<>();
        MSSQLServer ms = new MSSQLServer();
        ms.connect();

        String sql = IOUtils.toString(new FileInputStream(raiz + "\\sql\\mastermaq\\empresa.sql"), "UTF-8");

        PreparedStatement pstm = MSSQLServer.conn.prepareStatement(sql);
        
        if(filtroAno != null) {
            pstm.setString(1, filtroAno);
        } else {
            pstm.setString(1, "1900");
        }
        
        ResultSet rs = pstm.executeQuery();

        while (rs.next()) {
            lista.add(rs.getString("cnpj"));
        }

        ms.disconnect();
        return lista;
    }

    public ArrayList<Lancamento> movimento(String filtroAno) throws SQLException, IOException {
        ArrayList<Lancamento> lista = new ArrayList<>();
        MSSQLServer ms = new MSSQLServer();
        ms.connect();

        String sql = IOUtils.toString(new FileInputStream(raiz + "\\sql\\mastermaq\\movimento.sql"), "UTF-8");

        PreparedStatement pstm = MSSQLServer.conn.prepareStatement(sql);
        
        if(filtroAno != null) {
            pstm.setString(1, filtroAno);
        } else {
            pstm.setString(1, "1900");
        }
        
        ResultSet rs = pstm.executeQuery();

        while (rs.next()) {
            Lancamento la = new Lancamento();
            la.setCnpj(rs.getString("cnpj"));
            la.setCodEmpregado(rs.getString("cod_empregado"));
            la.setCompetencia(rs.getString("mes") + "/" + rs.getString("ano"));
            la.setCodRubrica(rs.getString("cod_rubrica"));
            la.setReferencia(rs.getString("referencia"));
            la.setReferenciaInfo(rs.getString("referencia_info"));
            la.setValor(rs.getString("valor"));
            la.setValorInfo(rs.getString("valor_info"));
            lista.add(la);
        }

        ms.disconnect();
        return lista;
    }
    
    public ArrayList<Rubrica> rubrica(String filtroAno) throws SQLException, IOException {
        ArrayList<Rubrica> lista = new ArrayList<>();
        MSSQLServer ms = new MSSQLServer();
        ms.connect();

        String sql = IOUtils.toString(new FileInputStream(raiz + "\\sql\\mastermaq\\rubrica.sql"), "UTF-8");

        PreparedStatement pstm = MSSQLServer.conn.prepareStatement(sql);
        
        if(filtroAno != null) {
            pstm.setString(1, filtroAno);
        } else {
            pstm.setString(1, "1900");
        }
        
        ResultSet rs = pstm.executeQuery();

        while (rs.next()) {
            Rubrica r = new Rubrica();
            r.setCnpj(rs.getString("cnpj"));
            r.setCodigo(rs.getString("codigo"));
            r.setDescricao(rs.getString("descricaorubricaesocial"));
            r.setNatureza(rs.getString("idnaturezarubrica"));
            r.setTipo(rs.getString("idtiporubrica"));
            r.setPercentual(rs.getInt("percentual"));
            lista.add(r);
        }

        ms.disconnect();
        return lista;
    }
}
