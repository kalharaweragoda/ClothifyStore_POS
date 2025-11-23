package service.custom;

import dto.OrderReturn;

import java.util.List;

public interface OrderReturnService {
    boolean add(OrderReturn orderReturn) throws Exception;
    List<OrderReturn> getAll() throws Exception;
}
