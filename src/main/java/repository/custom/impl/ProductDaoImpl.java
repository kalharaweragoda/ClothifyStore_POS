package repository.custom.impl;

import config.HibernateConfig;
import entity.ProductDetailEntity;
import entity.ProductDetailsId;
import entity.ProductEntity;
import entity.SupplierEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.hibernate.Session;
import org.hibernate.Transaction;
import repository.custom.ProductDao;

import java.sql.SQLException;
import java.util.List;

public class ProductDaoImpl implements ProductDao {

    @Override
    public boolean add(ProductEntity productEntity) throws SQLException {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSession()) {
            transaction = session.beginTransaction();

            SupplierEntity supplierEntity = session.find(SupplierEntity.class, productEntity.getSupplierId());

            session.persist(productEntity);
            session.flush();

            ProductDetailEntity productDetailEntity = new ProductDetailEntity(
                    supplierEntity, productEntity, productEntity.getUnitPrice(), productEntity.getQuantityInStock()
            );

            session.persist(productDetailEntity);
            transaction.commit();
            return true;

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Rollback Transaction on Failure
            }
            new Alert(Alert.AlertType.ERROR, "Error adding product: " + e.getMessage()).show();
            return false;
        }
    }

    @Override
    public List<ProductEntity> getAll() throws SQLException {
        try (Session session = HibernateConfig.getSession()) {
            return session.createQuery("FROM ProductEntity", ProductEntity.class).list();
        }
    }


    @Override
    public ObservableList<Integer> getIds() throws SQLException {
        ObservableList<Integer> productIds = FXCollections.observableArrayList();
        List<ProductEntity> productEntityList = getAll();
        productEntityList.forEach(productEntity -> productIds.add(productEntity.getCode()));
        return productIds;
    }

    @Override
    public ProductEntity search(Integer code) {
        Transaction transaction = null;
        ProductEntity productEntity = null;
        try (Session session = HibernateConfig.getSession()) {
            transaction = session.beginTransaction();
            productEntity = session.get(ProductEntity.class, code);
            transaction.commit();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return productEntity;
    }

    @Override
    public boolean update(ProductEntity productEntity) throws RuntimeException {
        Transaction transaction = null;
        try {
            Session session = HibernateConfig.getSession();  // Do not auto-close
            transaction = session.beginTransaction();

            ProductEntity existingProduct = session.get(ProductEntity.class, productEntity.getCode());

            if (existingProduct == null) {
                throw new RuntimeException("Product not found for code: " + productEntity.getCode());
            }

            ProductDetailsId productDetailId = new ProductDetailsId();
            productDetailId.setProdCode(existingProduct.getCode());
            productDetailId.setSupId(existingProduct.getSupplierId());

            ProductDetailEntity productDetailEntity = session.get(ProductDetailEntity.class, productDetailId);
            productDetailEntity.setQtySupplied(productEntity.getQuantityInStock());
            productDetailEntity.setUnitPrice(productEntity.getUnitPrice());

            existingProduct.setDescription(productEntity.getDescription());
            existingProduct.setCategory(productEntity.getCategory());
            existingProduct.setSize(productEntity.getSize());
            existingProduct.setUnitPrice(productEntity.getUnitPrice());
            existingProduct.setQuantityInStock(productEntity.getQuantityInStock());

            session.merge(existingProduct);  // Ensure it's within an open session

            transaction.commit();
            session.close();  // Manually close after committing
            return true;
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            return false;
        }
    }

    @Override
    public boolean delete(Integer code) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSession()) {
            transaction = session.beginTransaction();

            // Fetch product entity inside the same session
            ProductEntity productEntity = session.get(ProductEntity.class, code);
            if (productEntity == null) {
                return false; // No entity to delete
            }

            //Create composite key for ProductDetailEntity
            ProductDetailsId productDetailId = new ProductDetailsId();
            productDetailId.setProdCode(productEntity.getCode());
            productDetailId.setSupId(productEntity.getSupplierId());

            // Fetch and delete ProductDetailEntity
            ProductDetailEntity productDetailEntity = session.get(ProductDetailEntity.class, productDetailId);
            if (productDetailEntity != null) {
                session.remove(productDetailEntity);
            }

            // Remove product entity
            session.remove(productEntity);
            transaction.commit();
            return true;

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            return false;
        }
    }

}
