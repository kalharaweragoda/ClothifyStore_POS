package repository.custom.impl;

import config.HibernateConfig;
import entity.CustomerEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.hibernate.Session;
import org.hibernate.Transaction;
import repository.custom.CustomerDao;

import java.sql.SQLException;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
    @Override
    public boolean add(CustomerEntity customerEntity) throws SQLException {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSession()) {
            transaction = session.beginTransaction();
            session.persist(customerEntity);  // Recommended for Hibernate 6+
            transaction.commit();
            return true;
        }  catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            return false;
        }
    }

    @Override
    public CustomerEntity search(Integer id) throws SQLException {
        Transaction transaction = null;
        CustomerEntity customerEntity = null;
        try (Session session = HibernateConfig.getSession()) {
            transaction = session.beginTransaction();
            customerEntity = session.get(CustomerEntity.class, id);
            transaction.commit();
        }  catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();

        }
        return customerEntity;
    }

    @Override
    public List<CustomerEntity> getAll() throws SQLException {
        try (Session session = HibernateConfig.getSession()) {
            return session.createQuery("FROM CustomerEntity", CustomerEntity.class).list();
        }
    }

    @Override
    public ObservableList<Integer> getIds() throws SQLException {
        ObservableList<Integer> customerIds = FXCollections.observableArrayList();
        List<CustomerEntity> customerEntityList = getAll();
        customerEntityList.forEach(customerEntity -> customerIds.add(customerEntity.getId()));
        return customerIds;
    }
}
