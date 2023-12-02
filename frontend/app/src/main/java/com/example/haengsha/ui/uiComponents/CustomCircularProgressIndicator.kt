package com.example.haengsha.ui.uiComponents

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.haengsha.ui.theme.HaengshaBlue

@Composable
fun CustomCircularProgressIndicator() {
    Box(contentAlignment = Alignment.Center) {
        val infiniteTransition =
            rememberInfiniteTransition(label = "circularProgressIndicatorTransition")

        val angle by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = 800
                }
            ), label = "circularProgressIndicatorAnimation"
        )

        CircularProgressIndicator(
            progress = 0f,
            modifier = Modifier
                .size(60.dp)
                .rotate(angle)
                .border(
                    5.dp,
                    brush = Brush.sweepGradient(
                        listOf(
                            Color.Transparent,
                            HaengshaBlue.copy(alpha = 0.6f),
                            HaengshaBlue
                        )
                    ),
                    shape = CircleShape
                ),
            strokeWidth = 3.dp
        )
    }
}