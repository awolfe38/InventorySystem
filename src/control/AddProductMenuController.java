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
public class AddProductMenuController implements Initializable {

    Stage stage;
    Parent scene;

    private ObservableList<Part> temp = FXCollections.observableArrayList();

    @FXML
    private TableView<Part> addProductAddTableView;

    @FXML
    private TableColumn<Part, Integer> addProductAddIdCol;

    @FXML
    private TableColumn<Part, String> addProductAddNameCol;

    @FXML
    private TableColumn<Part, Integer> addProductAddInvCol;

    @FXML
    private TableColumn<Part, Double> addProductAddPriceCol;

    @FXML
    private TableView<Part> addProductDeleteTableView;

    @FXML
    private TableColumn<Part, Integer> addProductDeleteIdCol;

    @FXML
    private TableColumn<Part, String> addProductDeleteNameCol;

    @FXML
    private TableColumn<Part, Integer> addProductDeleteInvCol;

    @FXML
    private TableColumn<Part, Double> addProductDeletePriceCol;

    @FXML
    private TextField addProductNameTxt;

    @FXML
    private TextField addProductInvTxt;

    @FXML
    private TextField addProductPriceTxt;

    @FXML
    private TextField addProductMaxTxt;

    @FXML
    private TextField addProductMinTxt;

    @FXML
    private TextField addProductSearchTxt;

    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException {
        //adds the part to the associated list
        Part partSelected = addProductAddTableView.getSelectionModel().getSelectedItem();
        temp.add(partSelected);
    }

    @FXML
    void onActionAddSearchProducts(ActionEvent event) {
            //searches the TableView full of Parts
        String q = addProductSearchTxt.getText();
        if (isInteger(q)) {
            ObservableList<Part> tempList = Inventory.lookupPart(q);
            tempList.add(Inventory.lookupPart(Integer.valueOf(q)));
            addProductAddTableView.setItems(tempList);
        } else {
            addProductAddTableView.setItems(Inventory.lookupPart(q));
        }
    }

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        //Goes back to the main screen without saving anything
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Data will be cleared and no product will be created, do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        }
    }

    public static boolean isInteger(String str) {  //finds if the input is a string or integer
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
        Part partSelected = addProductDeleteTableView.getSelectionModel().getSelectedItem();
        temp.remove(partSelected);
    }

    @FXML
    void onActionSaveProducts(ActionEvent event) throws IOException {
        //saves the product
        int id = Inventory.getAllProducts().size() + 1;
        String name = addProductNameTxt.getText();
        double price = Double.parseDouble(addProductPriceTxt.getText());
        int stock = Integer.parseInt(addProductInvTxt.getText());
        int min = Integer.parseInt(addProductMinTxt.getText());
        int max = Integer.parseInt(addProductMaxTxt.getText());

        Product tempProduct = new Product(id, name, price, stock, min, max);

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
                tempProduct.addAssociatedPart(temp.get(i));
            }
            Inventory.addProduct(tempProduct);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // fills the TableViews when the screen is first brought up

        addProductAddTableView.setItems(Inventory.getAllParts());

        addProductAddIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductAddNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductAddInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductAddPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        addProductAddTableView.getSelectionModel().select(Inventory.lookupPart(1));

        addProductDeleteTableView.setItems(temp);

        addProductDeleteIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductDeleteNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductDeleteInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductDeletePriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

}
