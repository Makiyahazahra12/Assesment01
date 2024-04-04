package org.d3if0059.assesment01.ui.screen

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if0059.assesment01.R
import org.d3if0059.assesment01.navigation.Screen
import org.d3if0059.assesment01.ui.theme.Assesment01Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.About.route)
                    }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(id = R.string.tentang_aplikasi),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { padding ->
        ScreenContent(Modifier.padding(padding))
    }
}
@Composable
fun ScreenContent(modifier: Modifier) {
    var berat by rememberSaveable { mutableStateOf("") }
    var beratError by rememberSaveable { mutableStateOf(false) }

    var tinggi by rememberSaveable { mutableStateOf("") }
    var tinggiError by rememberSaveable { mutableStateOf(false) }

    var usia by rememberSaveable { mutableStateOf("") }
    var usiaError by rememberSaveable { mutableStateOf(false) }

    var jenisKelamin by rememberSaveable { mutableStateOf("") }
    var hasilBMR by rememberSaveable { mutableStateOf("") }
    var aktifitas by rememberSaveable{ mutableStateOf("") }

    val context = LocalContext.current

    Column (
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id =R.string.bmr_intro),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = berat,
            onValueChange = { berat = it },
            label = { Text(text = stringResource(id = R.string.berat_badan))},
            trailingIcon = { Text(text = "kg")},
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = tinggi,
            onValueChange = { tinggi = it},
            label = { Text(text = stringResource(id = R.string.tinggi_badan))},
            trailingIcon = { Text(text = "cm") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = usia,
            onValueChange = { usia = it },
            label = { Text(text = stringResource(id = R.string.usia)) },
            trailingIcon = { Text(text = "Tahun")},
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number, 
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = aktifitas,
            onValueChange = { aktifitas = it },
            label = { Text(text = "Aktifitas Fisik (rendah, sedang, tinggi)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()

        )
        Row(
            Modifier.padding(top = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = jenisKelamin == "Pria",
                onClick = { jenisKelamin = "Pria" }
            )
            Text(
                text = "Pria",
                modifier = Modifier.padding(start = 8.dp),

            )
            RadioButton(
                selected = jenisKelamin == "Wanita",
                onClick = { jenisKelamin = "Wanita" }
            )
            Text(
                text = "Wanita", modifier = Modifier.padding(start = 8.dp)
            )
        }
        Button(
            onClick = {
                val bmr = hitungBMR(berat.toFloat(), tinggi.toFloat(), usia.toInt(), jenisKelamin)
                hasilBMR = bmr.toInt().toString()
            },
            modifier = Modifier.padding(top = 8.dp),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.hitung),
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        Text(text = "BMR: $hasilBMR",
            modifier = Modifier.padding(top = 8.dp)
        )
        Button(onClick = { shareData(
            context = context,
            message = context.getString(R.string.bagikan)) },
            modifier = Modifier.padding(top = 8.dp),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
        ) {
            Text(text = stringResource(id = R.string.bagikan))
        }

    }

}

fun hitungBMR(berat: Float, tinggi: Float, usia: Int, jenisKelamin: String): Float {
    return if (jenisKelamin.equals("Pria", ignoreCase = true)) {
        66.5f + (13.7f * berat) + (5f * tinggi) - (6.8f * usia)
    } else {
        655f + (9.6f * berat) + (1.8f * tinggi) - (4.7f * usia)
    }
}
private fun shareData(context: Context, message: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    if (shareIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(shareIntent)
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ScreenPreview() {
    Assesment01Theme {
        MainScreen(rememberNavController())
    }
}