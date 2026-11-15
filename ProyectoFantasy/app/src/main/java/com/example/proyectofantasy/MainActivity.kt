package com.example.proyectofantasy

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.rememberNavBackStack
import com.example.aplicacindepreguntas.data.inicializarYCargar
import com.example.aplicacindepreguntas.navigation.Lista
import com.example.aplicacindepreguntas.navigation.NavDisplay

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val lista = inicializarYCargar(this)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                val pila = rememberNavBackStack(Lista)
                NavDisplay(pila = pila, lista)
            }

        }
    }
}