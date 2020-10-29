/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ajw51
 */
public class Inventory {
    /*
    Creates two lists, one full of all the Parts and the other full of all the Products
    */

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part newPart) { //adds a part
        allParts.add(newPart);
    }

    public static void addProduct(Product newProduct) { //adds a product
        allProducts.add(newProduct);
    }

    public static Part lookupPart(int partId) { //finds a part using it's ID
        for (Part part : getAllParts()) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    public static Product lookupProduct(int productId) { //finds a product using it's ID
        for (Product product : getAllProducts()) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }
    public static ObservableList<Part> lookupPart(String partName) { //looks up all the parts that contain what was entered
        ObservableList<Part> allFilteredParts = FXCollections.observableArrayList();

        for (Part part : getAllParts()) {
            if (part.getName().contains(partName)) {
                allFilteredParts.add(part);
            }
        }

        return allFilteredParts;
    }

    public static ObservableList<Product> lookupProduct(String productName) { //looks up all the products that contain what was entered
        ObservableList<Product> allFilteredProducts = FXCollections.observableArrayList();

        for (Product product : getAllProducts()) {
            if (product.getName().contains(productName)) {
                allFilteredProducts.add(product);
            }
        }

        return allFilteredProducts;
    }

    public static void updatePart(int index, Part selectedPart) { //updates the part

        getAllParts().set(index, selectedPart);

    }

    public static void updateProduct(int index, Product selectedProduct) { //updates the product
        
        getAllProducts().set(index, selectedProduct);
    
    }
    public static boolean deletePart(Part selectedPart) { //deletes the part
        
        for (Part part : getAllParts()) {
            if (part.getId() == selectedPart.getId()) {
                return getAllParts().remove(part);
            }
        }
        return false;
    }

    public static boolean deleteProduct(Product selectedProduct) { //deletes the product
        
        for (Product product : getAllProducts()) {
            if (product.getId() == selectedProduct.getId()) {
                return getAllProducts().remove(product);
            }
        }
        return false;
    }
    
    public static ObservableList<Part> getAllParts() { //returns the Parts list
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() { //returns the Products list
        return allProducts;
    }

}
