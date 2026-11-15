package com.example.proyectofantasy.ui.pantallas

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.unit.dp
import com.example.aplicacindepreguntas.data.listaEquipos
import com.example.aplicacindepreguntas.model.Partido
import com.example.aplicacindepreguntas.ui.componentes.pantallas.botonesFlotantes
import com.example.proyectofantasy.R
import kotlinx.coroutines.launch
import java.nio.file.WatchEvent
import kotlin.text.contains

@Composable
fun CrearPantallaLista(listaJugadores : List<Partido>, onAdd : () -> Unit){
    Column(

    ) {
        var buscado by remember { mutableStateOf( "") }
        var borrar by remember { mutableStateOf( false) }

        Text("Titulo")

        buscado = buscadorTipado(listaJugadores)

        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .weight(1f)) {
            items(1) {
                listaJugadores.forEach {
                        jugador ->
                    if(jugador.equipo.contains(buscado)){
                    ElementoLista(jugador, borrar)
                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            botonesFlotantes("Add", R.drawable.add, onAdd)
            botonesFlotantes("Delete", R.drawable.delete,
                {
                    borrar = !borrar
                    if(borrar){

                    }
                })
        }

    }

}

@Composable
fun ElementoLista(jugador : Partido, borrar : Boolean){
    var marcado by remember { mutableStateOf(false)}
    var listaBorrado = remember { mutableListOf<Partido>() }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(100.dp),
            painter = painterResource(jugador.foto),
            contentDescription = "",
            contentScale = ContentScale.Crop,
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(jugador.nombre)
            Text("VS ${jugador.equipoE},${jugador.fecha}")
            Text("${jugador.puntos} PP")
        }
        if(borrar){
            Checkbox(
                onCheckedChange = {
                    marcado = !marcado
//                    if(marcado){
//                        listaBorrado.add(jugador)
//                    }
                },
                checked = marcado,
                enabled = borrar
            )
        }


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun buscador(listaJugadores : List<Partido>){
    val textFieldState = rememberTextFieldState()
    var estado by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = Modifier.fillMaxWidth()
        ,expanded = estado
        , onExpandedChange = {
                nuevoEstado -> estado = nuevoEstado
        } ) {


        // TODO: Gestionar los eventos, añadir un icono, titulo y placeholder.
        TextField(
            state = textFieldState,
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(
                    PrimaryNotEditable, enabled = true
                )
        )

        DropdownMenu(expanded = estado,
            onDismissRequest = {
                estado = false
            }) {
            /*TODO: Añadir los diferentes elementos al menú, usando el Composable
            *  DropdownMenuItem, y la lista listaOpciones */
            for (i in listaEquipos){
                DropdownMenuItem(
                    modifier = Modifier.fillMaxSize(),
                    text = { Text(i) },
                    onClick = { }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun buscadorTipado(listaJugadores : List<Partido>) : String{
    var buscado by remember { mutableStateOf( "") }
    val scope = rememberCoroutineScope() // Similar a un Thread en java
    var estadoBarra = rememberSearchBarState()
    var estadoTexto = rememberTextFieldState()

    SearchBar(
        state = estadoBarra,
        inputField = {
            SearchBarDefaults.InputField(
                textFieldState = estadoTexto,
                searchBarState = estadoBarra,
                onSearch = {
                },
                placeholder = { Text("Buscar...") },
                //leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") }
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )

    ExpandedFullScreenSearchBar(
        state = estadoBarra,
        inputField = {
            SearchBarDefaults.InputField(
                textFieldState = estadoTexto,
                searchBarState = estadoBarra,
                onSearch = {},
                placeholder = { Text("Buscar...") },
                //leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            items(listaEquipos){
                if(it.contains(estadoTexto.text, ignoreCase = true)){
                    ListItem(headlineContent = {Text(it)}
                        , Modifier.clickable{
                            buscado = it
                            scope.launch {  estadoBarra.animateToCollapsed() }
                        })
                }
            }

        }

    }
    return buscado
}

@Composable
fun botones(texto : String, icono : Int, onPulsado : () -> Unit){
    Button(
        modifier = Modifier,
        colors = ButtonDefaults.buttonColors(
        ),
        onClick = onPulsado

    ) {
        Text(texto)
        Icon(
            painter = painterResource(icono),
            contentDescription = "",
        )
    }
}