package sistemacadastroacademia.controller;

import sistemacadastroacademia.model.Treino;
import sistemacadastroacademia.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Date; //  converter a data
import java.util.List;
import java.util.ArrayList;

public class TreinoController {


    //adiciona um novo treino ao banco de dados, associado a um membro.
    public boolean adicionarTreino(Treino treino) {
        String sql = "INSERT INTO Treinos (ID_Membro, ID_Funcionario_Instrutor, Tipo, Descricao, Duracao_Minutos, Data_Inicio) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = null;

        try {
            conn = Database.getConnection(); // Obtém a conexão
            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, treino.getIdMembro());

                if (treino.getIdFuncionarioInstrutor() != null) {
                    stmt.setInt(2, treino.getIdFuncionarioInstrutor());
                } else {
                    stmt.setNull(2, java.sql.Types.INTEGER); // Define como null no banco
                }
                stmt.setString(3, treino.getTipo());
                stmt.setString(4, treino.getDescricao());
                stmt.setInt(5, treino.getDuracaoMinutos());

                // convertendo java.time.LocalDate para java.sql.Date
                if (treino.getDataInicio() != null) {
                    stmt.setDate(6, Date.valueOf(treino.getDataInicio()));
                } else {
                    stmt.setNull(6, java.sql.Types.DATE);
                }

                int affectedRows = stmt.executeUpdate();

                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            treino.setId(generatedKeys.getInt(1)); // Define o ID gerado no objeto
                        }
                    }
                    System.out.println("TreinoController: Treino adicionado com sucesso! ID: " + treino.getId());
                    return true;
                } else {
                    System.err.println("TreinoController: Nenhuma linha afetada ao adicionar treino.");
                    return false;
                }
            }
        } catch (SQLException e) {
            System.err.println("TreinoController - ERRO ao adicionar treino: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    //lista todos os treinos de um membro específico, buscando pelo ID do membro.
    public List<Treino> listarTreinosPorMembro(int idMembro) {
        // cria uma lista vazia para armazenar os treinos encontrados.
        List<Treino> treinos = new ArrayList<>();
        // comando SQL para selecionar todos os  ONDE a coluna ID_Membro
        // corresponde ao ID que forneceremos.
        String sql = "SELECT * FROM Treinos WHERE ID_Membro = ?";
        Connection conn = null;

        try {
            conn = Database.getConnection(); //

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, idMembro);
                try (ResultSet rs = stmt.executeQuery()) {

                    // Itera sobre cada linha do resultado (cada treino encontrado)
                    while (rs.next()) {
                        // novo objeto Treino para cada linha
                        Treino treino = new Treino();

                        // Preenche os atributos
                        treino.setId(rs.getInt("ID"));
                        treino.setIdMembro(rs.getInt("ID_Membro"));

                        // Tratamento para a chave estrangeira nula ID_Funcionario_Instrutor
                        Object instrutorObj = rs.getObject("ID_Funcionario_Instrutor");
                        if (instrutorObj != null) {
                            treino.setIdFuncionarioInstrutor((Integer) instrutorObj);
                        } else {
                            treino.setIdFuncionarioInstrutor(null);
                        }

                        treino.setTipo(rs.getString("Tipo"));
                        treino.setDescricao(rs.getString("Descricao"));
                        treino.setDuracaoMinutos(rs.getInt("Duracao_Minutos"));

                        Date dataInicioSql = rs.getDate("Data_Inicio");
                        if (dataInicioSql != null) {
                            treino.setDataInicio(dataInicioSql.toLocalDate());
                        }

                        treinos.add(treino);
                    }
                }
            }
            System.out.println("TreinoController: Encontrados " + treinos.size() + " treinos para o membro ID: " + idMembro);

        } catch (SQLException e) {
            System.err.println("TreinoController - ERRO ao listar treinos por membro (" + idMembro + "): " + e.getMessage());
            e.printStackTrace();
        }
        return treinos;
    }
}