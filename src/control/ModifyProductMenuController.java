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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

/**
 * FXML Controller class
 *
 * @author ajw51
 */
public class ModifyProductMenuController implements Initializable {

    Stage stage;
    Parent scene;

    private ObservableList<Part> temp = FXCollections.observableArrayList();

    @FXML
    private TableView<Part> modifyProductAddTableView;

    @FXML
    private TableColumn<Part, Integer> modifyProductAddIdCol;

    @FXML
    private TableColumn<Part, String> modifyProductAddNameCol;

    @FXML
    private TableColumn<Part, Integer> modifyProductAddInvCol;

    @FXML
    private TableColumn<Part, Double> modifyProductAddPriceCol;

    @FXML
    private TableView<Part> modifyProductDeleteTableView;

    @FXML
    private TableColumn<Part, Integer> modifyProductDeleteIdCol;

    @FXML
    private TableColumn<Part, String> modifyProductDeleteNameCol;

    @FXML
    private TableColumn<Part, Integer> modifyProductDeleteInvCol;

    @FXML
    private TableColumn<Part, Double> modifyProductDeletePriceCol;

    @FXML
    private Label idLbl;

    @FXML
    private TextField modifyProductNameTxt;

    @FXML
    private TextField modifyProductInvTxt;

    @FXML
    private TextField modifyProductPriceTxt;

    @FXML
    private TextField modifyProductMaxTxt;

    @FXML
    private TextField modifyProductMinTxt;

    @FXML
    private TextField modifyProductSearchTxt;

    @FXML
    void onActionAddProduct(ActionEvent event) {
        //adds the part to the associated list
        Part partSelected = modifyProductAddTableView.getSelectionModel().getSelectedItem();
        temp.add(partSelected);
    }

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        //Goes back to the main screen without saving anything
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Data will be cleared and no product will be changed, do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        }
    }

    public static boolean isInteger(String str) { //finds if the input is a string or integer
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    @FXML
    void onActionDeleteProduct(ActionEvent event) {
        //removes the part from being associated with the product
        Part partSelected = modifyProductDeleteTableView.getSelectionModel().getSelectedItem();
        temp.remove(partSelected);
    }

    @FXML
    void onActionModifySearchProducts(ActionEvent event) {
        //searches the TableView full of Parts
        String q = modifyProductSearchTxt.getText();
        if (isInteger(q)) {
            ObservableList<Part> tempList = Inventory.lookupPart(q);
            tempList.add(Inventory.lookupPart(Integer.valueOf(q)));
            modifyProductAddTableView.setItems(tempList);
        } else {
            modifyProductAddTableView.setItems(Inventory.lookupPart(q));
        }
    }

    @FXML
    void onActionSaveProducts(ActionEvent event) throws IOException {
        //saves the updates to the product
        int id = Integer.parseInt(idLbl.getText());
        String name = modifyProductNameTxt.getText();
        Double price = Double.parseDouble(modifyProductPriceTxt.getText());
        int stock = Integer.parseInt(modifyProductInvTxt.getText());
        int max = Integer.parseInt(modifyProductMaxTxt.getText());
        int min = Integer.parseInt(modifyProductMinTxt.getText());

        Product newProduct = new Product(id, name, price, stock, min, max);

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
        } else if (temp.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Please make sure the product has at least 1 part");
            alert.showAndWait();
        } else {

            for (int i = 0; i < temp.size(); i++) {
                newProduct.addAssociatedPart(temp.get(i));
            }

            Inventory.updateProduct(id - 1, newProduct);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }

    public void sendProduct(Product product) {
        // fills in the appropriate fields from the product that is being modified
        idLbl.setText(String.valueOf(product.getId()));
        modifyProductNameTxt.setText(product.getName());
        modifyProductInvTxt.setText(String.valueOf(product.getStock()));
        modifyProductPriceTxt.setText(String.valueOf(product.getPrice()));
        modifyProductMinTxt.setText(String.valueOf(product.getMin()));
        modifyProductMaxTxt.setText(String.valueOf(product.getMax()));

        //looks up the associated parts and puts them into the TableView
        temp = Inventory.lookupProduct(Integer.parseInt(idLbl.getText())).getAllAssociatedParts();
        modifyProductDeleteTableView.setItems(temp);

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // fills the TableViews when the screen is first brought up
        modifyProductAddTableView.setItems(Inventory.getAllParts());

        modifyProductAddIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductAddNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductAddInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductAddPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        modifyProductAddTableView.getSelectionModel().select(Inventory.lookupPart(1));

        modifyProductDeleteTableView.setItems(temp);

        modifyProductDeleteIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductDeleteNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductDeleteInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductDeletePriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

}
