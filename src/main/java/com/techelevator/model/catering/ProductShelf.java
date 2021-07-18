package com.techelevator.model.catering;

public class ProductShelf {

    private Product product;
    private int numOfProducts;

    public ProductShelf(Product product, int numOfProducts) {
        this.product = product;
        this.numOfProducts = numOfProducts;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getNumOfProducts() {
        return numOfProducts;
    }

    public void setNumOfProducts(int numOfProducts) {
        this.numOfProducts = numOfProducts;
    }
}
