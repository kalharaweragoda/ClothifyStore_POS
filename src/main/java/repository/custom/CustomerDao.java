package repository.custom;

import entity.CustomerEntity;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDao {
    boolean add(CustomerEntity customerEntity) throws SQLException;
    CustomerEntity search(Integer id) throws SQLException;
    List<CustomerEntity> getAll() throws SQLException;
    ObservableList<Integer> getIds() throws SQLException;
}
