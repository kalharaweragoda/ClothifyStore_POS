package repository.custom;

import entity.EmployeeEntity;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeDao {
    boolean add(EmployeeEntity employeeEntity) throws SQLException;
    EmployeeEntity search(String email) throws SQLException;
    List<EmployeeEntity> getAll() throws SQLException;
    boolean update(EmployeeEntity employeeEntity) throws SQLException;
    boolean delete(String email) throws SQLException;
    ObservableList<Integer> getIds() throws SQLException;
    EmployeeEntity search(Integer id) throws SQLException;
}
