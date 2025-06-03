package sistemacadastroacademia.util;

import java.sql.Connection;       // conexão ativa com o banco.
import java.sql.DriverManager;
import java.sql.SQLException;


 // Classe responsável por gerenciar a conexão com o banco de dados MySQL
 // do nosso projeto.
public class Database {
     private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";


     // "localhost:3306" MySQL está nessa maquina (localhost) na porta 3306.
     private static final String DB_URL = "jdbc:mysql://localhost:3306/SistemaCadastroAcademia?useTimezone=true&serverTimezone=UTC";
     private static final String USER = "root";
     private static final String PASS = "admin";


     private static Connection dbconn = null;

     public static Connection getConnection() throws SQLException {
         try {
             Class.forName(JDBC_DRIVER);
             if (dbconn == null || dbconn.isClosed()) {
                 System.out.println("Database.java: Conectando ao banco: " + DB_URL);
                 System.out.println("Database.java: Estabelecendo NOVA conexão...");
                 dbconn = DriverManager.getConnection(DB_URL, USER, PASS);
                 System.out.println("Database.java: Conexão estabelecida com Sucesso!");
             } else {
                 System.out.println("Database.java:  Reutilizando conexão existente.");
             }
         } catch (ClassNotFoundException e) {
             // Erro se a classe do driver não for encontrada (JAR faltando ou nome errado).
             System.err.println("Database.java - ERRO FATAL: Driver JDBC (" + JDBC_DRIVER + ") não encontrado.");
             e.printStackTrace(); // Mostra o erro completo no console.
             throw new SQLException("Driver JDBC não encontrado. ", e);
         } catch (SQLException e) {
             // Erro durante a tentativa de conexão (URL, usuário/senha, MySQL offline).
             System.err.println("Database.java - ERRO AO CONECTAR: Falha ao estabelecer conexão com o MySQL.");
             System.err.println("   SQLState: " + e.getSQLState() + ", Error Code: " + e.getErrorCode());
             System.err.println("   Mensagem: " + e.getMessage());
             e.printStackTrace();
             throw e; // lança a exceção para o método que chamou getConnection().
         }
         // Retorna a conexão (nova ou reutilizada).
         return dbconn;
     }

     public static void closeConnection() {
         try {
             if (dbconn != null && !dbconn.isClosed()) {
                 dbconn.close();
                 System.out.println("Database.java: Conexão com o banco de dados FECHADA.");
             }
         } catch (SQLException e) {
             System.err.println("Database.java - ERRO ao fechar a conexão: " + e.getMessage());
             e.printStackTrace();
         }
     }
 }