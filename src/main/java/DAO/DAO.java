/**
 * Classe permettant d'initialiser les méthodes des DAO
 */

package DAO;

import java.sql.Connection;
import java.util.List;

public abstract class DAO<T> {
    protected Connection conn;

    public DAO(Connection conn){
        this.conn = conn;
    }

    public abstract boolean create(T obj);

    public abstract boolean delete(T obj);

    public abstract boolean update(T obj);

    public abstract List<T> getAll();

    public abstract T findById(int id);

    public abstract T findByString(String string);

    public abstract int getLastId();

    public abstract List<T> getListById(int id);

    public abstract boolean deleteAllById(int id);
}
