/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Part;
import model.Product;

/**
 * FXML Controller class
 *
 * @author ajw51
 */
public class MainMenuController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TableView<Part> partsTableView;

    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Integer> partInvCol;

    @FXML
    private TableColumn<Part, Double> partPriceCol;

    @FXML
    private TableView<Product> productsTableView;

    @FXML
    private TableColumn<Product, Integer> productIdCol;

    @FXML
    private TableColumn<Product, String> productNameCol;

    @FXML
    private TableColumn<Product, Integer> productInvCol;

    @FXML
    private TableColumn<Product, Double> productPriceCol;

    @FXML
    private TextField partSearchTxt;

    @FXML
    private TextField productSearchTxt;

    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {
        //takes you to the screen to add a Part
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPartInHouseMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException {
        //takes you to the screen to add a Product
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProductMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionDeletePart(ActionEvent event) {
        //deletes the selected Part
        Inventory.deletePart(partsTableView.getSelectionModel().getSelectedItem());
        partsTableView.setItems(Inventory.lookupPart(partSearchTxt.getText()));
    }

    @FXML
    void onActionDeleteProduct(ActionEvent event) {
        //deletes the selected Product
        Inventory.deleteProduct(productsTableView.getSelectionModel().getSelectedItem());
        productsTableView.setItems(Inventory.lookupProduct(productSearchTxt.getText()));

    }

    @FXML
    void onActionExit(ActionEvent event) {
        //exits the program
        System.exit(0);
    }

    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException {
        //finds if the part is in house or outsourced and takes you to the screen where you can modify the selected part
        Part partSelected = partsTableView.getSelectionModel().getSelectedItem();
        if (partSelected instanceof InHouse) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyPartInHouseMenu.fxml"));
            loader.load();
            
            ModifyPartInHouseMenuController MPIHController = loader.getController();
            MPIHController.sendPart(partSelected);
            
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyPartOutsourcedMenu.fxml"));
            loader.load();
            
            ModifyPartOutsourcedMenuController MPOController = loader.getController();
            MPOController.sendPart(partSelected);
            
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    @FXML
    void onActionModifytProduct(ActionEvent event) throws IOException {
        //takes you to the screen where you can modify the selected product
        Product productSelected = productsTableView.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModifyProductMenu.fxml"));
        loader.load();
        
        ModifyProductMenuController MPController = loader.getController();
        MPController.sendProduct(productSelected);
        
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
            //searches the TableView full of Parts
    void onActionSearchParts(ActionEvent event) throws IOException {

        String q = partSearchTxt.getText();
        if (isInteger(q)) {
            ObservableList<Part> tempList = Inventory.lookupPart(q);
            tempList.add(Inventory.lookupPart(Integer.valueOf(q)));
            partsTableView.setItems(tempList);
        } else {
            partsTableView.setItems(Inventory.lookupPart(q));
        }
    }

    @FXML
            //searches the TableView full of Products
    void onActionSearchProducts(ActionEvent event) throws IOException {
        String q = productSearchTxt.getText();
        if (isInteger(q)) {
            ObservableList<Product> tempList = Inventory.lookupProduct(q);
            tempList.add(Inventory.lookupProduct(Integer.valueOf(q)));
            productsTableView.setItems(tempList);
        } else {
            productsTableView.setItems(Inventory.lookupProduct(q));
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // fills the TableViews when the screen is first brought up

        partsTableView.setItems(Inventory.getAllParts());

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        partsTableView.getSelectionModel().select(Inventory.lookupPart(1));

        productsTableView.setItems(Inventory.getAllProducts());

        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        productsTableView.getSelectionModel().select(Inventory.lookupProduct(1));

    }

}
