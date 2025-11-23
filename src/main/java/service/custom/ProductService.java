package service.custom;

import dto.Product;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface ProductService {
    boolean add(Product product) throws SQLException;
    List<Product> getAll() throws SQLException;
    boolean update(Product product) throws RuntimeException;
    boolean delete(Integer code);
    ObservableList<Integer> getIds() throws SQLException;
    Product search(Integer id);
}
