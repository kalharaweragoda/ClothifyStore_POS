package repository.custom.impl;

import config.HibernateConfig;
import entity.*;
import javafx.scene.control.Alert;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.custom.OrderReturnDao;

import java.util.List;

public class OrderReturnDaoImpl implements OrderReturnDao {
    @Override
    public boolean add(OrderReturnEntity orderReturnEntity) throws HibernateException {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSession()) {
            transaction=session.beginTransaction();
            orderReturnEntity.setId(null);

            session.persist(orderReturnEntity);
            session.flush();

            String hql = "FROM OrderDetailEntity od WHERE od.orderEntity.id = :orderId";
            Query<OrderDetailEntity> query = session.createQuery(hql, OrderDetailEntity.class);
            query.setParameter("orderId", orderReturnEntity.getId());

            List<OrderDetailEntity> resultList = query.getResultList();

            for(OrderDetailEntity orderDetailEntity:resultList){
                ProductEntity productEntity = session.get(ProductEntity.class,
                        orderDetailEntity.getProductEntity().getCode());

                ProductDetailsId productDetailId = new ProductDetailsId();
                productDetailId.setProdCode(productEntity.getCode());
                productDetailId.setSupId(productEntity.getSupplierId());

                ProductDetailEntity productDetailEntity = session.get(ProductDetailEntity.class, productDetailId);
                productDetailEntity.setQtySupplied(productEntity.getQuantityInStock() + orderDetailEntity.getQuantityPurchased());
                productDetailEntity.setUnitPrice(productEntity.getUnitPrice());


                productEntity.setDescription(productEntity.getDescription());
                productEntity.setCategory(productEntity.getCategory());
                productEntity.setSize(productEntity.getSize());
                productEntity.setUnitPrice(productEntity.getUnitPrice());
                productEntity.setQuantityInStock(productEntity.getQuantityInStock() + orderDetailEntity.getQuantityPurchased());

                session.merge(productEntity);  // Ensure it's within an open session

            }
            transaction.commit();
            return true;
        } catch (Exception e) {
            if(transaction!=null){
                transaction.rollback();
            }
            new Alert(Alert.AlertType.ERROR, "Error adding orderreturn: " + e.getMessage()).show();
            return false;
        }
    }

    @Override
    public List<OrderReturnEntity> getAll() throws Exception {
        try (Session session = HibernateConfig.getSession()) {
            return session.createQuery("FROM OrderReturnEntity", OrderReturnEntity.class).list();
        }
    }

}
