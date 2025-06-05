package sistemacadastroacademia.controller;
import sistemacadastroacademia.model.Funcionario;
import sistemacadastroacademia.util.Database;
import org.mindrot.jbcrypt.BCrypt; // Importa a biblioteca jBCrypt

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FuncionarioController {


    //Adiciona um novo funcionário ao banco de dados, com a senha devidamente hasheada.
    public boolean adicionarFuncionario(Funcionario funcionario) {
        // SQL para inserir, note que a coluna 'Senha' receberá o hash.
        String sql = "INSERT INTO Funcionarios (Nome, Cargo, Login, Senha) VALUES (?, ?, ?, ?)";

        // O salt é um valor aleatório que torna o hash mais seguro.
        // BCrypt.gensalt() gera um salt com uma força de trabalho padrão.
        String salt = BCrypt.gensalt();
        String senhaHasheada = BCrypt.hashpw(funcionario.getSenha(), salt);

        Connection conn = null;

        try {
            conn = Database.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                stmt.setString(1, funcionario.getNome());
                stmt.setString(2, funcionario.getCargo());
                stmt.setString(3, funcionario.getLogin());
                stmt.setString(4, senhaHasheada); // Salva a SENHA Hash no banco

                int affectedRows = stmt.executeUpdate();

                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            funcionario.setId(generatedKeys.getInt(1));
                            System.out.println("FuncionarioController: Funcionário adicionado com sucesso! ID: " + funcionario.getId());
                        }
                    }
                    return true;
                } else {
                    System.err.println("FuncionarioController: Nenhuma linha afetada ao adicionar funcionário.");
                    return false;
                }
            }
        } catch (SQLException e) {
            System.err.println("FuncionarioController - ERRO ao adicionar funcionário: " + e.getMessage());
            if (e.getSQLState() != null && e.getSQLState().equals("23000")) { // Código de erro para violação de constraint (ex: login duplicado)
                System.err.println("FuncionarioController: Possível duplicidade de login (" + funcionario.getLogin() + ").");
            }
            e.printStackTrace();
            return false;
        }
    }


    //Busca um funcionário no banco de dados pelo seu login e mostra as info
    public Funcionario buscarFuncionarioPorLogin(String login) {
        String sql = "SELECT * FROM Funcionarios WHERE Login = ?";
        Funcionario funcionarioEncontrado = null;
        Connection conn = null;

        try {
            conn = Database.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, login);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        funcionarioEncontrado = new Funcionario();
                        funcionarioEncontrado.setId(rs.getInt("ID"));
                        funcionarioEncontrado.setNome(rs.getString("Nome"));
                        funcionarioEncontrado.setCargo(rs.getString("Cargo"));
                        funcionarioEncontrado.setLogin(rs.getString("Login"));
                        funcionarioEncontrado.setSenha(rs.getString("Senha")); // Pega o HASH da senha do banco
                        System.out.println("FuncionarioController: Funcionário encontrado por login: " + funcionarioEncontrado.getLogin());
                    } else {
                        System.out.println("FuncionarioController: Nenhum funcionário encontrado com o login: " + login);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("FuncionarioController - ERRO ao buscar funcionário por login (" + login + "): " + e.getMessage());
            e.printStackTrace();
        }
        return funcionarioEncontrado;
    }

}