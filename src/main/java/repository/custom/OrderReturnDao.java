package repository.custom;

import entity.OrderReturnEntity;

import java.util.List;

public interface OrderReturnDao {
    boolean add(OrderReturnEntity orderReturnEntity) throws Exception;
    List<OrderReturnEntity> getAll() throws Exception;
}
