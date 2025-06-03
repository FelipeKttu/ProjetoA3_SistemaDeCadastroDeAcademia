package sistemacadastroacademia.model;

public class Funcionario {
    private int id;             // ID (INT, Chave Primária, Auto Incremento)
    private String nome;        // Nome (VARCHAR)
    private String cargo;       // Cargo (VARCHAR)
    private String login;       // Login (VARCHAR, Único)
    private String senha;       // Senha (VARCHAR) (HASH)

    //Construtores
    public Funcionario() {
    }
    public Funcionario(String nome, String cargo, String login, String senha) {
        this.nome = nome;
        this.cargo = cargo;
        this.login = login;
        this.senha = senha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
              this.senha = senha;
    }

    // ToString()
    @Override
    public String toString() {
        return "Funcionario [ID=" + id + ", Nome=" + nome + ", Cargo=" + cargo + ", Login=" + login + "]";
    }
}