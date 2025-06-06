package sistemacadastroacademia.controller;
import sistemacadastroacademia.model.Pagamento;
import sistemacadastroacademia.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date; // Para converter LocalDate

public class PagamentoController {
     //Registra um novo pagamento no banco de dados
    public boolean registrarPagamento(Pagamento pagamento) {
        String sql = "INSERT INTO Pagamentos (ID_Membro, ID_Funcionario_Registro, Valor, Data_Pagamento, Status, Metodo_Pagamento, Data_Vencimento) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;

        try {
            conn = Database.getConnection(); // Obtém a conexão
            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                stmt.setInt(1, pagamento.getIdMembro());

                if (pagamento.getIdFuncionarioRegistro() != null) {
                    stmt.setInt(2, pagamento.getIdFuncionarioRegistro());
                } else {
                    stmt.setNull(2, java.sql.Types.INTEGER);
                }

                stmt.setBigDecimal(3, pagamento.getValor());
                stmt.setDate(4, Date.valueOf(pagamento.getDataPagamento()));
                stmt.setString(5, pagamento.getStatus());

                if (pagamento.getMetodoPagamento() != null) {
                    stmt.setString(6, pagamento.getMetodoPagamento());
                } else {
                    stmt.setNull(6, java.sql.Types.VARCHAR);
                }

                if (pagamento.getDataVencimento() != null) {
                    stmt.setDate(7, Date.valueOf(pagamento.getDataVencimento()));
                } else {
                    stmt.setNull(7, java.sql.Types.DATE);
                }

                int affectedRows = stmt.executeUpdate();

                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            pagamento.setId(generatedKeys.getInt(1)); // Define o ID gerado no objeto
                        }
                    }
                    System.out.println("PagamentoController: Pagamento registrado com sucesso! ID: " + pagamento.getId());
                    return true;
                } else {
                    System.err.println("PagamentoController: Nenhuma linha afetada ao registrar pagamento.");
                    return false;
                }
            }
        } catch (SQLException e) {
            System.err.println("PagamentoController - ERRO ao registrar pagamento: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}