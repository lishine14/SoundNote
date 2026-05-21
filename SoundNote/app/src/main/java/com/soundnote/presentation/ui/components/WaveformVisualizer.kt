package com.soundnote.presentation.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.soundnote.presentation.theme.Primary
import com.soundnote.presentation.theme.WaveBackground
import com.soundnote.presentation.theme.WaveColor

@Composable
fun WaveformVisualizer(
    amplitudes: List<Float>,
    modifier: Modifier = Modifier,
    barColor: Color = WaveColor,
    backgroundColor: Color = WaveBackground,
    isRecording: Boolean = true
) {
    val infiniteTransition = rememberInfiniteTransition(label = "wave")
    val animatedAlpha by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val barWidth = 4.dp.toPx()
            val barSpacing = 3.dp.toPx()
            val totalBarWidth = barWidth + barSpacing
            val numBars = (size.width / totalBarWidth).toInt()
            val centerY = size.height / 2

            // Display up to numBars bars
            val displayAmplitudes = if (amplitudes.isEmpty()) {
                List(numBars) { 0.05f }
            } else if (amplitudes.size >= numBars) {
                amplitudes.takeLast(numBars)
            } else {
                // Pad with zeros at the beginning
                List(numBars - amplitudes.size) { 0.05f } + amplitudes
            }

            displayAmplitudes.forEachIndexed { index, amp ->
                val barHeight = (amp * size.height * 0.8f).coerceAtLeast(4.dp.toPx())
                val x = index * totalBarWidth
                val y = centerY - barHeight / 2

                drawRoundRect(
                    color = barColor.copy(alpha = if (isRecording) animatedAlpha else 0.7f),
                    topLeft = Offset(x, y),
                    size = Size(barWidth, barHeight),
                    cornerRadius = CornerRadius(2.dp.toPx())
                )
            }
        }
    }
}

@Composable
fun RecordingButton(
    isRecording: Boolean,
    isPaused: Boolean,
    onStart: () -> Unit,
    onPause: () -> Unit,
    onResume: () -> Unit,
    onStop: () -> Unit,
    modifier: Modifier = Modifier
) {
    val buttonColor = when {
        isRecording && !isPaused -> Color(0xFFFF4757)
        isPaused -> Color(0xFFFDCB6E)
        else -> Primary
    }

    androidx.compose.material3.FloatingActionButton(
        onClick = {
            when {
                !isRecording && !isPaused -> onStart()
                isRecording && !isPaused -> onPause()
                isPaused -> onResume()
            }
        },
        containerColor = buttonColor,
        modifier = modifier.size(72.dp)
    ) {
        androidx.compose.material3.Icon(
            imageVector = when {
                !isRecording && !isPaused -> androidx.compose.material.icons.Icons.Default.Mic
                isPaused -> androidx.compose.material.icons.Icons.Default.PlayArrow
                else -> androidx.compose.material.icons.Icons.Default.Pause
            },
            contentDescription = "录音按钮",
            tint = Color.White,
            modifier = Modifier.size(36.dp)
        )
    }
}
