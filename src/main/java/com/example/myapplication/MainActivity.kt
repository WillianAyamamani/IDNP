package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFF8F9FA) // Fondo pastel claro
                ) {
                    PantallaAnimacionCirculo()
                }
            }
        }
    }
}

@Composable
fun PantallaAnimacionCirculo() {
    var tamano by remember { mutableStateOf(100.dp) }

    // Animación suave del tamaño
    val tamanoAnimado by animateDpAsState(
        targetValue = tamano,
        animationSpec = tween(durationMillis = 600)
    )

    // Animación de color basada en el tamaño
    val colorCirculo by animateColorAsState(
        targetValue = when {
            tamano < 120.dp -> Color(0xFFFFCDD2) // rosa pastel
            tamano < 180.dp -> Color(0xFFFFF9C4) // amarillo pastel
            else -> Color(0xFFB3E5FC) // celeste pastel
        },
        animationSpec = tween(700)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFBFE))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Círculo animado
        Canvas(
            modifier = Modifier
                .size(tamanoAnimado)
                .shadow(8.dp, CircleShape)
        ) {
            drawCircle(color = colorCirculo)
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Contenedor de botones
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Botón para reducir
            Button(
                onClick = {
                    if (tamano > 60.dp) tamano -= 20.dp
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF48FB1) // rosa pastel
                ),
                shape = CircleShape,
                modifier = Modifier
                    .width(140.dp)
                    .height(48.dp)
            ) {
                Text(
                    text = "Reducir",
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            // Botón para agrandar
            Button(
                onClick = {
                    if (tamano < 220.dp) tamano += 20.dp
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF81D4FA) // celeste pastel
                ),
                shape = CircleShape,
                modifier = Modifier
                    .width(140.dp)
                    .height(48.dp)
            ) {
                Text(
                    text = "Agrandar",
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

