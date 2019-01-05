package com.hapy.login;

public class FirebaseUpload {
    private String Name,Category, Amount,ImageUrl;

    public FirebaseUpload() {
    }

    public FirebaseUpload(String name, String category, String amount, String imageUrl) {
        Name = name;
        Category = category;
        Amount = amount;
        ImageUrl = imageUrl;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
