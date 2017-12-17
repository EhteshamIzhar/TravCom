package com.android.parii.travcom.Model;


public class Food {
    private String Name,Image,Description,Price,Discount,MenuID;

    public Food(){

    }

    public Food(String name, String image, String description, String price, String discount, String menuID) {
        Description = description;
        Discount = discount;
        Image = image;
        MenuID = menuID;
        Name = name;
        Price = price;

    }

    public void setName(String name) {
        Name = name;
    }

    public void setImage(String image) {
        Image = image;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public void setMenuID(String menuID) {
        MenuID = menuID;
    }

    public String getName() {
        return Name;
    }

    public String getImage() {
        return Image;
    }

    public String getDescription() {
        return Description;
    }

    public String getPrice() {
        return Price;
    }

    public String getDiscount() {
        return Discount;
    }

    public String getMenuID() {
        return MenuID;
    }
}
