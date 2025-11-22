package service.custom;

import dto.Employee;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeService {
    boolean add(Employee employee) throws SQLException;
    Employee search(String email) throws SQLException;
    List<Employee> getAll() throws SQLException;
    boolean update(Employee employee) throws SQLException;
    boolean delete(String email) throws SQLException;
    ObservableList<Integer> getIds() throws SQLException;
    Employee search(Integer id) throws SQLException;
}
