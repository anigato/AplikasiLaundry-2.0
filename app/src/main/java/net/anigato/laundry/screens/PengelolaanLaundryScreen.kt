package net.anigato.laundry.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LiveData
import androidx.room.Room
import net.anigato.laundry.models.SetoranLaundry
import net.anigato.laundry.presistences.AppDatabase

@Composable
fun PengelolaanLaundryScreen(){
    val viewModel = hiltViewModel<PengelolaanLaundryViewModel>()
    val items: List<SetoranLaundry> by viewModel.list.observeAsState(initial =
    listOf())

    Column(modifier = Modifier.fillMaxWidth()) {
        FormPencatatanLaunry()

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(items = items, itemContent = { item ->
                Row(modifier = Modifier
                    .padding(start = 15.dp, top = 0.dp, bottom = 0.dp, end = 15.dp)
                    .fillMaxWidth()) {
                    Column(modifier = Modifier.weight(3f)) {
                        Text(text = "Nama Pelanggan", fontSize = 14.sp)
                        Text(item.nama, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                    Column(modifier = Modifier.weight(3f)) {
                        Text(text = "Tipe Laundry", fontSize = 14.sp)
                        Text(item.tipeLaundry, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                    Column(modifier = Modifier.weight(3f)) {
                        Text(text = "Berat", fontSize = 14.sp)
                        Text(item.berat.toString()+" Kg", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }
                Row(modifier = Modifier
                    .padding(start = 15.dp, top = 5.dp, bottom = 0.dp, end = 15.dp)
                    .fillMaxWidth()) {
                    Column(modifier = Modifier.weight(3f)) {
                        Text(text = "Total Biaya", fontSize = 14.sp)
                        Text("Rp. "+item.totalBiaya.toString(), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                    Column(modifier = Modifier.weight(3f)) {
                        Text(text = "Tanggal Masuk", fontSize = 14.sp)
                        Text(item.tglMasuk, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                    Column(modifier = Modifier.weight(3f)) {
                        Text(text = "Tanggal Selesai", fontSize = 14.sp)
                        Text(item.tglSelesai, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }

                }
                Divider(modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp))
            })
        }
    }
}