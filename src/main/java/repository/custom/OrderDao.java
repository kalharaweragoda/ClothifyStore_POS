package repository.custom;

import entity.OrderEntity;
import javafx.collections.ObservableList;

import java.util.List;

public interface OrderDao {
    boolean add(OrderEntity orderEntity) throws Exception;
    List<OrderEntity> getAll() throws Exception;
    ObservableList<Integer> getIds() throws Exception;
    OrderEntity search(Integer id) throws Exception;
}
