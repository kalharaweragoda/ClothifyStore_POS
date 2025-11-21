package service.custom;

import dto.Customer;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface CustomerService {
    boolean add(Customer customer) throws SQLException;
    Customer search(Integer id) throws SQLException;
    List<Customer> getAll() throws SQLException;
    ObservableList<Integer> getIds() throws SQLException;
}
