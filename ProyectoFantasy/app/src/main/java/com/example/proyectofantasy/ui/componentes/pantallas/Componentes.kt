package com.example.aplicacindepreguntas.ui.componentes.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExpandedFullScreenSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType.Companion.PrimaryNotEditable
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.example.aplicacindepreguntas.data.listaEquipos
import com.example.aplicacindepreguntas.model.Partido
import com.example.proyectofantasy.R
import kotlinx.coroutines.launch

@Composable
fun botonesFlotantes(texto : String, icono : Int, onPulsado : () -> Unit){
    ExtendedFloatingActionButton(
        modifier = Modifier,
        onClick = onPulsado,
        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 15.dp)
    ) {
        Text(texto)
        Icon(
            painter = painterResource(icono),
            contentDescription = "",
        )
    }
}

@Composable
fun botonesFlotantesBorrar(texto : String, icono : Int, onPulsado : () -> Unit){
    ExtendedFloatingActionButton(
        modifier = Modifier,
        onClick = onPulsado,
        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 15.dp)
    ) {
        Text(texto)
        Icon(
            painter = painterResource(icono),
            contentDescription = "",
        )
    }
}

@Composable
fun Dialogo(alConfirmar : () -> Unit, alCancelar : () -> Unit){
    var mostrar by remember { mutableStateOf(true) }

    AlertDialog(
        onDismissRequest = alCancelar,
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                Text("¿Quiere borrar los jugadores seleccionados?")
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    alConfirmar()
                }
            ) {
                Text("Borrar")
            }
        },
        dismissButton = {
            TextButton(
                onClick = alCancelar
            ) {
                Text("Cancelar")
            }
        }
    )

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