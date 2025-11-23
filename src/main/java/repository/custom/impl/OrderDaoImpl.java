package repository.custom.impl;

import config.HibernateConfig;
import entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.hibernate.Session;
import org.hibernate.Transaction;
import repository.custom.OrderDao;

import java.util.List;

public class OrderDaoImpl implements OrderDao {
    @Override
    public boolean add(OrderEntity orderEntity) throws Exception {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSession()) {
            transaction = session.beginTransaction();


            session.persist(orderEntity);
            session.flush();

            for (OrderDetailEntity orderDetailEntity : orderEntity.getOrderDetails()) {

                ProductEntity productEntity = session.get(ProductEntity.class,
                        orderDetailEntity.getProductEntity().getCode());


                orderDetailEntity.setOrderEntity(orderEntity);
                orderDetailEntity.setProductEntity(productEntity);


                orderDetailEntity.setId(new OrderDetailsId(orderEntity.getId(), productEntity.getCode()));


                session.merge(orderDetailEntity);


                ProductDetailsId productDetailId = new ProductDetailsId();
                productDetailId.setProdCode(productEntity.getCode());
                productDetailId.setSupId(productEntity.getSupplierId());

                ProductDetailEntity productDetailEntity = session.get(ProductDetailEntity.class, productDetailId);
                productDetailEntity.setQtySupplied(productEntity.getQuantityInStock() - orderDetailEntity.getQuantityPurchased());
                productDetailEntity.setUnitPrice(productEntity.getUnitPrice());

                productEntity.setDescription(productEntity.getDescription());
                productEntity.setCategory(productEntity.getCategory());
                productEntity.setSize(productEntity.getSize());
                productEntity.setUnitPrice(productEntity.getUnitPrice());
                productEntity.setQuantityInStock(productEntity.getQuantityInStock() - orderDetailEntity.getQuantityPurchased());

                session.merge(productEntity);  // Ensure it's within an open session

            }

            transaction.commit();
            return true;

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            new Alert(Alert.AlertType.ERROR, "Error adding orders: " + e.getMessage()).show();
            return false;
        }
    }

    @Override
    public List<OrderEntity> getAll() throws Exception {
        try (Session session = HibernateConfig.getSession()) {
            return session.createQuery("FROM OrderEntity", OrderEntity.class).list();
        }
    }

    @Override
    public ObservableList<Integer> getIds() throws Exception {
        ObservableList<Integer> orderIds = FXCollections.observableArrayList();
        List<OrderEntity> orderEntityList = getAll();
        orderEntityList.forEach(orderEntity -> orderIds.add(orderEntity.getId()));
        return orderIds;
    }

    @Override
    public OrderEntity search(Integer id) throws Exception {
        try (Session session = HibernateConfig.getSession()) {
            return session.get(OrderEntity.class, id);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error finding order: " + e.getMessage()).show();
            return null;
        }
    }
}
