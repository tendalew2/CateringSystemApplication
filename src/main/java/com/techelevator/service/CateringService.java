package com.techelevator.service;

import com.techelevator.model.catering.Product;
import com.techelevator.PromptFileReader;
import com.techelevator.model.catering.ProductShelf;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CateringService {
    /*
    This class must handle inventory of the items in the menu (construct inventoryReport)
    inventoryReport contains: productCode, purchasePrice, amountRemaining (initially 50) or Sold OUt!
     How?
     -access product containing the attributes we need--build a map
     - create a map: Key-productCode
                     Value-Product

     -Needs access to file! It must use the file path users provide and the file reader (which returns file)
    It must use the file and create a ArrayList using the Product class


     */

    private Map<String, ProductShelf> productInventory;


    public CateringService() {
        productInventory = new HashMap<>();
        userFilePath();
    }


    public Map<String, ProductShelf> getProductInventory() {
        return this.productInventory;
    }

    /*

     */

    //create a method that gets userPath and gives a ray
    private void userFilePath() {

        Scanner in = new Scanner(System.in);

        System.out.println("Provide file location containing inventory Items.");

        PromptFileReader fileReader = new PromptFileReader(in.nextLine());

        //system exception, deal with it here

        try {
            //if it works
            mapFileToItems(fileReader.getFile());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }


    /**
     * a method that uses a file and created a map of type <Product,Integer>
     *
     * @param file pipe delimited file containing the inventory items
     */

    private void mapFileToItems(File file) throws FileNotFoundException {


        Scanner scanner = new Scanner(file);


        while (scanner.hasNext()) {
            String[] lineArr = scanner.nextLine().split("\\|");

            Product product = new Product(lineArr[0], lineArr[1], Double.parseDouble(lineArr[2]), lineArr[3]);
            ProductShelf productShelf = new ProductShelf(product, product.INITIAL_PRODUCT_INVENTORY);

            productInventory.put(lineArr[0], productShelf);

        }

    }


    //function requires the productCode and the amount remaining to be correct
    public void removeFromShelf(String productCode, int amountRemoved) {

        ProductShelf productShelf = productInventory.get(productCode);

        productShelf.setNumOfProducts(productShelf.getNumOfProducts() - amountRemoved);
        productInventory.put(productCode, productShelf);
    }


    public void inventoryReport() {
        double totalPayment = 0;

        System.out.printf("%-15s %-23s %-15s %-15s\n",
                "Product Code",
                "Product Name",
                "Purchase Price",
                "Amount Remaining"
        );

        System.out.println("------------------------------------------------------------------------------------");
        for (String productCode : productInventory.keySet()) {

            ProductShelf productShelf = productInventory.get(productCode);
            String amountRemainingStr = (productShelf.getNumOfProducts() == 0) ? "SOLD OUT" : String.valueOf(productShelf.getNumOfProducts());
            Product product = productShelf.getProduct();


            System.out.printf("%-15s %-23s $%-15.2f %-15s\n",
                    product.getProductCode(),
                    product.getProductName(),
                    product.getProductPrice(),
                    amountRemainingStr);

        }

    }
}
