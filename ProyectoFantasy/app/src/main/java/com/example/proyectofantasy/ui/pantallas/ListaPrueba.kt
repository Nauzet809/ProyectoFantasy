package com.example.proyectofantasy.ui.pantallas

import android.content.Context
import android.widget.Button
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExpandedFullScreenSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType.Companion.PrimaryNotEditable
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.unit.dp
import com.example.aplicacindepreguntas.data.guardarAFichero
import com.example.aplicacindepreguntas.data.listaEquipos
import com.example.aplicacindepreguntas.model.Partido
import com.example.aplicacindepreguntas.ui.componentes.pantallas.Dialogo
import com.example.aplicacindepreguntas.ui.componentes.pantallas.botonesFlotantes
import com.example.proyectofantasy.R
import kotlinx.coroutines.launch
import java.nio.file.WatchEvent
import kotlin.text.contains


@Composable
fun CrearPantallaListaPrueba(
    listaJugadores: List<Partido>,
    onAdd: () -> Unit,
    onBorrar: (List<Partido>) -> Unit      // nuevo: avisa de qué elementos borrar
) {
    val contexto = LocalContext.current
    var buscado by remember { mutableStateOf("") }
    var borrar by remember { mutableStateOf(false) }
    var modoBorrar by remember { mutableStateOf(false) }
    val seleccionados = remember { mutableStateListOf<Partido>() }

    if(borrar){
        Dialogo ({
            onBorrar(seleccionados.toList())
            seleccionados.clear()
            borrar = false
        },{
            borrar = false
        })
    }

    Column {
        Text("Titulo")

        buscado = buscadorTipado(listaJugadores)

        val filtrados = listaJugadores.filter { it.equipo.contains(buscado) }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(filtrados) { jugador ->
                ElementoLista(
                    jugador = jugador,
                    mostrarCheck = modoBorrar,
                    marcado = jugador in seleccionados,
                    onMarcadoChange = { marcado ->
                        if (marcado) seleccionados.add(jugador)
                        else seleccionados.remove(jugador)
                    }
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            if(!modoBorrar){
                botonesFlotantes("Add", R.drawable.add, onAdd)
            }
            botonesFlotantes(
                if (modoBorrar) "Borrar (${seleccionados.size})" else "Delete",
                R.drawable.delete
            ) {
                if (modoBorrar) {
                    // Segunda pulsación: borrar los marcados
                    borrar = true
                }
                modoBorrar = !modoBorrar
            }
        }
    }
}

@Composable
fun ElementoLista(
    jugador: Partido,
    mostrarCheck: Boolean,
    marcado: Boolean,
    onMarcadoChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            modifier = Modifier.size(100.dp),
            painter = painterResource(jugador.foto),
            contentDescription = "",
            contentScale = ContentScale.Crop,
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(jugador.nombre)
            Text("VS ${jugador.equipoE},${jugador.fecha}")
            Text("${jugador.puntos} PP")
        }

        if (mostrarCheck) {
            Checkbox(
                checked = marcado,
                onCheckedChange = onMarcadoChange
            )
        }
    }
}