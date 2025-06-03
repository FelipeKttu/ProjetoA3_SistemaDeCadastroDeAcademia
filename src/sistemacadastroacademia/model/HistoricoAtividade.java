import java.time.LocalDateTime;

public class HistoricoAtividade {
    private int id;                             // ID (INT, Chave Primária, Auto Incremento)
    private int idMembro;                       // ID_Membro (INT, NOT NULL, FK para Membros.ID)
    private Integer idFuncionarioResponsavel;   // ID_Funcionario_Responsavel (INT, NULL, FK para Funcionarios.ID)
    private String atividade;                   // Atividade (VARCHAR, NOT NULL)
    private LocalDateTime dataHoraAtividade;    // Data_Hora_Atividade (DATETIME, NOT NULL)
    private Integer tempoExecucaoMinutos;       // Tempo_Execucao_Minutos (INT, NULL)
    private String observacoes;                 // Observacoes (TEXT, NULL)

    //Construtores
    public HistoricoAtividade() {
    }
    public HistoricoAtividade(int idMembro, String atividade, LocalDateTime dataHoraAtividade) {
        this.idMembro = idMembro;
        this.atividade = atividade;
        this.dataHoraAtividade = dataHoraAtividade;
    }

    public HistoricoAtividade(int idMembro, Integer idFuncionarioResponsavel, String atividade, LocalDateTime dataHoraAtividade, Integer tempoExecucaoMinutos, String observacoes) {
        this.idMembro = idMembro;
        this.idFuncionarioResponsavel = idFuncionarioResponsavel;
        this.atividade = atividade;
        this.dataHoraAtividade = dataHoraAtividade;
        this.tempoExecucaoMinutos = tempoExecucaoMinutos;
        this.observacoes = observacoes;
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

    public Integer getIdFuncionarioResponsavel() {
        return idFuncionarioResponsavel;
    }

    public void setIdFuncionarioResponsavel(Integer idFuncionarioResponsavel) {
        this.idFuncionarioResponsavel = idFuncionarioResponsavel;
    }

    public String getAtividade() {
        return atividade;
    }

    public void setAtividade(String atividade) {
        this.atividade = atividade;
    }

    public LocalDateTime getDataHoraAtividade() {
        return dataHoraAtividade;
    }

    public void setDataHoraAtividade(LocalDateTime dataHoraAtividade) {
        this.dataHoraAtividade = dataHoraAtividade;
    }

    public Integer getTempoExecucaoMinutos() {
        return tempoExecucaoMinutos;
    }

    public void setTempoExecucaoMinutos(Integer tempoExecucaoMinutos) {
        this.tempoExecucaoMinutos = tempoExecucaoMinutos;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    // toString()
    @Override
    public String toString() {
        return "HistoricoAtividade [ID=" + id +
                ", MembroID=" + idMembro +
                ", Atividade='" + atividade + '\'' +
                ", DataHora=" + dataHoraAtividade;
    }
}