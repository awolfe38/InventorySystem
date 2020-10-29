/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorysystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Product;

/**
 *
 * @author ajw51
 */
public class InventorySystem extends Application{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        
        /*
        Adding a bunch of sample Parts and Products to test out the program.
        */
        
        InHouse inhouse1 = new InHouse(1, "Screw", 0.43, 781, 200, 2000, 8451);
        InHouse inhouse2 = new InHouse(3, "Wheel", 2.29, 40, 85, 200, 3201);
        
        Outsourced outsource1 = new Outsourced(2, "Bolt", 0.62, 112, 20, 700, "Wisconsin Tools");
        Outsourced outsource2 = new Outsourced(4, "Condenser", 42.63, 18, 4, 78, "Hardware Co");
        
        Product product1 = new Product(1, "Cooler", 63.99, 54, 2, 200);
        Product product2 = new Product(2, "Chest", 226.99, 18, 7, 70);
        Product product3 = new Product(3, "Freezer", 324.99, 8, 5, 40);
        Product product4 = new Product(4, "Refrigerator", 562.99, 3, 1, 10);
        
        Inventory.addPart(inhouse1);
        Inventory.addPart(outsource1);
        Inventory.addPart(inhouse2);
        Inventory.addPart(outsource2);
        
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);
        Inventory.addProduct(product4);
        
        product1.addAssociatedPart(inhouse1);
        product1.addAssociatedPart(inhouse1);
        product1.addAssociatedPart(inhouse2);
        product1.addAssociatedPart(inhouse2);
        product1.addAssociatedPart(outsource1);
        product1.addAssociatedPart(outsource1);
        
        
        product2.addAssociatedPart(inhouse1);
        product2.addAssociatedPart(outsource1);
        product2.addAssociatedPart(inhouse2);
        product2.addAssociatedPart(inhouse2);
        
        product3.addAssociatedPart(outsource2);
        product3.addAssociatedPart(outsource1);
        product3.addAssociatedPart(outsource1);
        product3.addAssociatedPart(outsource1);
        product3.addAssociatedPart(outsource1);
        product3.addAssociatedPart(inhouse1);
        product3.addAssociatedPart(inhouse1);
        product3.addAssociatedPart(inhouse1);
        product3.addAssociatedPart(inhouse1);
        
        product4.addAssociatedPart(outsource2);
        product4.addAssociatedPart(inhouse2);
        product4.addAssociatedPart(inhouse2);
        product4.addAssociatedPart(outsource1);
        product4.addAssociatedPart(outsource1);
        product4.addAssociatedPart(outsource1);
        product4.addAssociatedPart(outsource1);
        product4.addAssociatedPart(inhouse1);
        product4.addAssociatedPart(inhouse1);
        product4.addAssociatedPart(inhouse1);
        product4.addAssociatedPart(inhouse1);
        
        
        launch(args);  //Launches the program
    }

    @Override
    //Pulling up the Main Menu
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Inventory System");
        stage.show();
    }
    
}
