package net.anigato.laundry.presistences

import androidx.room.Database
import androidx.room.RoomDatabase
import net.anigato.laundry.models.SetoranLaundry

@Database(entities = [SetoranLaundry::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun setoranLaundryDao() : SetoranLaundryDao
}