package sistemacadastroacademia.model;

import java.time.LocalDate; // Para a data de início do treino

public class Treino {

    // --- ATRIBUTOS ---
    // Correspondem às colunas da tabela 'Treinos'.
    private int id;                         // ID (INT, Chave Primária, Auto Incremento)
    private int idMembro;                   // ID_Membro (INT, NOT NULL, FK para Membros.ID)
    private Integer idFuncionarioInstrutor; // ID_Funcionario_Instrutor (INT, NULL, FK para Funcionarios.ID)
    // Usamos Integer (classe Wrapper) para permitir valor null,
    // já que no banco essa coluna pode ser NULA.
    private String tipo;                    // Tipo (VARCHAR, NOT NULL) - Ex: "Musculação A", "Cardio", "Funcional"
    private String descricao;               // Descricao (TEXT) - Descrição mais detalhada do treino
    private int duracaoMinutos;             // Duracao_Minutos (INT) - Duração total estimada em minutos
    private LocalDate dataInicio;           // Data_Inicio (DATE, NOT NULL)

    //Construtores
    public Treino() {
    }

     // Construtor para criar um novo treino (sem o ID, que é gerado pelo banco)
    public Treino(int idMembro, String tipo, LocalDate dataInicio, int duracaoMinutos) {
        this.idMembro = idMembro;
        this.tipo = tipo;
        this.dataInicio = dataInicio;
        this.duracaoMinutos = duracaoMinutos;
    }

    public Treino(int idMembro, Integer idFuncionarioInstrutor, String tipo, String descricao, int duracaoMinutos, LocalDate dataInicio) {
        this.idMembro = idMembro;
        this.idFuncionarioInstrutor = idFuncionarioInstrutor;
        this.tipo = tipo;
        this.descricao = descricao;
        this.duracaoMinutos = duracaoMinutos;
        this.dataInicio = dataInicio;
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

    public Integer getIdFuncionarioInstrutor() {
        return idFuncionarioInstrutor;
    }

    public void setIdFuncionarioInstrutor(Integer idFuncionarioInstrutor) {
        this.idFuncionarioInstrutor = idFuncionarioInstrutor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public void setDuracaoMinutos(int duracaoMinutos) {
        this.duracaoMinutos = duracaoMinutos;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    // toString() ---
    @Override
    public String toString() {
        return "Treino [ID=" + id +
                ", MembroID=" + idMembro +
                ", Início=" + dataInicio +
                ", Duração=" + duracaoMinutos + " min]";
    }
}