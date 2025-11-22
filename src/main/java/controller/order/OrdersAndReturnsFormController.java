package controller.order;

import dto.Order;
import dto.OrderReturn;
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
import service.custom.OrderReturnService;
import service.custom.OrderService;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class OrdersAndReturnsFormController implements Initializable {

    @FXML
    private Button btnReload;

    @FXML
    private Button btnReloadOrderReturns;

    @FXML
    private Button btnReturn;

    @FXML
    private ComboBox cmbOrderId;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDateOR;

    @FXML
    private TableColumn<?, ?> colEmployeeId;

    @FXML
    private TableColumn<?, ?> colEmployeeName;

    @FXML
    private TableColumn<?, ?> colOrderId;

    @FXML
    private TableColumn<?, ?> colOrderIdOR;

    @FXML
    private TableColumn<?, ?> colPaymentType;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private TableView tblOrderReturns;

    @FXML
    private TableView tblViewAllOrders;

    @Inject
    OrderService orderService;

    @Inject
    OrderReturnService orderReturnService;


    @FXML
    void btnReloadOnAction(ActionEvent event) {
        ObservableList<Order> ordersObservableList = FXCollections.observableArrayList();
        try {

            List<Order> allOrders = orderService.getAll();

            ordersObservableList.addAll(allOrders);

            tblViewAllOrders.setItems(ordersObservableList);
            colOrderId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
            colEmployeeName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
            colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
            colPaymentType.setCellValueFactory(new PropertyValueFactory<>("paymentType"));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnReloadOrderReturnsOnAction(ActionEvent event) {
        ObservableList<OrderReturn> orderReturnObservableList = FXCollections.observableArrayList();
        try {
            orderReturnObservableList.addAll(orderReturnService.getAll());
            tblOrderReturns.setItems(orderReturnObservableList);
            colOrderIdOR.setCellValueFactory(new PropertyValueFactory<>("orderId"));
            colDateOR.setCellValueFactory(new PropertyValueFactory<>("date"));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void btnReturnOnAction(ActionEvent event) {
        if (cmbOrderId.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "order has to be selected to return").show();
        } else if (checkIfOrderReturned(Integer.parseInt(String.valueOf(cmbOrderId.getValue())))) {
            new Alert(Alert.AlertType.ERROR, "order has already been returned").show();
        } else {
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "do you want to return this order?");
                Optional<ButtonType> buttonType = alert.showAndWait();
                if (buttonType.isPresent() && buttonType.get().getText().equals("OK")) {

                    Order searchedOrder = orderService.search(Integer.parseInt(String.valueOf(cmbOrderId.getValue())));
                    OrderReturn orderReturn = new OrderReturn(
                            searchedOrder.getId(),
                            searchedOrder.getId(),
                            lblDate.getText(),
                            searchedOrder.getEmployeeId(),
                            searchedOrder.getEmployeeName(),
                            searchedOrder.getCustomerId(),
                            searchedOrder.getTotal(),
                            searchedOrder.getPaymentType()
                    );

                    boolean isAddedOrderReturn = orderReturnService.add(orderReturn);
                    if (isAddedOrderReturn) {
                        new Alert(Alert.AlertType.INFORMATION, "order return has been successful").show();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "order return has been unsuccessful").show();
                    }

                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    private boolean checkIfOrderReturned(Integer id) {
        try {
            List<OrderReturn> all = orderReturnService.getAll();
            for(OrderReturn orderReturn: all){
                if (orderReturn.getOrderId().equals(id)){
                    return true;
                }
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return false;
    }

    @FXML
    void cmbShowOrderIdOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadOrderIds();
        setTimeAndDate();
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

    private void loadOrderIds() {
        try {
            List<Integer> ids = orderService.getIds();
            ObservableList<Integer> orderIds = FXCollections.observableArrayList();
            orderIds.addAll(ids);
            cmbOrderId.setItems(orderIds);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
