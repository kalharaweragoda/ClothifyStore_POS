package controller.employee;

import com.jfoenix.controls.JFXTextField;
import dto.Employee;
import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.jasypt.util.text.BasicTextEncryptor;
import service.custom.EmployeeService;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeeFormController{

    @FXML
    private Button btnAdd;


    @FXML
    private Button btnReload;


    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPhoneNo;

    @FXML
    private TableView tblEmployees;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtConfirmPassword;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPassword;

    @FXML
    private JFXTextField txtPhoneNo;

    @Inject
    EmployeeService employeeService;

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @FXML
    void btnAddOnAction(ActionEvent event) {
        String nameText = txtName.getText().toLowerCase();
        String addressText = txtAddress.getText().toLowerCase();
        String emailText = txtEmail.getText().toLowerCase();
        String phoneNoText = txtPhoneNo.getText().toLowerCase();
        String passwordText = txtPassword.getText().toLowerCase();
        String confirmPasswordText = txtConfirmPassword.getText().toLowerCase();
        if(nameText.isEmpty()||addressText.isEmpty()||emailText.isEmpty()||phoneNoText.isEmpty()||passwordText.isEmpty()||confirmPasswordText.isEmpty()){
            new Alert(Alert.AlertType.ERROR,"all fields must be filled").show();
        }else{
            if(!validateEmail(emailText)){
                new Alert(Alert.AlertType.ERROR,"not a valid email address").show();
            }else if(!passwordText.equals(confirmPasswordText)){
                new Alert(Alert.AlertType.ERROR,"password fields do not match").show();
            }else{
                BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
                basicTextEncryptor.setPassword(emailText);
                String encrypted = basicTextEncryptor.encrypt(passwordText);
                Employee employee = new Employee(1,nameText, addressText, emailText, phoneNoText, encrypted);
                try {

                    boolean isAddedEmployee = employeeService.add(employee);
                    if(isAddedEmployee){
                        new Alert(Alert.AlertType.INFORMATION,"employee added successfully").show();
                    }else{
                        new Alert(Alert.AlertType.ERROR,"failed to add employee, try again later.").show();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
                }
            }
        }
    }

    private boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.matches();
    }


    @FXML
    void btnReloadOnAction(ActionEvent event) {
        loadData();
    }

    private void loadData(){
        ObservableList<Employee> employeeObservableList = FXCollections.observableArrayList();
        try {
            List<Employee> employeeList = employeeService.getAll();
            employeeObservableList.addAll(employeeList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        tblEmployees.setItems(employeeObservableList);
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhoneNo.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));
    }


}
