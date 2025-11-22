package service.custom.impl;

import dto.Employee;
import entity.EmployeeEntity;
import jakarta.inject.Inject;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;
import repository.custom.EmployeeDao;
import service.custom.EmployeeService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {
    @Inject
    EmployeeDao employeeDao;

    @Override
    public boolean add(Employee employee) throws SQLException {
        EmployeeEntity employeeEntity = new ModelMapper().map(employee, EmployeeEntity.class);
        employeeEntity.setId(null);
        return employeeDao.add(employeeEntity);
    }

    @Override
    public Employee search(String email) throws SQLException {
        EmployeeEntity searchedEmployeeEntity = employeeDao.search(email);
        if(searchedEmployeeEntity!=null){
            return new ModelMapper().map(searchedEmployeeEntity, Employee.class);
        }else{
            return null;
        }

    }

    @Override
    public List<Employee> getAll() throws SQLException {
        List<EmployeeEntity> employeeEntityList = employeeDao.getAll();
        List<Employee> employeeList = new ArrayList<>();
        for (EmployeeEntity employeeEntity:employeeEntityList){
            Employee map = new ModelMapper().map(employeeEntity, Employee.class);
            employeeList.add(map);
        }

        return employeeList;
    }

    @Override
    public boolean update(Employee employee) throws SQLException {
        EmployeeEntity employeeEntity = new ModelMapper().map(employee, EmployeeEntity.class);
        return employeeDao.update(employeeEntity);
    }

    @Override
    public boolean delete(String email) throws SQLException {
        return employeeDao.delete(email);
    }

    @Override
    public ObservableList<Integer> getIds() throws SQLException {
        return employeeDao.getIds();
    }

    @Override
    public Employee search(Integer id) throws SQLException {
        EmployeeEntity employeeEntity = employeeDao.search(id);
        return new ModelMapper().map(employeeEntity,Employee.class);
    }
}
