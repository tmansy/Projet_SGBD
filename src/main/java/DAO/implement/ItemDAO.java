/**
 * Classe permettant d'effectuer CRUD sur les articles
 */

package DAO.implement;

import DAO.DAO;
import model.Item;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO extends DAO<Item>{
    public ItemDAO(Connection conn){
        super(conn);
    }

    /**
     * Méthode permettant d'ajouter un article dans la base de données
     */
    @Override
    public boolean create(Item item){
        try{
            conn.setAutoCommit(false);
            PreparedStatement stateInsert = conn.prepareStatement("INSERT INTO items (name, brand, price, stock) VALUES (?, ?, ?, ?)");
            stateInsert.setString(1, item.getName());
            stateInsert.setString(2, item.getBrand());
            stateInsert.setFloat(3, item.getPrice());
            stateInsert.setInt(4, item.getStock());
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
     * Méthode permettant de supprimer un article dans la base de données
     */
    @Override
    public boolean delete(Item item){
        try{
            conn.setAutoCommit(false);
            PreparedStatement stateDelete = conn.prepareStatement("UPDATE items SET isDelete = 1 WHERE itemId = ?");
            stateDelete.setInt(1, item.getItemId());
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
     * Méthode permettant de modifier un article dans la base de données
     */
    @Override
    public boolean update(Item item){
        try{
            conn.setAutoCommit(false);
            PreparedStatement stateUpdate = conn.prepareStatement("UPDATE items SET name = ?, brand = ?, price = ?, stock = ? WHERE itemId = ?");
            stateUpdate.setString(1, item.getName());
            stateUpdate.setString(2, item.getBrand());
            stateUpdate.setFloat(3, item.getPrice());
            stateUpdate.setInt(4, item.getStock());
            stateUpdate.setInt(5, item.getItemId());
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
     * Méthode permettant de lister tous les articles de la base de données
     */
    @Override
    public List<Item> getAll(){
        List<Item> list = new ArrayList<>();

        try {
            conn.setAutoCommit(false);
            PreparedStatement state = conn.prepareStatement("SELECT * FROM items WHERE isDelete != 1");
            ResultSet result = state.executeQuery();

            while(result.next()){
                list.add(new Item(result.getInt("itemId"), result.getString("name"), result.getString("brand"), result.getFloat("price"), result.getInt("stock")));
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
     * Méthode permettant de trouver un article dans la base de données via son id
     */
    @Override
    public Item findById(int id){
        Item item = null;

        try {
            conn.setAutoCommit(false);
            PreparedStatement state = conn.prepareStatement("SELECT * FROM items AS i WHERE i.itemId = ?");
            state.setInt(1, id);
            ResultSet result = state.executeQuery();

            while(result.first()){
                item = new Item(result.getInt("itemId"), result.getString("name"), result.getString("brand"), result.getFloat("price"), result.getInt("stock"));
            }
            state.close();
            result.close();
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e){
            e.printStackTrace();
        }

        return item;
    }

    /**
     * Méthode permettant de trouver un article dans la base de données via son nom
     */
    @Override
    public Item findByString(String string){
        Item item = null;

        try {
            conn.setAutoCommit(false);
            PreparedStatement state = conn.prepareStatement("SELECT * FROM items WHERE name = ?");
            state.setString(1, string);
            ResultSet result = state.executeQuery();

            while(result.next()){
                item = new Item(result.getInt("itemId"), result.getString("name"), result.getString("brand"), result.getFloat("price"), result.getInt("stock"));
            }

            state.close();
            result.close();
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e){
            e.printStackTrace();
        }

        return item;
    }

    /**
     * Méthode permettant de retourner le dernier article enregistré dans la base de données
     * Inutilisée dans l'application
     */
    @Override
    public int getLastId(){
        return 0;
    }

    /**
     * Méthode permettant d'ajouter dans une liste tous les articles d'une facture et de renvoyer celle-ci
     */
    @Override
    public List<Item> getListById(int id) {
        List<Item> list = new ArrayList<>();

        try{
            conn.setAutoCommit(false);
            PreparedStatement state = conn.prepareStatement("SELECT i.name, i.brand, i.price, bi.quantity, c.lastname FROM items AS i INNER JOIN bills_items AS bi ON i.itemId = bi.fk_itemId INNER JOIN bills AS b ON bi.fk_billId = b.billId INNER JOIN customers AS C on b.fk_customerId = c.customerId WHERE b.billId = ?");
            state.setInt(1, id);
            ResultSet result = state.executeQuery();

            while(result.next()) {
                list.add(new Item(result.getString("name"), result.getString("brand"), result.getFloat("price"), result.getInt("quantity"), result.getString("lastname")));
            }

            state.close();
            result.close();
            conn.commit();
            conn.setAutoCommit(true);
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Méthode permettant de supprimer tous les articles d'une facture dans la base de données via l'id d'une facture
     * Inutilisée dans l'application
     */
    @Override
    public boolean deleteAllById(int id){
        return true;
    }
}
