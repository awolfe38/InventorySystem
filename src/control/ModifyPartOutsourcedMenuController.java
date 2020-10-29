/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

/**
 * FXML Controller class
 *
 * @author ajw51
 */
public class ModifyPartOutsourcedMenuController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private ToggleGroup addPartTG;

    @FXML
    private Label idLbl;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField invTxt;

    @FXML
    private TextField priceTxt;

    @FXML
    private TextField maxTxt;

    @FXML
    private TextField minTxt;

    @FXML
    private TextField compTxt;

    @FXML
    void modifyOutsourceCompanyTxt(ActionEvent event) {

    }

    @FXML
    void modifyOutsourceInvTxt(ActionEvent event) {

    }

    @FXML
    void modifyOutsourceMaxTxt(ActionEvent event) {

    }

    @FXML
    void modifyOutsourceMinTxt(ActionEvent event) {

    }

    @FXML
    void modifyOutsourceNameTxt(ActionEvent event) {

    }

    @FXML
    void modifyOutsourcePriceTxt(ActionEvent event) {

    }

    @FXML
    void onActionDisplayMainMenu(ActionEvent event) throws IOException {
        //Goes back to the main screen without saving anything
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Data will be cleared and no part will be changed, do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        }
    }

    @FXML
    void onActionOutsourceSave(ActionEvent event) throws IOException {
        //saves the part
        int id = Integer.parseInt(idLbl.getText());
        String name = nameTxt.getText();
        Double price = Double.parseDouble(priceTxt.getText());
        int stock = Integer.parseInt(invTxt.getText());
        int max = Integer.parseInt(maxTxt.getText());
        int min = Integer.parseInt(minTxt.getText());
        String compName = compTxt.getText();

        if (min > max) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Please make sure the maximum is larger than the minimum");
            alert.showAndWait();
        } else if (stock < min) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Please make sure the stock is above the minimum");
            alert.showAndWait();
        } else if (stock > max) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Please make sure the stock is below the maximum");
            alert.showAndWait();
        } else {

            Part newPart = new Outsourced(id, name, price, stock, min, max, compName);

            Inventory.updatePart(id - 1, newPart);
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    @FXML
    private void openModifyInHousePartOnAction(MouseEvent event) throws IOException {
        
        //switches to a in house part
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModifyPartInHouseMenu.fxml"));
        loader.load();

        int id = Integer.parseInt(idLbl.getText());
        String name = nameTxt.getText();
        Double price = Double.parseDouble(priceTxt.getText());
        int stock = Integer.parseInt(invTxt.getText());
        int max = Integer.parseInt(maxTxt.getText());
        int min = Integer.parseInt(minTxt.getText());
        int machId = 0;

        Part partSending = new InHouse(id, name, price, stock, min, max, machId);
        ModifyPartInHouseMenuController MPIHController = loader.getController();
        MPIHController.sendPart(partSending);

        stage = (Stage) ((RadioButton) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void sendPart(Part part) {
        // fills in the appropriate fields from the part that is being modified
        idLbl.setText(String.valueOf(part.getId()));
        nameTxt.setText(part.getName());
        invTxt.setText(String.valueOf(part.getStock()));
        priceTxt.setText(String.valueOf(part.getPrice()));
        minTxt.setText(String.valueOf(part.getMin()));
        maxTxt.setText(String.valueOf(part.getMax()));
        compTxt.setText(((Outsourced) part).getCompanyName());

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
