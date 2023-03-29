/**
 * Classe permettant d'effectuer CRUD sur les factures
 */

package DAO.implement;

import DAO.DAO;
import model.Bill;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillDAO extends DAO<Bill>{
    public BillDAO(Connection conn){
        super(conn);
    }

    /**
     * Méthode permettant d'ajouter une facture dans la base de données dans la table bills si totalPrice et customerId sont définis
     * Sinon la méthode ajoute dans la table bills_items les articles et leurs quantités
     * Et mets à jour la table items la quantité restante de l'article en question
     */
    @Override
    public boolean create(Bill bill){
        if(bill.getTotalPrice() != null && bill.getFk_customerId() != 0) {
            try{
                conn.setAutoCommit(false);
                PreparedStatement stateInsert = conn.prepareStatement("INSERT INTO bills (date, totalPrice, fk_customerId) VALUES (?, ?, ?)");
                stateInsert.setString(1, bill.getDate());
                stateInsert.setFloat(2, bill.getTotalPrice());
                stateInsert.setInt(3, bill.getFk_customerId());
                stateInsert.executeUpdate();
                stateInsert.close();
                conn.commit();
                conn.setAutoCommit(true);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                conn.setAutoCommit(false);
                PreparedStatement stateInsert2 = conn.prepareStatement("INSERT INTO bills_items (quantity, fk_billId, fk_itemId) VALUES (?, ?, ?)");
                stateInsert2.setInt(1, bill.getQuantity());
                stateInsert2.setInt(2, bill.getFk_billId());
                stateInsert2.setInt(3, bill.getFk_itemId());
                stateInsert2.executeUpdate();
                stateInsert2.close();

                PreparedStatement stateUpdate = conn.prepareStatement("UPDATE items SET stock = stock - ? WHERE itemId = ?");
                stateUpdate.setInt(1, bill.getQuantity());
                stateUpdate.setInt(2, bill.getFk_itemId());
                stateUpdate.executeUpdate();
                stateUpdate.close();
                conn.commit();
                conn.setAutoCommit(true);
                return true;
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Méthode permettant de supprimer une facture dans la base de données
     */
    @Override
    public boolean delete(Bill bill){
        try{
            conn.setAutoCommit(false);
            PreparedStatement stateDelete = conn.prepareStatement("UPDATE bills SET isDelete = 1 WHERE billId = ?");
            stateDelete.setInt(1, bill.getBillId());
            stateDelete.executeUpdate();
            stateDelete.close();
            conn.commit();
            conn.setAutoCommit(true);
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Méthode permettant de modifier une facture dans la base de données
     */
    @Override
    public boolean update(Bill bill){
        try{
            conn.setAutoCommit(false);
            PreparedStatement stateUpdate = conn.prepareStatement("UPDATE bills SET totalPrice = ?, date = ? WHERE billId = ?");
            stateUpdate.setFloat(1, bill.getTotalPrice());
            stateUpdate.setString(2, bill.getDate());
            stateUpdate.setInt(3, bill.getBillId());
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
     * Méthode permettant de lister tous les factures de la base de données
     */
    @Override
    public List<Bill> getAll(){
        List<Bill> list = new ArrayList<>();

        try {
            conn.setAutoCommit(false);
            PreparedStatement state = conn.prepareStatement("SELECT * FROM bills WHERE isDelete != 1");
            ResultSet result = state.executeQuery();

            while(result.next()){
                list.add(new Bill(result.getInt("billId")));
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
     * Méthode permettant de trouver une facture dans la base de données via son id
     * Inutilisée dans l'application
     */
    @Override
    public Bill findById(int id){
        Bill bill = null;
        return bill;
    }

    /**
     * Méthode permettant de trouver une facture dans la base de données via son nom
     * Inutilisée dans l'application
     */
    @Override
    public Bill findByString(String string){
        Bill bill = null;
        return bill;
    }

    /**
     * Méthode permettant de retourner la dernière facture enregistrée dans la base de données
     * Inutilisée dans l'application
     */
    @Override
    public int getLastId() {
        try {
            conn.setAutoCommit(false);
            int lastBillId = 0;
            PreparedStatement state = conn.prepareStatement("SELECT MAX(billId) as lastId FROM bills");
            ResultSet result = state.executeQuery();

            if(result.first()) {
                lastBillId = result.getInt("lastId");
            }
            state.close();
            conn.commit();
            conn.setAutoCommit(true);
            return lastBillId;
        } catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Méthode permettant d'ajouter dans une liste toutes les factures et de renvoyer celle-ci
     * Inutilisée dans l'application
     */
    @Override
    public List<Bill> getListById(int id) {
        List<Bill> list = new ArrayList<>();
        return list;
    }

    /**
     * Méthode permettant de supprimer toutes les factures de la table bills_items dans la base de données avec l'id d'une facture
     */
    @Override
    public boolean deleteAllById(int id){
        try {
            conn.setAutoCommit(false);
            PreparedStatement stateDelete = conn.prepareStatement("DELETE FROM bills_items WHERE fk_billId = ?");
            stateDelete.setInt(1, id);
            stateDelete.executeUpdate();
            stateDelete.close();
            conn.commit();
            conn.setAutoCommit(true);
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
