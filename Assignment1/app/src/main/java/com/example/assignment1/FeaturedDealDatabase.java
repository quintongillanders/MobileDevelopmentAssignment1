package com.example.assignment1;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {FeaturedDeal.class}, version = 3, exportSchema = false)
public abstract class FeaturedDealDatabase extends RoomDatabase {

    private static volatile FeaturedDealDatabase INSTANCE;

    public abstract FeaturedDealDAO featuredDealDAO();

    public static FeaturedDealDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (FeaturedDealDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    FeaturedDealDatabase.class, "featured_deal_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

