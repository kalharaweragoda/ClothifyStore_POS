package service.custom.impl;

import dto.OrderReturn;
import entity.OrderReturnEntity;
import jakarta.inject.Inject;
import org.modelmapper.ModelMapper;
import repository.custom.OrderReturnDao;
import service.custom.OrderReturnService;

import java.util.ArrayList;
import java.util.List;

public class OrderReturnServiceImpl implements OrderReturnService {
    @Inject
    OrderReturnDao orderReturnDao;

    @Override
    public boolean add(OrderReturn orderReturn) throws Exception {
        return orderReturnDao.add(new ModelMapper().map(orderReturn, OrderReturnEntity.class));
    }

    @Override
    public List<OrderReturn> getAll() throws Exception {
        List<OrderReturnEntity> orderReturnEntityList = orderReturnDao.getAll();
        List<OrderReturn> orderReturnList = new ArrayList<>();
        for (OrderReturnEntity orderReturnEntity:orderReturnEntityList){
            orderReturnList.add(new ModelMapper().map(orderReturnEntity, OrderReturn.class));
        }

        return orderReturnList;
    }

}
