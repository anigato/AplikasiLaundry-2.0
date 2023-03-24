package net.anigato.laundry.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import net.anigato.laundry.models.SetoranLaundry
import net.anigato.laundry.presistences.SetoranLaundryDao
import javax.inject.Inject

@HiltViewModel
class PengelolaanLaundryViewModel @Inject constructor(private val setoranLaundryDao: SetoranLaundryDao) : ViewModel() {
    val list : LiveData<List<SetoranLaundry>> = setoranLaundryDao.loadAll()
    suspend fun insert(
        id: String,
        nama: String,
        tglMasuk: String,
        tglSelesai: String,
        tipeLaundry: String,
        totalBiaya: Int,
        berat: Double
    ){
        val item = SetoranLaundry(id, nama, tglMasuk, tglSelesai, tipeLaundry, totalBiaya, berat)
        setoranLaundryDao.insertAll(item)
    }
}