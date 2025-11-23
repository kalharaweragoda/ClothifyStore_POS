package service.custom;

import dto.Order;
import javafx.collections.ObservableList;

import java.util.List;

public interface OrderService {
    boolean add(Order order) throws Exception;
    List<Order> getAll() throws Exception;
    ObservableList<Integer> getIds() throws Exception;
    Order search(Integer id) throws Exception;
}
