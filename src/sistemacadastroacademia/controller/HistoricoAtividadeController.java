package sistemacadastroacademia.controller;

import sistemacadastroacademia.model.HistoricoAtividade;
import sistemacadastroacademia.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp; // Para converter LocalDateTime
import java.util.ArrayList;
import java.util.List;

public class HistoricoAtividadeController {


     //Registra uma nova atividade no histórico de um membro.
    public boolean registrarAtividade(HistoricoAtividade atividade) {
        String sql = "INSERT INTO Historico_Atividades (ID_Membro, ID_Funcionario_Responsavel, Atividade, Data_Hora_Atividade, Tempo_Execucao_Minutos, Observacoes) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = null;

        try {
            conn = Database.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                stmt.setInt(1, atividade.getIdMembro());

                if (atividade.getIdFuncionarioResponsavel() != null) {
                    stmt.setInt(2, atividade.getIdFuncionarioResponsavel());
                } else {
                    stmt.setNull(2, java.sql.Types.INTEGER);
                }

                stmt.setString(3, atividade.getAtividade());

                // para colunas DATETIME, convertemos LocalDateTime para java.sql.Timestamp
                if (atividade.getDataHoraAtividade() != null) {
                    stmt.setTimestamp(4, Timestamp.valueOf(atividade.getDataHoraAtividade()));
                } else {
                    stmt.setNull(4, java.sql.Types.TIMESTAMP);
                }

                if (atividade.getTempoExecucaoMinutos() != null) {
                    stmt.setInt(5, atividade.getTempoExecucaoMinutos());
                } else {
                    stmt.setNull(5, java.sql.Types.INTEGER);
                }

                stmt.setString(6, atividade.getObservacoes());

                int affectedRows = stmt.executeUpdate();

                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            atividade.setId(generatedKeys.getInt(1)); // define o ID
                        }
                    }
                    System.out.println("HistoricoAtividadeController: Atividade registrada com sucesso! ID: " + atividade.getId());
                    return true;
                } else {
                    System.err.println("HistoricoAtividadeController: Nenhuma linha afetada ao registrar atividade.");
                    return false;
                }
            }
        } catch (SQLException e) {
            System.err.println("HistoricoAtividadeController - ERRO ao registrar atividade: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


     // Lista todas as atividades de um membro específico, buscando pelo ID do membro.
    public List<HistoricoAtividade> listarAtividadesPorMembro(int idMembro) {
        List<HistoricoAtividade> historico = new ArrayList<>();
        String sql = "SELECT * FROM Historico_Atividades WHERE ID_Membro = ? ORDER BY Data_Hora_Atividade DESC";
        Connection conn = null;

        try {
            conn = Database.getConnection(); // Obtém a conexão

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, idMembro);

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        HistoricoAtividade atividade = new HistoricoAtividade();

                        atividade.setId(rs.getInt("ID"));
                        atividade.setIdMembro(rs.getInt("ID_Membro"));

                        Object funcObj = rs.getObject("ID_Funcionario_Responsavel");
                        if (funcObj != null) {
                            atividade.setIdFuncionarioResponsavel((Integer) funcObj);
                        }

                        atividade.setAtividade(rs.getString("Atividade"));

                        Timestamp timestamp = rs.getTimestamp("Data_Hora_Atividade");
                        if (timestamp != null) {
                            atividade.setDataHoraAtividade(timestamp.toLocalDateTime());
                        }

                        Object tempoObj = rs.getObject("Tempo_Execucao_Minutos");
                        if (tempoObj != null) {
                            atividade.setTempoExecucaoMinutos((Integer) tempoObj);
                        }

                        atividade.setObservacoes(rs.getString("Observacoes"));

                        historico.add(atividade);
                    }
                }
            }
            System.out.println("HistoricoAtividadeController: Encontradas " + historico.size() + " atividades para o membro ID: " + idMembro);

        } catch (SQLException e) {
            System.err.println("HistoricoAtividadeController - ERRO ao listar atividades por membro (" + idMembro + "): " + e.getMessage());
            e.printStackTrace();
        }
        return historico;
    }
}