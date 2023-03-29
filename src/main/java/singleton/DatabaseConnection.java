/**
 * Classe permettant se connecter à la base de données
 */

package singleton;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class DatabaseConnection {
    private String url = "jdbc:mysql://localhost:3307/wholesaler?autoReconnect=true&useSSL=false";
    private String user = "root";
    private String password = "";
    private static Connection conn;
    private volatile static DatabaseConnection single = new DatabaseConnection();

    /**
     * Méthode permettant de se connecter à la base de données
     */
    private DatabaseConnection(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
        } catch(SQLException | ClassNotFoundException e){
            System.out.println("Erreur lors de l'initialisation de la connexion");
        }
    }

    /**
     * Méthode permettant d'obtenir une instance de la connexion à la base de données
     */
    public static Connection getInstance(){
        if(single == null){
            synchronized (Connection.class) {
                if (single == null) {
                    single = new DatabaseConnection();
                }
            }
        }
        return conn;
    }

    /**
     * Méthode permettant de se déconnecter à la base de données
     */
    public static void closeConnexion(){
        try{
            if(conn != null){
                conn.close();
                System.out.println("\nLa connection a bien été fermée");
            }
        }catch(SQLException e){
            System.out.println("Erreur lors de la fermeture de la connexion");
        }
    }
}
