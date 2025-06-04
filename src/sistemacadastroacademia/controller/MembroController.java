package sistemacadastroacademia.controller;
import sistemacadastroacademia.model.Membro;
import sistemacadastroacademia.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class MembroController {

    public boolean adicionarMembro(Membro membro) {
        String sql = "INSERT INTO Membros (Nome, CPF, Telefone, Endereco, Data_Cadastro) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, membro.getNome());
            stmt.setString(2, membro.getCpf());
            stmt.setString(3, membro.getTelefone());
            stmt.setString(4, membro.getEndereco());

            if (membro.getDataCadastro() != null) {
                stmt.setDate(5, Date.valueOf(membro.getDataCadastro()));
            } else {
                stmt.setDate(5, Date.valueOf(java.time.LocalDate.now()));
            }

            int affectedRows = stmt.executeUpdate(); // Executa a inserção

            if (affectedRows > 0) {
                // Opcional: recuperar e definir o ID gerado
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        membro.setId(generatedKeys.getInt(1)); // Supondo que o ID é a primeira coluna gerada
                        System.out.println("MembroController: Membro adicionado com sucesso! ID: " + membro.getId());
                    } else {
                        System.out.println("MembroController: Membro adicionado com sucesso, mas não foi possível obter o ID gerado.");
                    }
                }
                return true; // Retorna true indicando sucesso.
            } else {
                System.err.println("MembroController - Nenhuma linha afetada ao adicionar membro.");
                return false; // Falha se nenhuma linha foi afetada
            }
        } catch (SQLException e) { // Bloco catch corrigido
            System.err.println("MembroController - ERRO ao adicionar membro: " + e.getMessage());
            e.printStackTrace();
            return false; // Retorna false se a inserção falhou.
        }
    }


    public List<Membro> listarMembros() {
        List<Membro> membros = new ArrayList<>();
        String sql = "SELECT * FROM Membros";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Membro membro = new Membro();
                membro.setId(rs.getInt("ID"));
                membro.setNome(rs.getString("Nome"));
                membro.setCpf(rs.getString("CPF"));
                membro.setTelefone(rs.getString("Telefone"));
                membro.setEndereco(rs.getString("Endereco"));
                Date dataCadastroSql = rs.getDate("Data_Cadastro");
                if (dataCadastroSql != null) {
                    membro.setDataCadastro(dataCadastroSql.toLocalDate());
                }
                membros.add(membro);
            }
            System.out.println("MembroController: " + membros.size() + " membros listados com sucesso.");

        } catch (SQLException e) {
            System.err.println("MembroController - ERRO ao listar membros: " + e.getMessage());
            e.printStackTrace();
            // A lista 'membros' será retornada (potencialmente vazia)
        }
        return membros; // Retorna a lista de membros (pode estar vazia)
    }

    //Busca um membro específico no banco de dados pelo seu ID.
    public Membro buscarMembroPorId(int id) {
        String sql = "SELECT * FROM Membros WHERE ID = ?";
        Membro membroEncontrado = null;

        try (Connection conn = Database.getConnection(); // Obtém a conexão com o banco
             PreparedStatement stmt = conn.prepareStatement(sql)) { // Prepara o comando SQL

            // stmt.setInt(1, id) define que o primeiro '?' será substituído pelo valor da variável 'id'.
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                // Verifica se o ResultSet contém alguma linha (ou seja, se um membro foi encontrado).
                if (rs.next()) {
                    // Se um membro foi encontrado, cria um novo objeto Membro.
                    membroEncontrado = new Membro();

                    // Preenche os atributos do objeto Membro com os dados da linha atual do ResultSet.
                    membroEncontrado.setId(rs.getInt("ID"));
                    membroEncontrado.setNome(rs.getString("Nome"));
                    membroEncontrado.setCpf(rs.getString("CPF"));
                    membroEncontrado.setTelefone(rs.getString("Telefone"));
                    membroEncontrado.setEndereco(rs.getString("Endereco"));

                    Date dataCadastroSql = rs.getDate("Data_Cadastro");
                    if (dataCadastroSql != null) {
                        membroEncontrado.setDataCadastro(dataCadastroSql.toLocalDate());
                    }
                    System.out.println("MembroController: Membro encontrado por ID (" + id + "): " + membroEncontrado.getNome());
                } else {
                    // Se rs.next() for false, nenhum membro foi encontrado com o ID fornecido.
                    System.out.println("MembroController: Nenhum membro encontrado com o ID: " + id);
                }
            }
        } catch (SQLException e) {
            System.err.println("MembroController - ERRO ao buscar membro por ID (" + id + "): " + e.getMessage());
            e.printStackTrace(); // Imprime o stack trace completo do erro para depuração.
        }
        return membroEncontrado;
    }


    //Atualiza os dados de um membro existente no banco de dados.
    public boolean atualizarMembro(Membro membro) {

        // Comando SQL para atualizar um registro na tabela Membros.
        String sql = "UPDATE Membros SET Nome = ?, CPF = ?, Telefone = ?, Endereco = ?, Data_Cadastro = ? WHERE ID = ?";


        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Define os valores para os placeholders '?' na string SQL.
            // A ordem é crucial e deve corresponder à ordem dos '?' no comando SQL.
            stmt.setString(1, membro.getNome());        // Define o novo Nome
            stmt.setString(2, membro.getCpf());         // Define o novo CPF
            stmt.setString(3, membro.getTelefone());    // Define o novo Telefone
            stmt.setString(4, membro.getEndereco());    // Define o novo Endereço

            // Converte java.time.LocalDate para java.sql.Date para o PreparedStatement.
            if (membro.getDataCadastro() != null) {
                stmt.setDate(5, Date.valueOf(membro.getDataCadastro())); // Define a nova Data_Cadastro
            } else {
                stmt.setDate(5, Date.valueOf(java.time.LocalDate.now())); // Usar a data atual se nulo
            }

            // Define o ID do membro para a cláusula WHERE. Este é o último '?' na string SQL.
            stmt.setInt(6, membro.getId());

            // Executa o comando SQL de atualização.
            // executeUpdate() é usado para comandos INSERT, UPDATE, DELETE.
            // Retorna o número de linhas que foram afetadas pela execução do comando.
            // Para um UPDATE bem-sucedido em um registro específico (baseado no ID), esperamos que seja 1.
            int linhasAfetadas = stmt.executeUpdate();

            // Verifica se alguma linha foi realmente atualizada.
            if (linhasAfetadas > 0) {
                // Se linhasAfetadas for maior que 0 (normalmente 1),
                // o membro foi encontrado e atualizado com sucesso.
                System.out.println("MembroController: Membro com ID " + membro.getId() + " atualizado com sucesso.");
                return true; // Retorna true indicando sucesso.
            } else {
                // Se nenhuma linha foi afetada, pode ser que o membro com o ID fornecido não exista no banco.
                System.out.println("MembroController: Nenhuma alteração realizada. Membro com ID " + membro.getId() + " pode não ter sido encontrado.");
                return false; // Retorna false, pois nada foi atualizado.
            }

        } catch (SQLException e) {
            // Captura e trata qualquer erro de SQL que possa ocorrer durante a atualização.
            // Imprime uma mensagem de erro detalhada e o stack trace para depuração.
            System.err.println("MembroController - ERRO ao atualizar membro com ID " + (membro != null ? membro.getId() : "N/A") + ": " + e.getMessage());
            e.printStackTrace();
            return false; // Retorna false em caso de erro.
        }
    }
}

// --- OUTROS MÉTODOS CRUD VIRÃO AQUI ---
// Exemplos:
// public boolean excluirMembro(int id) { ... }

