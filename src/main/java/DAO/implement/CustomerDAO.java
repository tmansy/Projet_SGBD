/**
 * Classe permettant d'effectuer CRUD sur les clients
 */

package DAO.implement;

import model.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import DAO.DAO;

public class CustomerDAO extends DAO<Customer>{
    public CustomerDAO(Connection conn){
        super(conn);
    }

    /**
     * Méthode permettant d'ajouter un client dans la base de données
     */
    @Override
    public boolean create(Customer customer){
        try{
            conn.setAutoCommit(false);
            PreparedStatement stateInsert = conn.prepareStatement("INSERT INTO customers (firstname, lastname, address, phoneNumber) VALUES (?, ?, ?, ?)");
            stateInsert.setString(1, customer.getFirstname());
            stateInsert.setString(2, customer.getLastname());
            stateInsert.setString(3, customer.getAddress());
            stateInsert.setString(4, customer.getPhoneNumber());
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
     * Méthode permettant de supprimer un client dans la base de données
     */
    @Override
    public boolean delete(Customer customer){
        try{
            conn.setAutoCommit(false);
            PreparedStatement stateDelete = conn.prepareStatement("UPDATE customers SET isDelete = 1 WHERE customerId = ?");
            stateDelete.setInt(1, customer.getCustomerId());
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
     * Méthode permettant de modifier un client dans la base de données
     */
    @Override
    public boolean update(Customer customer){
        try{
            conn.setAutoCommit(false);
            PreparedStatement stateUpdate = conn.prepareStatement("UPDATE customers SET firstname = ?, lastname = ?, address = ?, phoneNumber = ? WHERE customerId = ?");
            stateUpdate.setString(1, customer.getFirstname());
            stateUpdate.setString(2, customer.getLastname());
            stateUpdate.setString(3, customer.getAddress());
            stateUpdate.setString(4, customer.getPhoneNumber());
            stateUpdate.setInt(5, customer.getCustomerId());
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
     * Méthode permettant de lister tous les clients de la base de données
     */
    @Override
    public List<Customer> getAll(){
        List<Customer> list = new ArrayList<>();

        try {
            conn.setAutoCommit(false);
            PreparedStatement state = conn.prepareStatement("SELECT * FROM customers WHERE isDelete != 1");
            ResultSet result = state.executeQuery();

            while(result.next()){
                list.add(new Customer(result.getInt("customerId"), result.getString("firstname"), result.getString("lastname"), result.getString("address"), result.getString("phoneNumber")));
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
     * Méthode permettant de trouver un client dans la base de données via son id
     */
    @Override
    public Customer findById(int id){
        Customer customer = null;

        try {
            conn.setAutoCommit(false);
            PreparedStatement state = conn.prepareStatement("SELECT * FROM customers AS c WHERE c.customerId = ?");
            state.setInt(1, id);
            ResultSet result = state.executeQuery();

            while(result.first()){
                customer = new Customer(result.getInt("customerId"), result.getString("firstname"), result.getString("lastname"), result.getString("address"), result.getString("phoneNumber"));
            }
            state.close();
            result.close();
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return customer;
    }

    /**
     * Méthode permettant de trouver un client dans la base de données via son nom
     */
    @Override
    public Customer findByString(String string){
        Customer customer = null;

        try {
            conn.setAutoCommit(false);
            PreparedStatement state = conn.prepareStatement("SELECT * FROM customers WHERE firstname = ? OR lastname = ?");
            state.setString(1, string);
            state.setString(2, string);
            ResultSet result = state.executeQuery();

            while(result.next()){
                customer = new Customer(result.getInt("customerId"), result.getString("firstname"), result.getString("lastname"), result.getString("address"), result.getString("phoneNumber"));
            }

            state.close();
            result.close();
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return customer;
    }

    /**
     * Méthode permettant de retourner le dernier client enregistré dans la base de données
     * Inutilisée dans l'application
     */
    @Override
    public int getLastId(){
        return 0;
    }

    /**
     * Méthode permettant d'ajouter dans une liste tous les clients et de renvoyer celle-ci
     * Inutilisée dans l'application
     */
    @Override
    public List<Customer> getListById(int id) {
        List<Customer> list = new ArrayList<>();
        return list;
    }

    /**
     * Méthode permettant de supprimer tous les clients dans la base de données
     * Inutilisée dans l'application
     */
    @Override
    public boolean deleteAllById(int id){
        return true;
    }
}
