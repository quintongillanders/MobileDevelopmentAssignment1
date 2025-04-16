package com.example.assignment1;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface FeaturedDealDAO {


    @Insert
    void insert(FeaturedDeal deal);

    @Query("SELECT * FROM featured_deals")
    List<FeaturedDeal> getAllDeals();

    @Query("DELETE  FROM featured_deals")
    void deleteAllDeals();

    @Query("SELECT * FROM featured_deals WHERE category = :category")
    List<FeaturedDeal> getDealsByCategory(String category);

    @Query("SELECT * FROM featured_deals WHERE title LIKE '%' || :query || '%'")
    List<FeaturedDeal> searchDeals(String query);

    @Query("SELECT * FROM featured_deals WHERE LOWER (category) = LOWER(:selectedCategory) AND LOWER (title) LIKE '%' || LOWER (:query) || '%'")
    List<FeaturedDeal> searchDealsByCategory(String selectedCategory, String query);

    @Query("SELECT * FROM featured_deals WHERE LOWER(title) LIKE '%' || LOWER(:query) || '%'")
    List<FeaturedDeal> searchDealsByTitle(String query);




    }

