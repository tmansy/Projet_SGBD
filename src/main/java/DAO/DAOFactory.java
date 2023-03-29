/**
 * Classe permettant d'initialiser les DAO
 */

package DAO;

import DAO.implement.BillDAO;
import DAO.implement.CustomerDAO;
import DAO.implement.ItemDAO;
import model.Bill;
import model.Customer;
import model.Item;
import singleton.DatabaseConnection;
import DAO.implement.UserDAO;
import model.User;
import java.sql.Connection;

public class DAOFactory {
    protected static final Connection conn = DatabaseConnection.getInstance();

    public static DAO<User> getUserDAO(){
        return new UserDAO(conn);
    }

    public static DAO<Item> getItemDAO() { return new ItemDAO(conn); }

    public static DAO<Customer> getCustomerDAO() { return new CustomerDAO(conn); }

    public static DAO<Bill> getBillDAO() { return new BillDAO(conn); }
}
