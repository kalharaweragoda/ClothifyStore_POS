package controller.supplier;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import util.Navigator;

import java.io.IOException;
import java.net.URL;

public class AdminDashboardWindowController {


    @FXML
    private AnchorPane ancPaneLoadContainer;

    @FXML
    private Button btnLoadChangeEmailForm;

    @FXML
    private Button btnLoadEmployeeForm;

    @FXML
    private Button btnLoadViewReports;

    @FXML
    private Button btnLoadModifyEmployee;


    @FXML
    void btnLoadEmployeeFormOnAction(ActionEvent event) {
        URL resource = this.getClass().getResource("/view/employee_form.fxml");

        assert resource!=null;

        try {
            FXMLLoader loader = new FXMLLoader(resource);
            loader.setControllerFactory(Navigator.getInjector()::getInstance); // Guice injects controller
            Parent load = loader.load();

            ancPaneLoadContainer.getChildren().clear();
            ancPaneLoadContainer.getChildren().add(load);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnLoadViewReportsOnAction(ActionEvent event) {
        URL resource = this.getClass().getResource("/view/view_reports.fxml");

        assert resource!=null;

        try {
            FXMLLoader loader = new FXMLLoader(resource);
            loader.setControllerFactory(Navigator.getInjector()::getInstance); // Guice injects controller
            Parent load = loader.load();

            ancPaneLoadContainer.getChildren().clear();
            ancPaneLoadContainer.getChildren().add(load);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnLoadModifyEmployeeOnAction(ActionEvent actionEvent) {
        URL resource = this.getClass().getResource("/view/modify_employees_form.fxml");

        assert resource!=null;

        try {
            FXMLLoader loader = new FXMLLoader(resource);
            loader.setControllerFactory(Navigator.getInjector()::getInstance); // Guice injects controller
            Parent load = loader.load();

            ancPaneLoadContainer.getChildren().clear();
            ancPaneLoadContainer.getChildren().add(load);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
