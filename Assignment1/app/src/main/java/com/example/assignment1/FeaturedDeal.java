package com.example.assignment1;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "featured_deals")
public class FeaturedDeal {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String category;
    public String description;
    public double price;
    public String title;
    public int images;


    public FeaturedDeal(String category, String description, double price, int images) {
        this.category = category;
        this.description = description;
        this.price = price;
        this.images = images;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImages() {
        this.images = images;
        return images;
    }
}







