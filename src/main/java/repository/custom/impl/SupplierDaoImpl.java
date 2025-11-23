package repository.custom.impl;

import config.HibernateConfig;
import entity.SupplierEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.hibernate.Session;
import org.hibernate.Transaction;
import repository.custom.SupplierDao;

import java.sql.SQLException;
import java.util.List;

public class SupplierDaoImpl implements SupplierDao {
    @Override
    public boolean add(SupplierEntity supplierEntity) throws SQLException {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSession()) {
            transaction = session.beginTransaction();
            session.persist(supplierEntity);  // Recommended for Hibernate 6+
            transaction.commit();
            return true;
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            return false;
        }
    }

    @Override
    public SupplierEntity search(String email) throws SQLException {
        Transaction transaction = null;
        SupplierEntity supplierEntity = null;
        try (Session session = HibernateConfig.getSession()) {
            transaction = session.beginTransaction();

            // Find employee by email using HQL
            supplierEntity = session.createQuery("FROM SupplierEntity e WHERE e.email = :email", SupplierEntity.class)
                    .setParameter("email", email)
                    .uniqueResult();

            transaction.commit();

        }  catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();

        }
        return supplierEntity;
    }

    @Override
    public List<SupplierEntity> getAll() throws SQLException {
        try (Session session = HibernateConfig.getSession()) {
            return session.createQuery("FROM SupplierEntity", SupplierEntity.class).list();
        }
    }

    @Override
    public boolean update(SupplierEntity supplierEntity) throws Exception {
        Transaction transaction = null;
        try {
            Session session = HibernateConfig.getSession();  // Do not auto-close
            transaction = session.beginTransaction();

            SupplierEntity existingSupplier = session.get(SupplierEntity.class, supplierEntity.getId());

            if (existingSupplier == null) {
                throw new SQLException("Supplier not found for ID: " + supplierEntity.getId());
            }

            existingSupplier.setName(supplierEntity.getName());
            existingSupplier.setEmail(supplierEntity.getEmail());
            existingSupplier.setCompany(supplierEntity.getCompany());
            existingSupplier.setPhoneNo(supplierEntity.getPhoneNo());


            session.merge(existingSupplier);  // Ensure it's within an open session

            transaction.commit();
            session.close();  // Manually close after committing
            return true;
        }  catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            return false;
        }
    }

    @Override
    public boolean delete(String email) throws SQLException {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSession()) {
            transaction = session.beginTransaction();

            // Find employee by email using HQL
            SupplierEntity supplierEntity= session.createQuery("FROM SupplierEntity e WHERE e.email = :email", SupplierEntity.class)
                    .setParameter("email", email)
                    .uniqueResult();

            if (supplierEntity != null) {
                session.remove(supplierEntity);// Recommended for Hibernate 6+
                transaction.commit();
                return true;
            } else {
                return false;
            }
        }  catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            return false;
        }
    }

    @Override
    public ObservableList<Integer> getIds() throws SQLException {
        ObservableList<Integer> supplierIds = FXCollections.observableArrayList();
        List<SupplierEntity> supplierEntityList = getAll();
        supplierEntityList.forEach(supplier -> supplierIds.add(supplier.getId()));
        return supplierIds;
    }

    @Override
    public SupplierEntity search(Integer id) throws SQLException {
        Transaction transaction = null;
        SupplierEntity supplierEntity = null;
        try (Session session = HibernateConfig.getSession()) {
            transaction = session.beginTransaction();
            supplierEntity = session.get(SupplierEntity.class, id);
            transaction.commit();
        }  catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return supplierEntity;
    }
}
