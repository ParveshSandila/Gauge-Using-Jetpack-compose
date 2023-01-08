package com.example.gaugeusingjetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gaugeusingjetpackcompose.ui.theme.GaugeUsingJetpackComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GaugeUsingJetpackComposeTheme {
                // A surface container using the 'background' color from the theme
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            horizontal = 20.dp,
                            vertical = 20.dp
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    CustomGauge(
                        modifier = Modifier.size(280.dp),
                        percentage = 30f
                    )
                }
            }
        }
    }
}

@Composable
fun CustomGauge(
    modifier: Modifier = Modifier,
    trackColor : Color = Color.LightGray,
    gaugeColor : Color = Color.Green,
    percentage : Float = 0f
){
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }

    var sweepAngle by remember {
        mutableStateOf(0f)
    }

    val animatedSweepAngle by animateFloatAsState(
        targetValue = sweepAngle,
        animationSpec = tween(
            delayMillis = 100,
            durationMillis = 300,
            easing = LinearEasing
        )
    )

    LaunchedEffect(key1 = percentage){
        sweepAngle = (240 * percentage) / 100
    }

    Box(
        modifier = modifier.onSizeChanged {
            size = it
        },
        contentAlignment = Alignment.Center
    ) {

        Canvas(
            modifier = modifier
        ){

            drawArc(
                color = trackColor,
                size = Size(size.width.toFloat(), size.height.toFloat()),
                startAngle = -210f,
                sweepAngle = 240f,
                useCenter = false,
                style = Stroke(
                    width = 60f,
                    cap = StrokeCap.Butt
                )
            )

            drawArc(
                color = Color.White,
                size = Size(size.width.toFloat(), size.height.toFloat()),
                startAngle = -210f,
                sweepAngle = animatedSweepAngle + 2f,
                useCenter = false,
                style = Stroke(
                    width = 50f,
                    cap = StrokeCap.Butt
                )
            )

            drawArc(
                color = gaugeColor,
                size = Size(size.width.toFloat(), size.height.toFloat()),
                startAngle = -210f,
                sweepAngle = animatedSweepAngle,
                useCenter = false,
                style = Stroke(
                    width = 60f,
                    cap = StrokeCap.Butt
                )
            )
        }

        Text(
            text = "$percentage%",
            style = TextStyle(
                color = gaugeColor,
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}