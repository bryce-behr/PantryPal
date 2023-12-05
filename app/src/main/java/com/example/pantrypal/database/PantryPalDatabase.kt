package com.example.pantrypal.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Recipe::class],
    version = 1
)
abstract class PantryPalDatabase: RoomDatabase(){
    abstract val pantryPalDAO: PantryPalDAO
}