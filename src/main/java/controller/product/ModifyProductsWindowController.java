package controller.product;

import com.jfoenix.controls.JFXTextField;
import dto.Product;
import jakarta.inject.Inject;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import service.custom.ProductService;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyProductsWindowController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox cmbProductCode;

    @FXML
    private JFXTextField txtCategory;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtQuantity;

    @FXML
    private JFXTextField txtSize;

    @FXML
    private JFXTextField txtSupplierId;

    @FXML
    private JFXTextField txtUnitPrice;

    @Inject
    ProductService productService;

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        Product product = null;
        try {
            Integer productCode = Integer.parseInt(String.valueOf(cmbProductCode.getValue()));
            product = productService.search(productCode);
            if (product == null) {
                new Alert(Alert.AlertType.ERROR, "check product code and try again").show();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "do you want to delete this product?");
                Optional<ButtonType> buttonType = alert.showAndWait();
                if (buttonType.isPresent() && buttonType.get().getText().equals("OK")) {
                    boolean isDeletedProduct = productService.delete(product.getCode());
                    if (isDeletedProduct) {
                        new Alert(Alert.AlertType.INFORMATION, "product deleted successfully").show();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "some error has occurred, try again later.").show();
                    }

                }
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String descriptionText = txtDescription.getText().toLowerCase();
        String categoryText = txtCategory.getText().toLowerCase();
        String sizeText = txtSize.getText().toLowerCase();
        String unitPriceText = txtUnitPrice.getText().toLowerCase();
        String quantityText = txtQuantity.getText().toLowerCase();


        try{
            if (cmbProductCode.getSelectionModel().isEmpty()||descriptionText.isEmpty() || categoryText.isEmpty() || sizeText.isEmpty() || unitPriceText.isEmpty() || quantityText.isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "all fields must be filled").show();
            } else if (!categoryText.equals("ladies")&&!categoryText.equals("gents")&&!categoryText.equals("kids")) {
                new Alert(Alert.AlertType.ERROR, "category has to be ladies/gents/kids").show();
            } else if (!sizeText.equals("small")&&!sizeText.equals("medium")&&!sizeText.equals("large")) {
                new Alert(Alert.AlertType.ERROR, "size has to be small/medium/large").show();
            } else if (Integer.parseInt(quantityText)<=0 || Double.parseDouble(unitPriceText)<=0) {
                new Alert(Alert.AlertType.ERROR, "quantity and unit price has to be greater than 0").show();
            } else {
                try {
                    Double unitPriceValue = Double.parseDouble(unitPriceText);
                    Integer quantityTextValue = Integer.parseInt(quantityText);
                    Integer productCode = Integer.parseInt(String.valueOf(cmbProductCode.getValue()));

                    Product product = new Product(productCode,descriptionText,categoryText,sizeText,unitPriceValue,quantityTextValue,Integer.parseInt(txtSupplierId.getText()),new HashSet<>(), new ArrayList<>());

                    Product searchedProduct = productService.search(productCode);
                    if (searchedProduct == null) {
                        new Alert(Alert.AlertType.ERROR, "check code and try again later.").show();
                    } else {
                        boolean isUpdatedProduct = productService.update(product);
                        if (isUpdatedProduct) {
                            new Alert(Alert.AlertType.INFORMATION, "product updated successfully").show();
                        } else {
                            new Alert(Alert.AlertType.ERROR, "product not updated. Try again later.").show();
                        }
                    }

                } catch (NumberFormatException e){
                    new Alert(Alert.AlertType.ERROR,"quantity must be an integer, unit price must be a double").show();
                }


            }
        }catch (NumberFormatException e){
            new Alert(Alert.AlertType.ERROR,"quantity must be an integer, unit price must be a double").show();
        }

    }

    @FXML
    void cmbShowProductCodesOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadProductIds();

        cmbProductCode.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) ->{
            if(newValue!=null){
                searchProductData(String.valueOf(newValue));
            }
        } );
    }

    private void searchProductData(String code){
        Product product = productService.search(Integer.parseInt(code));
        if(product!=null){
            txtDescription.setText(product.getDescription());
            txtCategory.setText(product.getCategory());
            txtSize.setText(product.getSize());
            txtUnitPrice.setText(String.valueOf(product.getUnitPrice()));
            txtQuantity.setText(String.valueOf(product.getQuantityInStock()));
            txtSupplierId.setText(String.valueOf(product.getSupplierId()));
        }
    }

    private void loadProductIds(){
        try {
            ObservableList<Integer> ids = productService.getIds();
            cmbProductCode.setItems(ids);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

}
