package controller.order;

import com.jfoenix.controls.JFXTextField;
import dto.*;
import jakarta.inject.Inject;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import service.custom.CustomerService;
import service.custom.EmployeeService;
import service.custom.OrderService;
import service.custom.ProductService;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;

public class OrderFormController implements Initializable {

    @FXML
    private Button btnAddToCart;

    @FXML
    private Button btnPlaceOrder;

    @FXML
    private ComboBox cmbCustomerIds;

    @FXML
    private ComboBox cmbEmployeeIds;

    @FXML
    private ComboBox cmbPaymentType;

    @FXML
    private ComboBox cmbProductCode;

    @FXML
    private TableColumn<?, ?> colProductCode;

    @FXML
    private TableColumn<?, ?> colProductDescription;

    @FXML
    private TableColumn<?, ?> colQuantity;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblNetTotalHeading;

    @FXML
    private Label lblTime;

    @FXML
    private TableView tblOrders;

    @FXML
    private JFXTextField txtEmployeeName;

    @FXML
    private JFXTextField txtProductDescription;

    @FXML
    private JFXTextField txtQuantityInStock;

    @FXML
    private JFXTextField txtQuantityPurchased;

    @FXML
    private JFXTextField txtUnitPrice;

    @Inject
    EmployeeService employeeService;

    @Inject
    CustomerService customerService;

    @Inject
    ProductService productService;

    @Inject
    OrderService orderService;


