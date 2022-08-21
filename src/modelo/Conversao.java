package modelo;

/**
 *
 * @author Gabriel Moraes
 */
public class Conversao {
    private int id;
    private String numero;
    private int sistema;
    private String dns;
    private String usuario;
    private String senha;
    private String filtroMes;
    private String filtroAno;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getSistema() {
        return sistema;
    }

    public void setSistema(int sistema) {
        this.sistema = sistema;
    }

    public String getDns() {
        return dns;
    }

    public void setDns(String dns) {
        this.dns = dns;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getFiltroMes() {
        return filtroMes;
    }

    public void setFiltroMes(String filtroMes) {
        this.filtroMes = filtroMes;
    }

    public String getFiltroAno() {
        return filtroAno;
    }

    public void setFiltroAno(String filtroAno) {
        this.filtroAno = filtroAno;
    }
}
