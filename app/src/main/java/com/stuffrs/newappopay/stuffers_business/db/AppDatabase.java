package com.stuffrs.newappopay.stuffers_business.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {Product.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DaoProduct daoProduct();

}

