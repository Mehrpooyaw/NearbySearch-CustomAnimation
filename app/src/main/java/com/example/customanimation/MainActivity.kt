package com.example.customanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.customanimation.ui.theme.CustomAnimationTheme
import java.lang.Math.PI
import kotlin.math.atan
import kotlin.math.cos



const val TWO_PI = 2 * PI
const val RADIANS_TO_DEGREES_RATIO = 360f / TWO_PI
val getSlope: (Int, Float, Float) -> Float
        = { x, amplitude, period ->
    (
            cos((TWO_PI * x / period)) * (amplitude * (TWO_PI) / period)
            ).toFloat()
}
val getRotation: (Float) -> Float = { slope ->
    (atan(slope) * RADIANS_TO_DEGREES_RATIO).toFloat()
}
val getY: (Int, Float, Float) -> Float = { x, amplitude, period ->
    (kotlin.math.sin(x * TWO_PI / period) * amplitude).toFloat()
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CustomAnimationTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    var xState by remember { mutableStateOf(0) }
                    var run by remember { mutableStateOf(false)}
                    val xOffset = animateIntAsState(
                        targetValue = if (run) 300 else xState,
                        animationSpec =
                        tween(durationMillis = 3000, easing = LinearEasing)
                    )
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
                        Item(
                            modifier = Modifier.fillMaxSize(fraction = .8f),
                            xOffset = xOffset.value,
                            amplitude = 30.0f,
                            period = 150.0f,
                            getYOffset = getY,
                            getSlope = getSlope,
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.square),
                                contentDescription = null,
                                Modifier.size(20.dp)
                            )
                        }
                        Button(onClick = { run = !run }) {
                            Text("Run")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Item(
    modifier: Modifier,
    xOffset: Int,
    amplitude: Float,
    period: Float,
    getYOffset: (Int, Float, Float) -> Float,
    getSlope: (Int, Float, Float) -> Float,
    item: @Composable () -> Unit
) {

    Box(modifier) {
        Box(
            Modifier
                .offset(
                    x = xOffset.dp,
                    y = getYOffset(xOffset, amplitude, period).dp
                )
                .rotate(getRotation(getSlope(xOffset, amplitude, period)))
                .align(Alignment.CenterStart)
        ) {
            item()
        }
    }
}