    ObservableList<CartTM> cartDataList = FXCollections.observableArrayList();

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        loadData();
        calcNetTotal();
    }

    private void calcNetTotal() {
        Double total = 0.0;
        for (CartTM cartData : cartDataList) {
            total += cartData.getTotal();
        }
        lblNetTotal.setText(String.valueOf(total));
    }

    private void loadData() {
        String descriptionText = txtProductDescription.getText();


        if (cmbCustomerIds.getSelectionModel().isEmpty() || cmbEmployeeIds.getSelectionModel().isEmpty() || cmbProductCode.getSelectionModel().isEmpty() || txtQuantityPurchased.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "all fields must be filled").show();
        } else {
            try {
                Integer productCode = Integer.parseInt(String.valueOf(cmbProductCode.getValue()));
                double unitPrice = Double.parseDouble(txtUnitPrice.getText());
                int quantityPurchased = Integer.parseInt(txtQuantityPurchased.getText());
                Double totalPrice = unitPrice * quantityPurchased;

                if (quantityPurchased > Integer.parseInt(txtQuantityInStock.getText())) {
                    new Alert(Alert.AlertType.ERROR, "cannot purchase more than qty in stock").show();
                    return;
                }

                if (quantityPurchased <= 0) {
                    new Alert(Alert.AlertType.ERROR, "quantity purchased has to be at least 1").show();
                    return;
                }

                txtQuantityInStock.setText(String.valueOf(Integer.parseInt(txtQuantityInStock.getText()) - quantityPurchased));

                CartTM cartTM = new CartTM(productCode, descriptionText, unitPrice, quantityPurchased, totalPrice);
                CartTM cartData = checkItem(cartTM);
                if (cartData != null) {
                    cartDataList.remove(cartData);
                    cartData.setQuantity(cartData.getQuantity() + cartTM.getQuantity());
                    cartData.setTotal(cartData.getTotal() + cartTM.getTotal());
                    cartDataList.add(cartData);
                } else {
                    cartDataList.add(cartTM);
                }

                tblOrders.setItems(cartDataList);
                colProductCode.setCellValueFactory(new PropertyValueFactory<>("productCode"));
                colProductDescription.setCellValueFactory(new PropertyValueFactory<>("productDescription"));
                colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
                colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
                colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

        }
    }

    private CartTM checkItem(CartTM cartTMItem) {
        for (CartTM cartData : cartDataList) {
            if (cartData.getProductCode().equals(cartTMItem.getProductCode())) {
                return cartData;
            }
        }
        return null;
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        if (cmbPaymentType.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "payment type has to be given").show();
        } else {
            try {
                String dateText = lblDate.getText();
                Integer employeeId = Integer.parseInt(String.valueOf(cmbEmployeeIds.getValue()));
                String employeeNameText = txtEmployeeName.getText();
                double netTotal = Double.parseDouble(lblNetTotal.getText());
                String paymentType = String.valueOf(cmbPaymentType.getValue());

                Customer customer = customerService.search(Integer.parseInt(String.valueOf(cmbCustomerIds.getValue())));
                List<OrderDetail> orderDetailList = new ArrayList<>();


                Order order = new Order();


                order.setDate(dateText);
                order.setEmployeeId(employeeId);
                order.setEmployeeName(employeeNameText);
                order.setCustomerId(customer.getId());
                order.setTotal(netTotal);
                order.setPaymentType(paymentType);

                cartDataList.forEach(cartTM -> {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setProduct(productService.search(cartTM.getProductCode()));
                    orderDetail.setOrder(order);
                    orderDetail.setUnitPrice(cartTM.getUnitPrice());
                    orderDetail.setQuantityPurchased(cartTM.getQuantity());
                    orderDetailList.add(
                            orderDetail);
                });

                order.setOrderDetailList(orderDetailList);

                boolean isOrderPlaced = orderService.add(order);
                if (isOrderPlaced) {
                    txtQuantityPurchased.setText("");
                    new Alert(Alert.AlertType.INFORMATION, "order placed successfully").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "order not placed").show();
                }

            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }


        }
    }

    @FXML
    void cmbShowCustomerIdsOnAction(ActionEvent event) {

    }

    @FXML
    void cmbShowEmployeeIdsOnAction(ActionEvent event) {

    }

    @FXML
    void cmbShowPaymentTypeOnAction(ActionEvent event) {

    }

    @FXML
    void cmbShowProductCodes(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTimeAndDate();
        loadCustomerIds();
        loadEmployeeIds();
        loadProductCodes();
        loadPaymentType();


        cmbProductCode.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                searchProductData(newValue.toString());
            }
        });

        cmbEmployeeIds.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                searchEmployeeData(newValue.toString());
            }
        });

    }

    private void searchProductData(String prodCode) {
        Product product = productService.search(Integer.parseInt(prodCode));
        txtProductDescription.setText(product.getDescription());
        txtUnitPrice.setText(String.valueOf(product.getUnitPrice()));


        String stockInDb = String.valueOf(product.getQuantityInStock());

        ObservableList<CartTM> items = tblOrders.getItems();
        for (CartTM cartItem : items) {
            if (cartItem.getProductCode().equals(Integer.parseInt(prodCode))) {
                txtQuantityInStock.setText(String.valueOf(Integer.parseInt(stockInDb) - cartItem.getQuantity()));
                return;
            }
        }
        txtQuantityInStock.setText(stockInDb);
    }

    private void searchEmployeeData(String employeeId) {
        try {
            Employee employee = employeeService.search(Integer.parseInt(employeeId));
            txtEmployeeName.setText(employee.getName());
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void loadEmployeeIds() {
        try {
            ObservableList<Integer> ids = employeeService.getIds();
            cmbEmployeeIds.setItems(ids);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void loadProductCodes() {
        try {
            ObservableList<Integer> ids = productService.getIds();
            cmbProductCode.setItems(ids);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void loadCustomerIds() {
        try {
            ObservableList<Integer> ids = customerService.getIds();
            cmbCustomerIds.setItems(ids);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void loadPaymentType() {
        ObservableList<String> paymentTypeObservableList = FXCollections.observableArrayList();
        paymentTypeObservableList.addAll(Arrays.asList("cash", "card"));
        cmbPaymentType.setItems(paymentTypeObservableList);
    }

    private void setTimeAndDate() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(date);
        lblDate.setText(format);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> {
                    LocalTime now = LocalTime.now();
                    lblTime.setText(padWithZeros(now.getHour(), now.getMinute(), now.getSecond()));
                }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private String padWithZeros(int hour, int min, int sec) {
        String newHour = String.valueOf(hour).length() == 1 ? "0" + hour : String.valueOf(hour);
        String newMin = String.valueOf(min).length() == 1 ? "0" + min : String.valueOf(min);
        String newSec = String.valueOf(sec).length() == 1 ? "0" + sec : String.valueOf(sec);
        return newHour + ":" + newMin + ":" + newSec;
    }

}
