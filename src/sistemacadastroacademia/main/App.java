package sistemacadastroacademia.model;
import java.math.BigDecimal; // Para representar valores monetários com precisão
import java.time.LocalDate;  // Para datas

public class Pagamento {
    private int id;                         // ID (INT, Chave Primária, Auto Incremento)
    private int idMembro;                   // ID_Membro (INT, NOT NULL, FK para Membros.ID)
    private Integer idFuncionarioRegistro;  // ID_Funcionario_Registro (INT, NULL, FK para Funcionarios.ID)
    // Usamos Integer para permitir valor null.
    private BigDecimal valor;               // Valor (DECIMAL(10, 2), NOT NULL)
    private LocalDate dataPagamento;        // Data_Pagamento (DATE, NOT NULL)
    private String status;                  // Status (VARCHAR, NOT NULL)
    private String metodoPagamento;         // Metodo_Pagamento (VARCHAR, NULL)
    private LocalDate dataVencimento;       // Data_Vencimento (DATE, NULL)

    //Construtores
    public Pagamento() {
    }
    public Pagamento(int idMembro, BigDecimal valor, LocalDate dataPagamento, String status) {
        this.idMembro = idMembro;
        this.valor = valor;
        this.dataPagamento = dataPagamento;
        this.status = status;
    }

    public Pagamento(int idMembro, Integer idFuncionarioRegistro, BigDecimal valor, LocalDate dataPagamento, String status, String metodoPagamento, LocalDate dataVencimento) {
        this.idMembro = idMembro;
        this.idFuncionarioRegistro = idFuncionarioRegistro;
        this.valor = valor;
        this.dataPagamento = dataPagamento;
        this.status = status;
        this.metodoPagamento = metodoPagamento;
        this.dataVencimento = dataVencimento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdMembro() {
        return idMembro;
    }

    public void setIdMembro(int idMembro) {
        this.idMembro = idMembro;
    }

    public Integer getIdFuncionarioRegistro() {
        return idFuncionarioRegistro;
    }

    public void setIdFuncionarioRegistro(Integer idFuncionarioRegistro) {
        this.idFuncionarioRegistro = idFuncionarioRegistro;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    //  toString()
    @Override
    public String toString() {
        return "Pagamento [ID=" + id +
                ", MembroID=" + idMembro +
                ", Valor=R$" + valor +
                ", DataPag=" + dataPagamento +
                ", Status='" + status;
    }
}