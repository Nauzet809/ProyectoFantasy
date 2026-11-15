package com.example.aplicacindepreguntas.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.aplicacindepreguntas.data.guardarAFichero
import com.example.aplicacindepreguntas.model.Partido
import com.example.proyectofantasy.ui.pantallas.CrearPantallaLista
import com.example.proyectofantasy.ui.pantallas.CrearPantallaListaPrueba
import com.example.proyectofantasy.ui.pantallas.PantallaFormulario

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavDisplay(
    pila : NavBackStack<NavKey>,
    listaPartidos : List<Partido>,
) {
    var jugadores = remember { mutableStateListOf<Partido>().apply { addAll(listaPartidos) } }
    var listaAux = remember { listaPartidos.toMutableStateList() }
    var contexto =  LocalContext.current


    NavDisplay(
        modifier = Modifier.padding(horizontal = 5.dp, 40.dp ),
        backStack = pila,
        onBack = {pila.removeLastOrNull()},


        entryProvider = entryProvider {
            entry<Lista> {
                CrearPantallaListaPrueba(
                    listaJugadores = jugadores,
                    onAdd = { pila.add(Anadir) },
                    onBorrar = { aBorrar ->
                        jugadores.removeAll(aBorrar)
                        guardarAFichero(contexto, jugadores.toList())
                    }
                )
            }

            entry<Anadir> {
                PantallaFormulario({it -> jugadores.add(it)})
            }

        }

    )
}
