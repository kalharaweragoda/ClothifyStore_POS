package controller.product;

import com.jfoenix.controls.JFXTextField;
import dto.Product;
import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import service.custom.ProductService;
import service.custom.SupplierService;

import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class AddProductsFormController implements Initializable {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnReload;

    @FXML
    private ComboBox cmbCategory;

    @FXML
    private ComboBox cmbSize;

    @FXML
    private ComboBox cmbSupplierId;

    @FXML
    private TableColumn<?, ?> colCategory;

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colQtyInStock;

    @FXML
    private TableColumn<?, ?> colSize;

    @FXML
    private TableColumn<?, ?> colSupplierId;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableView tblProducts;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtQuantity;

    @FXML
    private JFXTextField txtUnitPrice;

    @Inject
    SupplierService supplierService;

    @Inject
    ProductService productService;

    @FXML
    void btnAddOnAction(ActionEvent event) {
        String descriptionText = txtDescription.getText().trim().toLowerCase();
        String quantityText = txtQuantity.getText();
        String unitPriceText = txtUnitPrice.getText();
        String sizeValue = String.valueOf(cmbSize.getValue());
        String categoryValue = String.valueOf(cmbCategory.getValue());

        try{
            if(descriptionText.isEmpty()||quantityText.isEmpty()||unitPriceText.isEmpty()|| cmbSize.getSelectionModel().isEmpty()||cmbCategory.getSelectionModel().isEmpty()||cmbSupplierId.getSelectionModel().isEmpty()){
                new Alert(Alert.AlertType.ERROR,"all fields & combo boxes must be filled").show();
            } else if (Integer.parseInt(quantityText)<=0 || Double.parseDouble(unitPriceText)<=0) {
                new Alert(Alert.AlertType.ERROR,"unit price and quantity has to be greater than 0").show();
            } else{

                Product product = new Product();
                product.setDescription(descriptionText);
                product.setCategory(categoryValue);
                product.setSize(sizeValue);
                product.setUnitPrice(Double.parseDouble(unitPriceText));
                product.setQuantityInStock(Integer.parseInt(quantityText));
                product.setSupplierId(Integer.parseInt(String.valueOf(cmbSupplierId.getValue())));

                try {
                    boolean isAddedProduct = productService.add(product);
                    if(isAddedProduct){
                        new Alert(Alert.AlertType.INFORMATION,"product added successfully").show();
                    }else{
                        new Alert(Alert.AlertType.ERROR,"something went wrong, try again later.").show();
                    }


                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
                }

            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR,"quantity and unit price must be a number").show();
        }


    }

    @FXML
    void btnReloadOnAction(ActionEvent event) {
        loadData();
    }

    private void loadData(){
        ObservableList<Product> products = FXCollections.observableArrayList();
        try {
            List<Product> productList = productService.getAll();
            products.addAll(productList);
            tblProducts.setItems(products);

            colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
            colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
            colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
            colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
            colQtyInStock.setCellValueFactory(new PropertyValueFactory<>("quantityInStock"));
            colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }


    @FXML
    void cmbShowCategoryOnAction(ActionEvent event) {

    }

    @FXML
    void cmbShowSizeOnAction(ActionEvent event) {

    }

    @FXML
    void cmbShowSupplierIdOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCategoryValues();
        loadSizeValues();
        loadSupplierIdValues();
    }

    private void loadCategoryValues() {
        ObservableList<String> categoryObservableList = FXCollections.observableArrayList();
        categoryObservableList.addAll(Arrays.asList("ladies", "gents", "kids"));
        cmbCategory.setItems(categoryObservableList);
    }

    private void loadSizeValues() {
        ObservableList<String> categoryObservableList = FXCollections.observableArrayList();
        categoryObservableList.addAll(Arrays.asList("small", "medium", "large"));
        cmbSize.setItems(categoryObservableList);
    }

    private void loadSupplierIdValues(){
        try {
            ObservableList<Integer> supplierIds = supplierService.getIds();
            cmbSupplierId.setItems(supplierIds);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
}
