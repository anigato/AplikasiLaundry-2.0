package net.anigato.laundry.screens

import android.R
import android.app.DatePickerDialog
import android.widget.Toast
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.benasher44.uuid.uuid4
import kotlinx.coroutines.launch
import net.anigato.laundry.ui.theme.Purple700
import net.anigato.laundry.ui.theme.Teal200
import java.util.*

@Composable
fun FormPencatatanLaunry(){
//    dagger hilt
    val viewModel = hiltViewModel<PengelolaanLaundryViewModel>()

    val context = LocalContext.current
    val nama = remember { mutableStateOf(TextFieldValue(""))}
    val totalBiaya = remember { mutableStateOf("")}
    val biaya = remember { mutableStateOf("10000")}
    val berat = remember { mutableStateOf(TextFieldValue(""))}

//    radio button tipe laundry
    val tipeLaundry = remember { mutableStateOf("3 Jam")}
    val radioTipeLaundry = listOf("3 Jam", "Besok Selesai", "Reguler")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioTipeLaundry[0]) }

//    deklarasi integer tahun, bulan, tanggal dan kalender
    val tahun: Int
    val bulan: Int
    val tanggal: Int
    val kalender = Calendar.getInstance()

//    ambil tanggal saat ini
    tahun = kalender.get(Calendar.YEAR)
    bulan = kalender.get(Calendar.MONTH)
    tanggal = kalender.get(Calendar.DAY_OF_MONTH)
    kalender.time = Date()

//    date picker
    val tglMasuk = remember { mutableStateOf("$tanggal/${bulan+1}/$tahun")}
    val tglSelesai = remember { mutableStateOf("")}
    val tglNilai = remember { mutableStateOf("0")}
    val tglPicker = remember { mutableStateOf("$tanggal")}
    val blnPicker = remember { mutableStateOf("${bulan+1}")}
    val thnPicker = remember { mutableStateOf("$tahun")}


//    deklarasi date picker
    val datePickerDialogTglMasuk = DatePickerDialog(
        context,
        { _: DatePicker, tahun: Int, bulan: Int, tanggal: Int ->
            tglMasuk.value = "$tanggal/${bulan+1}/$tahun"
            tglPicker.value = "$tanggal"
            blnPicker.value = "${bulan+1}"
            thnPicker.value = "$tahun"
        }, tahun, bulan, tanggal
    )

//    style tombol save dan reset
    val saveButtonColors = ButtonDefaults.buttonColors(
        backgroundColor = Purple700,
        contentColor = Teal200
    )
    val resetButtonColors = ButtonDefaults.buttonColors(
        backgroundColor = Teal200,
        contentColor = Purple700
    )

    val scope = rememberCoroutineScope()


    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
//        Judul Aplikasi
        Column (
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Catat Laundry 2.0",
                fontWeight = FontWeight.Bold,
                fontSize = 35.sp
            )
        }
//        Form nama pelanggan
        Column {
            OutlinedTextField(
                label = { Text(text = "Nama Pelanggan") },
                value = nama.value,
                onValueChange = {
                    nama.value = it
                },
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                placeholder = { Text(text = "Contoh : Anggi") }
            )
        }
//        radio button tipe laundry
        Column {
            Text(text = "Tipe Laundry", fontSize = 18.sp)
            Row {
                radioTipeLaundry.forEach { text ->
                    RadioButton(
                        selected = text == selectedOption,
                        onClick = {
                            onOptionSelected(text)
                            if(text.equals("3 Jam")){
                                biaya.value = "10000"
                                tglNilai.value = "0"
                            }else if(text.equals("Besok Selesai")){
                                biaya.value = "8000"
                                tglNilai.value = "1"
                            }else {
                                biaya.value = "6000"
                                tglNilai.value = "3"
                            }
                            tipeLaundry.value = text
                            if(berat.value.text.isNotBlank()){
                                totalBiaya.value = (berat.value.text.toInt()*biaya.value.toInt()).toString()
                            }else{
                                totalBiaya.value = ""
                            }
                        }
                    )
                    Text(
                        text = text,
                        modifier = Modifier.padding(start = 0.dp, top = 12.dp)
                    )
                }
            }
        }
