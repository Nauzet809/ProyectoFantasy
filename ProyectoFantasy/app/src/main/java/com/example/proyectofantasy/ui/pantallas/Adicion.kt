package com.example.proyectofantasy.ui.pantallas

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuAnchorType.Companion.PrimaryNotEditable
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.aplicacindepreguntas.data.listaEquipos
import com.example.aplicacindepreguntas.data.listaJugadores
import com.example.aplicacindepreguntas.data.listaPartidos
import com.example.aplicacindepreguntas.model.Partido
import com.example.proyectofantasy.R
import java.time.Instant
import java.time.ZoneOffset
import kotlin.math.roundToInt

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaFormulario(onGuardar: (Partido) -> Unit) {
    var jugador by remember { mutableStateOf("") }
    var equipo by remember { mutableStateOf("") }
    var rival by remember { mutableStateOf("") }
    var puntos by remember { mutableFloatStateOf(0f) }
    val posiciones = remember { mutableStateListOf<String>() }
    val datePickerState = rememberDatePickerState()
    val contexto = LocalContext.current

    val fotosJugadores = mapOf(
        "LeBron James" to R.drawable.lebron,
        "Stephen Curry" to R.drawable.curry,
        "Luka Doncic" to R.drawable.luka,
        "Kevin Durant" to R.drawable.durant,
        "Jayson Tatum" to R.drawable.tatum,
        "Jimmy Butler" to R.drawable.butler,
        "Nikola Jokic" to R.drawable.jokic,
        "Giannis Antetokounmpo" to R.drawable.giannis,
        "Anthony Edwards" to R.drawable.edwards,
        "Shai Gilgeous-Alexander" to R.drawable.shai
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text("Introduce el nombre del jugador:")
        Desplegable(jugador, listaJugadores) { jugador = it }

        Text("El equipo en el que juega:")
        Desplegable(equipo, listaEquipos) { equipo = it }

        Text("Selecciona una o más posiciones:")
        listOf("PF", "C", "PG", "SG", "SF").forEach { pos ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = pos in posiciones,
                    onCheckedChange = { marcado ->
                        if (marcado) posiciones.add(pos) else posiciones.remove(pos)
                    }
                )
                Text(pos)
            }
        }

        Text("El número de puntos que metió en el partido:")
        Row(verticalAlignment = Alignment.CenterVertically) {
            Slider(
                value = puntos,
                onValueChange = { puntos = it },
                valueRange = 0f..60f,
                modifier = Modifier.weight(1f)
            )
            Text(puntos.roundToInt().toString())
        }

        Text("El equipo contra el que se jugó:")
        Desplegable(rival, listaEquipos) { rival = it }

        Text("Selecciona la fecha del partido:")
        DatePicker(state = datePickerState)

        Button(
            enabled = jugador.isNotBlank() && equipo.isNotBlank() &&
                    rival.isNotBlank() && posiciones.isNotEmpty() &&
                    datePickerState.selectedDateMillis != null,
            onClick = {
                val fecha = Instant.ofEpochMilli(datePickerState.selectedDateMillis!!)
                    .atZone(ZoneOffset.UTC)
                    .toLocalDate()

                onGuardar(
                    Partido(
                        nombre = jugador,
                        equipo = equipo,
                        posiciones = posiciones.toList(),
                        puntos = puntos.roundToInt(),
                        equipoE = rival,
                        fecha = fecha,
                        foto = (fotosJugadores[jugador] ?: R.drawable.jugador_generico) as Int
                    )
                )

                jugador = ""
                equipo = ""
                rival = ""
                puntos = 0f
                posiciones.clear()
                datePickerState.selectedDateMillis = null

                Toast.makeText(contexto, "Partido guardado", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar partido")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Desplegable(
    seleccionado: String,
    opciones: List<String>,
    onSeleccion: (String) -> Unit
) {
    var expandido by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expandido,
        onExpandedChange = { expandido = it }
    ) {
        TextField(
            value = seleccionado,
            onValueChange = {},
            readOnly = true,
            label = { Text("Selecciona un valor") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expandido) },
            modifier = Modifier
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expandido,
            onDismissRequest = { expandido = false }
        ) {
            opciones.forEach { opcion ->
                DropdownMenuItem(
                    text = { Text(opcion) },
                    onClick = {
                        onSeleccion(opcion)
                        expandido = false
                    }
                )
            }
        }
    }
}