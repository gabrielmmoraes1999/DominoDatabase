package modelo;

/**
 *
 * @author Gabriel Moraes
 */
public class RubricaRef {
    private int id;
    private Conversao conversao;
    private String cnpj;
    private String codConcorrente;
    private String codDominio;
    private String tipo;

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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCodConcorrente() {
        return codConcorrente;
    }

    public void setCodConcorrente(String codConcorrente) {
        this.codConcorrente = codConcorrente;
    }

    public String getCodDominio() {
        return codDominio;
    }

    public void setCodDominio(String codDominio) {
        this.codDominio = codDominio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
