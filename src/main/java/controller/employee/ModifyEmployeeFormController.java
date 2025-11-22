package controller.employee;

import com.jfoenix.controls.JFXTextField;
import dto.Employee;
import jakarta.inject.Inject;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import org.jasypt.util.text.BasicTextEncryptor;
import service.custom.EmployeeService;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyEmployeeFormController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox cmbEmployeeId;

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

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        try {
            if (cmbEmployeeId.getSelectionModel().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "employee must be selected from dropdown").show();
            } else {
                Employee employee = employeeService.search(txtEmail.getText());
                if (employee != null) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "do you want to delete this employee?");
                    Optional<ButtonType> buttonType = alert.showAndWait();
                    if (buttonType.isPresent() && buttonType.get().getText().equals("OK")) {
                        boolean isEmployeeDeleted = employeeService.delete(txtEmail.getText());
                        if (isEmployeeDeleted) {
                            new Alert(Alert.AlertType.INFORMATION, "employee has been deleted successfully").show();
                        } else {
                            new Alert(Alert.AlertType.ERROR, "some error has occurred, try again later.").show();
                        }

                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "employee not found.").show();
                }
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String nameText = txtName.getText().toLowerCase();
        String addressText = txtAddress.getText().toLowerCase();
        String emailText = txtEmail.getText().toLowerCase();
        String phoneNoText = txtPhoneNo.getText().toLowerCase();
        String passwordText = txtPassword.getText().toLowerCase();
        String confirmPasswordText = txtConfirmPassword.getText().toLowerCase();
        if (cmbEmployeeId.getSelectionModel().isEmpty() || nameText.isEmpty() || addressText.isEmpty() || emailText.isEmpty() || phoneNoText.isEmpty() || passwordText.isEmpty() || confirmPasswordText.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "all fields must be filled").show();
        } else {
            if (!passwordText.equals(confirmPasswordText)) {
                new Alert(Alert.AlertType.ERROR, "password fields do not match").show();
            } else {
                BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
                basicTextEncryptor.setPassword(emailText);
                String encrypted = basicTextEncryptor.encrypt(passwordText);
                Employee employee = new Employee(Integer.parseInt(String.valueOf(cmbEmployeeId.getValue())), nameText, addressText, emailText, phoneNoText, encrypted);
                try {
                    boolean isUpdatedEmployee = employeeService.update(employee);
                    if (isUpdatedEmployee) {
                        new Alert(Alert.AlertType.INFORMATION, "employee updated successfully").show();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "employee not updated. Try again later.").show();
                    }


                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }
            }

        }
    }

    @FXML
    void cmbShowEmployeeIdsOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadEmployeeIds();

        cmbEmployeeId.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                searchEmployeeData(newValue.toString());
            }
        });
    }

    private void searchEmployeeData(String employeeId) {
        try {
            Employee employee = employeeService.search(Integer.parseInt(employeeId));

            txtName.setText(employee.getName());
            txtAddress.setText(employee.getAddress());
            txtPhoneNo.setText(employee.getPhoneNo());
            txtEmail.setText(employee.getEmail());

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void loadEmployeeIds() {
        try {
            ObservableList<Integer> employeeIds = employeeService.getIds();
            cmbEmployeeId.setItems(employeeIds);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


}
