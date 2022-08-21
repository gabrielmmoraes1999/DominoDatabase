package modelo;

import java.sql.Date;

/**
 *
 * @author Gabriel Moraes
 */
public class Empresa {
    private int id;
    private Conversao conversao;
    private int codigo;
    private String razaoSocial;
    private String cnpj;
    private Date inicioDasAtividades;
    private int codEmpresaEvento;
    private Date dataEmpresaEvento;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Conversao getConversao() {
        return conversao;
    }

    public void setConversao(Conversao conversao) {
        this.conversao = conversao;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public Date getInicioDasAtividades() {
        return inicioDasAtividades;
    }

    public void setInicioDasAtividades(Date inicioDasAtividades) {
        this.inicioDasAtividades = inicioDasAtividades;
    }

    public int getCodEmpresaEvento() {
        return codEmpresaEvento;
    }

    public void setCodEmpresaEvento(int codEmpresaEvento) {
        this.codEmpresaEvento = codEmpresaEvento;
    }

    public Date getDataEmpresaEvento() {
        return dataEmpresaEvento;
    }

    public void setDataEmpresaEvento(Date dataEmpresaEvento) {
        this.dataEmpresaEvento = dataEmpresaEvento;
    }
}
