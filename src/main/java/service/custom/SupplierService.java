package service.custom;

import dto.Supplier;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface SupplierService {
    boolean add(Supplier supplier) throws SQLException;
    boolean search(String email) throws SQLException;
    List<Supplier> getAll() throws SQLException;
    boolean update(Supplier supplier) throws Exception;
    boolean delete(String email) throws SQLException;
    ObservableList<Integer> getIds() throws SQLException;
    Supplier search(Integer id) throws SQLException;

}
