package sistemacadastroacademia.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/SistemaCadastroAcademia?useTimezone=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "admin"; // Lembre-se de usar sua senha

    private static Connection dbconn = null;

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(JDBC_DRIVER);
            if (dbconn == null || dbconn.isClosed()) {
                // Adicionando log para sabermos quando uma nova conexão é criada
                System.out.println("Database.java: Estabelecendo NOVA conexão...");
                dbconn = DriverManager.getConnection(DB_URL, USER, PASS);
                System.out.println("Database.java: Conexão estabelecida com Sucesso!");
            } else {
                // Adicionando log para sabermos quando a conexão é reutilizada
                System.out.println("Database.java: Reutilizando conexão existente.");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Database.java - ERRO FATAL: Driver JDBC não encontrado.");
            throw new SQLException("Driver JDBC não encontrado.", e);
        }
        return dbconn;
    }

    public static void closeConnection() {
        try {
            if (dbconn != null && !dbconn.isClosed()) {
                dbconn.close();
                // Adicionando log para confirmar o fechamento da conexão
                System.out.println("Database.java: Conexão com o banco de dados FECHADA.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}