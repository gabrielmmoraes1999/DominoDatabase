package modelo;

/**
 *
 * @author Gabriel Moraes
 */
public class Lancamento {
    private int id;
    private Conversao conversao;
    private String cnpj;
    private String codEmpregado;
    private String competencia;
    private String codRubrica;
    private String referencia;
    private String referenciaInfo;
    private String valor;
    private String valorInfo;

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

    public String getCodEmpregado() {
        return codEmpregado;
    }

    public void setCodEmpregado(String codEmpregado) {
        this.codEmpregado = codEmpregado;
    }

    public String getCompetencia() {
        return competencia;
    }

    public void setCompetencia(String competencia) {
        this.competencia = competencia;
    }

    public String getCodRubrica() {
        return codRubrica;
    }

    public void setCodRubrica(String codRubrica) {
        this.codRubrica = codRubrica;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getReferenciaInfo() {
        return referenciaInfo;
    }

    public void setReferenciaInfo(String referenciaInfo) {
        this.referenciaInfo = referenciaInfo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getValorInfo() {
        return valorInfo;
    }

    public void setValorInfo(String valorInfo) {
        this.valorInfo = valorInfo;
    }
}
