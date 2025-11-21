package service.custom.impl;

import dto.Customer;
import entity.CustomerEntity;
import jakarta.inject.Inject;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;
import repository.custom.CustomerDao;
import service.custom.CustomerService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    @Inject
    CustomerDao customerDao;

    @Override
    public boolean add(Customer customer) throws SQLException {
        CustomerEntity customerEntity = new ModelMapper().map(customer, CustomerEntity.class);
        customerEntity.setId(null);
        return customerDao.add(customerEntity);
    }

    @Override
    public Customer search(Integer id) throws SQLException {
        CustomerEntity customerEntity = customerDao.search(id);
        return new ModelMapper().map(customerEntity, Customer.class);
    }

    @Override
    public List<Customer> getAll() throws SQLException {
        List<CustomerEntity> customerEntityList = customerDao.getAll();
        List<Customer> customerList = new ArrayList<>();
        customerEntityList.forEach(customerEntity -> customerList.add(new ModelMapper().map(customerEntity, Customer.class)));
        return  customerList;
    }

    @Override
    public ObservableList<Integer> getIds() throws SQLException {
        return customerDao.getIds();
    }
}
