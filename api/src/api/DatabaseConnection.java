package api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection {
    public static String status = "Não conectou!";
	
	// Método de conexão
	
	public static java.sql.Connection getConexaoMySQL(){
		 Connection connection = null; 
		
		try {
		// Carregando o JDBC Driver Padrão
		String driverName = "com.mysql.cj.jdbc.Driver";
		Class.forName(driverName);
		
		// Configurando conexão com um banco de Dados//
		
		String serverName = "localhost";
		String mydatabase = "mysql";
		String url = "jdbc:mysql://"+serverName+"/"+mydatabase;
		String username = "root";
		String password = "Desenv1243$";
		connection = DriverManager.getConnection(url, username, password);
		
		// Testa conexão:
		
		if(connection != null) {
			status = ("STATUS----> Conectado com sucesso!");
		}else {
			status = ("STATUS----> Não foi possível realizar conexão");
		}
		return connection;
			
		}catch(ClassNotFoundException e)
 {System.out.println("O driver especificado não foi encontrado");
			return null;
		}catch(SQLException e) {
			// Não conseguimos conectar ao banco de dados
			System.out.println("Não foi possível conectar ao banco de dados");
			return null;
		}
	}
	
	
	// Método que retorna o status da conexão:
	public static String statusConnection() {
		return status;
		
	}
	
	public static boolean CloseConnection() {
		try {
			DatabaseConnection.getConexaoMySQL().close();
			return true;
				
		}catch(SQLException e) {
			return false;
		}
	}
	
	
	// Método que reinicia sua conexão//
		public static java.sql.Connection RestartConnection(){
			CloseConnection();
			return DatabaseConnection.getConexaoMySQL();
		
		}
    
}
