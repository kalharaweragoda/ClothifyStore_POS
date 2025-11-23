package repository.custom.impl;

import config.HibernateConfig;
import entity.EmployeeEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.hibernate.Session;
import org.hibernate.Transaction;
import repository.custom.EmployeeDao;

import java.sql.SQLException;
import java.util.List;

public class EmployeeDaoImpl implements EmployeeDao {
    @Override
    public boolean add(EmployeeEntity employeeEntity) throws SQLException {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSession()) {
            transaction = session.beginTransaction();
            session.persist(employeeEntity);  // Recommended for Hibernate 6+
            transaction.commit();
            return true;
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            return false;
        }
    }

    @Override
    public EmployeeEntity search(String email) throws SQLException {
        Transaction transaction = null;
        EmployeeEntity employeeEntity = null;
        try (Session session = HibernateConfig.getSession()) {
            transaction = session.beginTransaction();

            // Find employee by email using HQL
            employeeEntity = session.createQuery("FROM EmployeeEntity e WHERE e.email = :email", EmployeeEntity.class)
                    .setParameter("email", email)
                    .uniqueResult();

            transaction.commit();

        }  catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();

        }
        return employeeEntity;
    }

    @Override
    public List<EmployeeEntity> getAll() throws SQLException {
        try (Session session = HibernateConfig.getSession()) {
            return session.createQuery("FROM EmployeeEntity", EmployeeEntity.class).list();
        }
    }

    @Override
    public boolean update(EmployeeEntity employeeEntity) throws SQLException {
        Transaction transaction = null;
        try {
            Session session = HibernateConfig.getSession();  // Do not auto-close
            transaction = session.beginTransaction();

            EmployeeEntity existingEmployee = session.get(EmployeeEntity.class, employeeEntity.getId());

            if (existingEmployee == null) {
                throw new SQLException("Employee not found for ID: " + employeeEntity.getId());
            }

            existingEmployee.setName(employeeEntity.getName());
            existingEmployee.setEmail(employeeEntity.getEmail());
            existingEmployee.setAddress(employeeEntity.getAddress());
            existingEmployee.setPhoneNo(employeeEntity.getPhoneNo());
            existingEmployee.setPassword(employeeEntity.getPassword());

            session.merge(existingEmployee);  // Ensure it's within an open session

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
            EmployeeEntity employeeEntity = session.createQuery("FROM EmployeeEntity e WHERE e.email = :email", EmployeeEntity.class)
                    .setParameter("email", email)
                    .uniqueResult();

            if (employeeEntity != null) {
                session.remove(employeeEntity);// Recommended for Hibernate 6+
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
        ObservableList<Integer> employeeIds = FXCollections.observableArrayList();
        List<EmployeeEntity> employeeEntityList = getAll();
        employeeEntityList.forEach(employeeEntity -> employeeIds.add(employeeEntity.getId()));
        return employeeIds;
    }

    @Override
    public EmployeeEntity search(Integer id) throws SQLException {
        Transaction transaction = null;
        EmployeeEntity employeeEntity = null;
        try (Session session = HibernateConfig.getSession()) {
            transaction = session.beginTransaction();
            employeeEntity = session.get(EmployeeEntity.class, id);
            transaction.commit();
        }  catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return employeeEntity;
    }


}
