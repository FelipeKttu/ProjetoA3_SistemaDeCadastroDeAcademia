package sistemacadastroacademia.model;
import java.time.LocalDate;

    public class Membro {
        private int id;                 // Corresponde à coluna 'ID'
        private String nome;            // Corresponde à coluna 'Nome' (VARCHAR)
        private String cpf;             // Corresponde à coluna 'CPF' (VARCHAR, Único)
        private String telefone;        // Corresponde à coluna 'Telefone' (VARCHAR)
        private String endereco;        // Corresponde à coluna 'Endereco' (VARCHAR)
        private LocalDate dataCadastro; // Corresponde à coluna 'Data_Cadastro' (DATE)

        /* Construtores
         * Construtor Membro
         * Necessário para criar um objeto Membro
         * e depois preencher seus atributos usando os métodos setters como aprendemos na aula.
         */

        public Membro() {
        }
        public Membro(String nome, String cpf, String telefone, String endereco, LocalDate dataCadastro) {
            this.nome = nome;
            this.cpf = cpf;
            this.telefone = telefone;
            this.endereco = endereco;
            this.dataCadastro = dataCadastro;
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

        public String getCpf() {
            return cpf;
        }

        public void setCpf(String cpf) {
            this.cpf = cpf;
        }

        public String getTelefone() {
            return telefone;
        }

        public void setTelefone(String telefone) {
            this.telefone = telefone;
        }

        public String getEndereco() {
            return endereco;
        }

        public void setEndereco(String endereco) {
            this.endereco = endereco;
        }

        public LocalDate getDataCadastro() {
            return dataCadastro;
        }

        public void setDataCadastro(LocalDate dataCadastro) {
            this.dataCadastro = dataCadastro;
    }
        public String toString() {
            return "Membro [ID=" + id + ", Nome=" + nome + ", CPF=" + cpf + "]";
        }
}
