package com.stuffrs.newappopay.stuffers_business.db;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Notification.class}, version = 1)
public abstract class NotificationDatabase extends RoomDatabase {
    public abstract DaoNotification daoNotification();

}