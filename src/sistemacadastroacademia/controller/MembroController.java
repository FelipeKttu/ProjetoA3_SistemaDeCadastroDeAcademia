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
        Connection conn = null;

        try {
            conn = Database.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

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
            }
        } catch (SQLException e) {
            System.err.println("MembroController - ERRO ao adicionar membro: " + e.getMessage());
            e.printStackTrace();
            return false; // Retorna false se a inserção falhou.
        }
    }


    public List<Membro> listarMembros() {
        List<Membro> membros = new ArrayList<>();
        String sql = "SELECT * FROM Membros";
        Connection conn = null;

        try {
            conn = Database.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql);
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

            }
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
        Connection conn = null;

        try {
            conn = Database.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        membroEncontrado = new Membro();
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
                        System.out.println("MembroController: Nenhum membro encontrado com o ID: " + id);
                    }
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
        Connection conn = null;

        try {
            conn = Database.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, membro.getNome());
                stmt.setString(2, membro.getCpf());
                stmt.setString(3, membro.getTelefone());
                stmt.setString(4, membro.getEndereco());
                if (membro.getDataCadastro() != null) {
                    stmt.setDate(5, Date.valueOf(membro.getDataCadastro()));
                } else {
                    stmt.setDate(5, Date.valueOf(java.time.LocalDate.now()));
                }
                stmt.setInt(6, membro.getId());
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("MembroController: Membro com ID " + membro.getId() + " atualizado com sucesso.");
                    return true;
                } else {
                    System.out.println("MembroController: Nenhuma alteração realizada. Membro com ID " + membro.getId() + " pode não ter sido encontrado.");
                    return false;
                }
            }
        } catch (SQLException e) {
            System.err.println("MembroController - ERRO ao atualizar membro com ID " + (membro != null ? membro.getId() : "N/A") + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    // Exclui um membro do banco de dados com base no seu ID.
    public boolean excluirMembro(int id) {
        // Define o comando SQL para deletar um registro da tabela Membros.
        // A cláusula WHERE ID = ? especifica qual membro será excluído
        String sql = "DELETE FROM Membros WHERE ID = ?";
        Connection conn = null;

        try {
            conn = Database.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("MembroController: Membro com ID " + id + " excluído com sucesso.");
                    return true;
                } else {
                    System.out.println("MembroController: Nenhuma linha afetada. Membro com ID " + id + " não encontrado para exclusão.");
                    return false;
                }
            }
        } catch (SQLException e) {
            System.err.println("MembroController - ERRO ao excluir membro com ID " + id + ": " + e.getMessage());
            e.printStackTrace(); // Imprime o erro
            return false;
        }
    }
}