//        form berat pakaian
        Column {
            OutlinedTextField(
                label = { Text(text = "Berat (Kg)") },
                value = berat.value,
                onValueChange = {
                    berat.value = it
                    if(berat.value.text.isNotBlank()){
                        totalBiaya.value = (berat.value.text.toInt()*biaya.value.toInt()).toString()
                    }
                },
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType =
                KeyboardType.Number),
                placeholder = { Text(text = "Contoh : 5") }
            )
        }
//        form total biaya (diisi otomatis)
        Column {
            OutlinedTextField(
                label = { Text(text = "Total Biaya") },
                value = totalBiaya.value,
                onValueChange = {
                    totalBiaya.value
                },
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(),
                placeholder = { Text(text = "Rp. ") },
                readOnly = true
            )
        }
//        form datepicker tanggal masuk
        Column {
            OutlinedTextField(
                label = { Text(text = "Tanggal Masuk") },
                value = tglMasuk.value,
                onValueChange = {
                    tglMasuk.value
                },
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(),
                placeholder = { Text(text = "dd/mm/yyyy") },
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = {
                        datePickerDialogTglMasuk.show()
                    }) {
//                        Icon(imageVector = Icons.Filled.DateRange, contentDescription = "Pilih Tanggal")
                        Text(text = "Pilih Tanggal  ", color = Color(0XFF0F9D58))
                    }
                }
            )
        }
//        form tanggal selesai (otomatis berdasarkan tipe laundry)
        Column {
            tglSelesai.value = (tglPicker.value.toInt()+tglNilai.value.toInt()).toString()+"/"+blnPicker.value+"/"+thnPicker.value
            OutlinedTextField(
                label = { Text(text = "Tanggal Selesai") },
                value = tglSelesai.value,
                onValueChange = {
                    tglSelesai.value
                },
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(),
                placeholder = { Text(text = "dd/mm/yyyy") },
                readOnly = true
            )
        }


        Row (modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()) {
            Button(modifier = Modifier.weight(5f), onClick = {
                if (nama.value.text.isNotEmpty() &&
                    tglMasuk.value.isNotEmpty() &&
                    tglSelesai.value.isNotEmpty() &&
                    tipeLaundry.value.isNotEmpty() &&
                    totalBiaya.value.isNotEmpty() &&
                    berat.value.text.isNotEmpty()
                ) {
                    val id = uuid4().toString()
                    scope.launch {
                        viewModel.insert(id, nama.value.text, tglMasuk.value, tglSelesai.value, tipeLaundry.value, totalBiaya.value.toInt(), berat.value.text.toDouble())
                        nama.value = TextFieldValue("")
                        tipeLaundry.value = "3 Jam"
                        berat.value = TextFieldValue("")
                        totalBiaya.value = ""
                        tglMasuk.value = tglMasuk.value
                        tglSelesai.value = tglSelesai.value
                    }
                    Toast.makeText(context, "Berhasil Disimpan", Toast.LENGTH_LONG).show()
                } else if (nama.value.text.isEmpty()) {
                    Toast.makeText(context, "Harap isi Nama Pelanggan",Toast.LENGTH_LONG).show()
                } else if (berat.value.text.isEmpty()) {
                    Toast.makeText(context, "Harap isi Berat Pakaian",Toast.LENGTH_LONG).show()
                } else if (tglMasuk.value.isEmpty()) {
                    Toast.makeText(context, "Harap isi Tanggal Masuk",Toast.LENGTH_LONG).show()
                }
            }, colors = saveButtonColors) {
                Text(
                    text = "Simpan",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 18.sp
                    ), modifier = Modifier.padding(8.dp)
                )
            }
            Button(modifier = Modifier.weight(5f), onClick = {
                nama.value = TextFieldValue("")
                tipeLaundry.value = "3 Jam"
                berat.value = TextFieldValue("")
                totalBiaya.value = ""
                tglMasuk.value = tglMasuk.value
                tglSelesai.value = tglSelesai.value
            }, colors = resetButtonColors) {
                Text(
                    text = "Reset",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 18.sp
                    ), modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}
