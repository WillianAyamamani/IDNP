package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFFFFBFE) // Fondo pastel claro
                ) {
                    val personas = generarPersonas()

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(personas) { persona ->
                            TarjetaPersona(persona)
                        }
                    }
                }
            }
        }
    }
}

// ---------------------- DATA CLASS ----------------------

data class Persona(
    val cui: String,
    val nombres: String,
    val apellidos: String,
    val avatarUrl: String
)

// ---------------------- GENERACIÓN DE DATOS ----------------------

fun generarPersonas(): List<Persona> {
    val nombres = listOf(
        "Lucas", "Valeria", "Sofía", "Mateo", "Camila",
        "Tomás", "María", "Andrés", "Daniela", "Julián"
    )
    val apellidos = listOf(
        "Gómez", "Fernández", "Rodríguez", "Pérez", "López",
        "Martínez", "Torres", "Sánchez", "Ramírez", "Castro"
    )

    val lista = mutableListOf<Persona>()
    for (i in 1..20) {
        val nombre = nombres.random()
        val apellido = apellidos.random()
        val cui = "CUI - ${Random.nextInt(1000, 9999)}"
        val avatarUrl = "https://randomuser.me/api/portraits/men/${i}.jpg"
        lista.add(Persona(cui, nombre, apellido, avatarUrl))
    }
    return lista
}

// ---------------------- COMPONENTE DE TARJETA ----------------------

@Composable
fun TarjetaPersona(persona: Persona) {
    var seleccionado by remember { mutableStateOf(false) }

    // Paleta de colores pastel
    val coloresPastel = listOf(
        Color(0xFFFFF3E0), // naranja claro
        Color(0xFFE1F5FE), // celeste
        Color(0xFFF3E5F5), // lila
        Color(0xFFFFEBEE), // rosa claro
        Color(0xFFE8F5E9)  // verde menta
    )

    val colorBase = coloresPastel.random()

    val colorFondo by animateColorAsState(
        targetValue = if (seleccionado) colorBase.copy(alpha = 0.8f) else Color.White,
        animationSpec = tween(400)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(colorFondo, RoundedCornerShape(16.dp))
            .border(1.dp, Color(0xFFDADADA), RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .clickable { seleccionado = !seleccionado }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Imagen del usuario
        Image(
            painter = rememberAsyncImagePainter(persona.avatarUrl),
            contentDescription = null,
            modifier = Modifier
                .size(70.dp)
                .clip(RoundedCornerShape(50))
                .border(1.dp, Color.Gray.copy(alpha = 0.3f), RoundedCornerShape(50)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Datos del usuario
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = persona.nombres,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = persona.apellidos,
                color = Color(0xFF555555),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = persona.cui,
                color = Color(0xFF777777),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}
