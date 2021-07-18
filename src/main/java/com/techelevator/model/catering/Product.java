package com.techelevator.model.catering;


public class Product {

    private String productName;
    private double productPrice;
    private String productCode;
    private String productType;


    public final int INITIAL_PRODUCT_INVENTORY = 50;


    public Product(String productCode,String productName, double productPrice,String productType) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productCode=productCode;
        this.productType=productType;
    }

    public String getProductCode() {
        return productCode;
    }


    public String getProductName() {
        return productName;
    }

    public double getProductPrice() {
        return productPrice;
    }


    @Override
    public String toString() {

        String strRep="";
        switch(productType) {
            case "B":
              strRep=  "Beverage";
              break;

            case "D":
                strRep=  "Desert";;
                break;
            case "A":
                strRep=  "Appetizer";

                break;
            case "E":
                strRep=  "Entree";
                break;
            default:
                // code block
        }


        return strRep;
    }
}
