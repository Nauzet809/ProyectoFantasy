package com.example.proyectofantasy.ui.pantallas


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.aplicacindepreguntas.model.Partido

@Composable
fun PantallaDatos(jugador: Partido){
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(10.dp)
    ) {
        Text(jugador.nombre)
        Image(
            painter = painterResource(jugador.foto),
            contentDescription = "",
            contentScale = ContentScale.FillWidth,
        )
        Text(jugador.descripcion)
    }
}