/**
 * Classe permettant d'effectuer CRUD sur les utilisateurs
 */

package DAO.implement;

import DAO.DAO;
import model.User;
import singleton.PasswordHash;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends DAO<User>{
    public UserDAO(Connection conn){
        super(conn);
    }

    /**
     * Méthode permettant d'ajouter un utilisateur dans la base de données
     */
    @Override
    public boolean create(User user){
        try{
            conn.setAutoCommit(false);
            String password = PasswordHash.getSecurePassword(user.getPassword());
            PreparedStatement stateInsert = conn.prepareStatement("INSERT INTO users (firstname, lastname, username, password, email) VALUES (?, ?, ?, ?, ?)");
            stateInsert.setString(1, user.getFirstname());
            stateInsert.setString(2, user.getLastname());
            stateInsert.setString(3, user.getUsername());
            stateInsert.setString(4, password);
            stateInsert.setString(5, user.getEmail());
            stateInsert.executeUpdate();
            stateInsert.close();
            conn.commit();
            conn.setAutoCommit(true);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Méthode permettant de supprimer un utilisateur dans la base de données
     */
    @Override
    public boolean delete(User user){
        try{
            conn.setAutoCommit(false);
            PreparedStatement stateDelete = conn.prepareStatement("UPDATE users SET isDelete = 1 WHERE idUser = ?");
            stateDelete.setInt(1, user.getIdUser());
            stateDelete.executeUpdate();
            stateDelete.close();
            conn.commit();
            conn.setAutoCommit(true);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Méthode permettant de modifier un utilisateur dans la base de données
     */
    @Override
    public boolean update(User user){
        try{
            conn.setAutoCommit(false);
            String password = PasswordHash.getSecurePassword(user.getPassword());
            PreparedStatement stateUpdate = conn.prepareStatement("UPDATE users SET firstname = ?, lastname = ?, username = ?, password = ?, email = ? WHERE idUser = ?");
            stateUpdate.setString(1, user.getFirstname());
            stateUpdate.setString(2, user.getLastname());
            stateUpdate.setString(3, user.getUsername());
            stateUpdate.setString(4, password);
            stateUpdate.setString(5, user.getEmail());
            stateUpdate.setInt(6, user.getIdUser());
            stateUpdate.executeUpdate();
            stateUpdate.close();
            conn.commit();
            conn.setAutoCommit(true);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Méthode permettant de lister tous les utilisateurs de la base de données
     */
    @Override
    public List<User> getAll(){
        List<User> list = new ArrayList<>();

        try {
            conn.setAutoCommit(false);
            PreparedStatement state = conn.prepareStatement("SELECT * FROM users WHERE isDelete != 1");
            ResultSet result = state.executeQuery();

            while(result.next()){
                list.add(new User(result.getInt("idUser"), result.getString("firstname"), result.getString("lastname"), result.getString("username"), result.getString("password"), result.getString("email")));
            }
            state.close();
            result.close();
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Méthode permettant de trouver un utilisateur dans la base de données via son id
     */
    @Override
    public User findById(int id){
        User user = null;

        try {
            conn.setAutoCommit(false);
            PreparedStatement state = conn.prepareStatement("SELECT * FROM users AS u WHERE u.idUser = ?");
            state.setInt(1, id);
            ResultSet result = state.executeQuery();

            while(result.first()){
                user = new User(result.getInt("idUser"), result.getString("firstname"), result.getString("lastname"), result.getString("username"), result.getString("password"), result.getString("email"));
            }
            state.close();
            result.close();
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Méthode permettant de trouver un utilisateur dans la base de données via son nom
     */
    @Override
    public User findByString(String string){
        User user = null;

        try {
            conn.setAutoCommit(false);
            PreparedStatement state = conn.prepareStatement("SELECT * FROM users WHERE username = ? OR email = ?");
            state.setString(1, string);
            state.setString(2, string);
            ResultSet result = state.executeQuery();

            while(result.next()){
                user = new User(result.getInt("idUser"), result.getString("firstname"), result.getString("lastname"), result.getString("username"), result.getString("password"), result.getString("email"));
            }
            
            state.close();
            result.close();
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Méthode permettant de retourner le dernier utilisateur enregistré dans la base de données
     * Inutilisée dans l'application
     */
    @Override
    public int getLastId(){
        return 0;
    }

    /**
     * Méthode permettant d'ajouter dans une liste tous les utilisateurs et de renvoyer celle-ci
     * Inutilisée dans l'application
     */
    @Override
    public List<User> getListById(int id) {
        List<User> list = new ArrayList<>();
        return list;
    }

    /**
     * Méthode permettant de supprimer tous les articles dans la base de données
     * Inutilisée dans l'application
     */
    @Override
    public boolean deleteAllById(int id){
        return true;
    }
}
