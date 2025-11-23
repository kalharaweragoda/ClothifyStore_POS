package repository.custom;

import entity.SupplierEntity;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface SupplierDao {
    boolean add(SupplierEntity supplierEntity) throws SQLException;
    SupplierEntity search(String email) throws SQLException;
    List<SupplierEntity> getAll() throws SQLException;
    boolean update(SupplierEntity supplierEntity) throws Exception;
    boolean delete(String email) throws SQLException;
    ObservableList<Integer> getIds() throws SQLException;
    SupplierEntity search(Integer id) throws SQLException;
}
