package service.custom.impl;

import dto.Order;
import dto.OrderDetail;
import entity.OrderDetailEntity;
import entity.OrderEntity;
import entity.ProductEntity;
import jakarta.inject.Inject;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;
import repository.custom.OrderDao;
import service.custom.OrderService;

import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    @Inject
    OrderDao orderDao;

    @Override
    public boolean add(Order order) throws Exception {
        OrderEntity orderEntity = new OrderEntity();

        orderEntity.setDate(order.getDate());
        orderEntity.setEmployeeId(order.getEmployeeId());
        orderEntity.setEmployeeName(order.getEmployeeName());
        orderEntity.setCustomerId(order.getCustomerId());
        orderEntity.setTotal(order.getTotal());
        orderEntity.setPaymentType(order.getPaymentType());

        List<OrderDetailEntity> orderDetailEntityList = new ArrayList<>();
        if (order.getOrderDetailList()!=null){
            for (OrderDetail orderDetail: order.getOrderDetailList()){
                OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
                orderDetailEntity.setOrderEntity(orderEntity);
                orderDetailEntity.setProductEntity(new ModelMapper().map(orderDetail.getProduct(), ProductEntity.class));
                orderDetailEntity.setUnitPrice(orderDetail.getUnitPrice());
                orderDetailEntity.setQuantityPurchased(orderDetail.getQuantityPurchased());

                orderDetailEntityList.add(orderDetailEntity);
                orderEntity.getOrderDetails().add(orderDetailEntity);
            }

        }

        orderEntity.setOrderDetails(orderDetailEntityList);
        return orderDao.add(orderEntity);
    }

    @Override
    public List<Order> getAll() throws Exception {
        List<OrderEntity> orderEntityList = orderDao.getAll();
        List<Order> orderList = new ArrayList<>();
        for (OrderEntity orderEntity:orderEntityList){
            Order map = new ModelMapper().map(orderEntity, Order.class);
            orderList.add(map);
        }

        return orderList;
    }

    @Override
    public ObservableList<Integer> getIds() throws Exception {
        return orderDao.getIds();
    }

    @Override
    public Order search(Integer id) throws Exception {
        OrderEntity search = orderDao.search(id);
        return new ModelMapper().map(search, Order.class);
    }
}
