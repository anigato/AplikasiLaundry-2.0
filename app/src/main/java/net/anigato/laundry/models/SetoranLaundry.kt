package net.anigato.laundry.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SetoranLaundry(
    @PrimaryKey val id: String,
    val nama: String,
    val tglMasuk: String,
    val tglSelesai: String,
    val tipeLaundry: String,
    val totalBiaya: Int,
    val berat: Double
)
