package net.anigato.laundry.presistences

import androidx.lifecycle.LiveData
import androidx.room.*
import net.anigato.laundry.models.SetoranLaundry

@Dao
interface SetoranLaundryDao {
    @Query("SELECT * FROM SetoranLaundry")
    fun loadAll() : LiveData<List<SetoranLaundry>>

    @Query("SELECT * FROM SetoranLaundry WHERE id = :id")
    suspend fun find(id: String) : SetoranLaundry?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg items: SetoranLaundry)

    @Delete
    fun delete(item: SetoranLaundry)
}