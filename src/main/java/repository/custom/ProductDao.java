package repository.custom;

import entity.ProductEntity;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {
    boolean add(ProductEntity productEntity) throws SQLException;
    List<ProductEntity> getAll() throws SQLException;
    ObservableList<Integer> getIds() throws SQLException;
    ProductEntity search(Integer code);
    boolean update(ProductEntity productEntity) throws RuntimeException;

    boolean delete(Integer code);
}
