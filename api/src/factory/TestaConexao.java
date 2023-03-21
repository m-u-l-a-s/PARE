
package factory;

public class TestaConexao {

    public static void main(String[] args) {
        ConnectionFactory MySQL = new ConnectionFactory();
        MySQL.getConexaoMySQL();
        System.out.println(MySQL.statusConnection());
        MySQL.CloseConnection();
        
    }
    
}
