package controller.supplier;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dto.Employee;
import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;
import org.jasypt.util.text.BasicTextEncryptor;
import service.custom.EmployeeService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class LoginFormController {

    @FXML
    private Button btnLogin;

    @FXML
    private Hyperlink hyperRegisterHere;

    @FXML
    private Hyperlink hyperResetPassword;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXPasswordField txtPassword;

    @Inject
    EmployeeService employeeService;

    @FXML
    void btnLoginOnAction(ActionEvent event) {
        String emailText = txtEmail.getText();
        String passwordText = txtPassword.getText();
        if (emailText.isEmpty() || passwordText.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "all fields must be filled").show();
        } else {
            try {
                Employee employee = employeeService.search(emailText);
                if (employee == null) {
                    new Alert(Alert.AlertType.ERROR, "employee not found, Check email and try again.").show();
                } else {
                    BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
                    basicTextEncryptor.setPassword(employee.getEmail());
                    String decrypted = basicTextEncryptor.decrypt(employee.getPassword());
                    if (decrypted.equals(passwordText)) {
                        Stage stage = new Stage();
                        stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/user_dashboard_window.fxml")))));
                        stage.show();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "invalid credentials").show();
                    }
                }

            } catch (SQLException | IOException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    @FXML
    void hyperRegisterHereOnAction(ActionEvent event) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/admin_login_form.fxml")))));
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        stage.show();
    }

    @FXML
    void hyperResetPasswordOnAction(ActionEvent event) {

    }

}
