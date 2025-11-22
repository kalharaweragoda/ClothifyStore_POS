package controller.customer;

import com.jfoenix.controls.JFXTextField;
import dto.Customer;
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
import service.custom.CustomerService;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddCustomerFormController {

    @FXML
    private Button btnAddCustomer;

    @FXML
    private Button btnReload;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPhoneNo;

    @FXML
    private TableView tblCustomers;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPhoneNo;

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Inject
    CustomerService customerService;

    @FXML
    void btnAddCustomerOnAction(ActionEvent event) {
        String nameText = txtName.getText().toLowerCase();
        String emailText = txtEmail.getText().toLowerCase();
        String phoneNoText = txtPhoneNo.getText().toLowerCase();

        if(nameText.isEmpty()||emailText.isEmpty()||phoneNoText.isEmpty()){
            new Alert(Alert.AlertType.ERROR,"all fields must be filled").show();
        }else if (!validateEmail(emailText)) {
            new Alert(Alert.AlertType.ERROR, "not a valid email address").show();
        }else{
            Customer customer = new Customer(1, nameText, emailText, phoneNoText);
            try {
                boolean isAddedCustomer = customerService.add(customer);
                if(isAddedCustomer){
                    new Alert(Alert.AlertType.INFORMATION,"customer added successfully").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
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
        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();
        try {
            List<Customer> customerList = customerService.getAll();
            customerObservableList.addAll(customerList);

            tblCustomers.setItems(customerObservableList);

            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            colPhoneNo.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }
}
