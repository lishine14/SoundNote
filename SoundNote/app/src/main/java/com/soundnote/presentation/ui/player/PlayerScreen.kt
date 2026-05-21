package com.soundnote.presentation.ui.player

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.soundnote.presentation.theme.Primary
import com.soundnote.presentation.theme.RecordingRed
import com.soundnote.presentation.viewmodel.PlayerViewModel
import com.soundnote.service.PlaybackState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(
    recordingId: String,
    onNavigateBack: () -> Unit,
    viewModel: PlayerViewModel = hiltViewModel()
) {
    val recording by viewModel.recording.collectAsState()
    val playbackState by viewModel.playbackState.collectAsState()
    val currentPosition by viewModel.currentPosition.collectAsState()
    val duration by viewModel.duration.collectAsState()
    val playbackSpeed by viewModel.playbackSpeed.collectAsState()
    val markers by viewModel.markers.collectAsState()
    val recordingTags by viewModel.recordingTags.collectAsState()

    LaunchedEffect(recordingId) {
        viewModel.loadRecording(recordingId)
    }

    val rec = recording ?: return

    val context = LocalContext.current


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(rec.fileName, maxLines = 1) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "返回")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.toggleFavorite() }) {
                        Icon(
                            if (rec.isFavorite) Icons.Default.Star else Icons.Default.StarBorder,
                            "收藏",
                            tint = if (rec.isFavorite) Color(0xFFFDCB6E) else LocalContentColor.current
                        )
                    }
                    IconButton(onClick = { /* Share */ }) {
                        Icon(Icons.Default.Share, "分享")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Primary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Primary.copy(alpha = 0.08f),
                            MaterialTheme.colorScheme.background
                        )
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(32.dp))

                // Album art / Icon
                Box(
                    modifier = Modifier
                        .size(160.dp)
                        .clip(CircleShape)
                        .background(Primary.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.MusicNote,
                        null,
                        modifier = Modifier.size(80.dp),
                        tint = Primary
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Recording info
                Text(
                    rec.fileName,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Tags
                if (recordingTags.isNotEmpty()) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(horizontal = 24.dp)
                    ) {
                        recordingTags.forEach { tag ->
                            AssistChip(
                                onClick = { },
                                label = { Text(tag.name, style = MaterialTheme.typography.labelSmall) }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Progress slider
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                ) {
                    Slider(
                        value = if (duration > 0) currentPosition.toFloat() / duration else 0f,
                        onValueChange = { value ->
                            viewModel.seekTo((value * duration).toLong())
                        },
                        colors = SliderDefaults.colors(
                            thumbColor = Primary,
                            activeTrackColor = Primary
                        )
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            formatDuration(currentPosition),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            formatDuration(duration),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Playback controls
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 24.dp)
                ) {
                    // Speed control
                    val speedSupported by viewModel.speedSupported.collectAsState()
                    TextButton(
                        onClick = {
                            if (speedSupported) {
                                val speeds = listOf(0.5f, 0.75f, 1f, 1.25f, 1.5f, 2f)
                                val currentIdx = speeds.indexOf(playbackSpeed)
                                val nextIdx = (currentIdx + 1) % speeds.size
                                viewModel.setSpeed(speeds[nextIdx])
                            } else {
                                Toast.makeText(context, "当前设备不支持播放倍率调节", Toast.LENGTH_SHORT).show()
                            }
                        }
                    ) {
                        Text(
                            "${playbackSpeed}x",
                            style = MaterialTheme.typography.labelLarge,
                            color = if (speedSupported) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                            }
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // Rewind 15s
                    IconButton(
                        onClick = { viewModel.seekBackward() },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(Icons.Default.Replay10, "后退15秒", modifier = Modifier.size(32.dp))
                    }

                    // Play/Pause
                    FilledIconButton(
                        onClick = {
                            when (playbackState) {
                                is PlaybackState.Playing -> viewModel.pause()
                                else -> viewModel.play()
                            }
                        },
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = Primary
                        ),
                        modifier = Modifier.size(72.dp)
                    ) {
                        Icon(
                            if (playbackState is PlaybackState.Playing)
                                Icons.Default.Pause
                            else
                                Icons.Default.PlayArrow,
                            "播放",
                            modifier = Modifier.size(40.dp),
                            tint = Color.White
                        )
                    }

                    // Forward 15s
                    IconButton(
                        onClick = { viewModel.seekForward() },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(Icons.Default.Forward10, "前进15秒", modifier = Modifier.size(32.dp))
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // Export button
                    IconButton(onClick = { /* Export */ }) {
                        Icon(Icons.Default.FileDownload, "导出")
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Recording details
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "录音信息",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        DetailRow("格式", rec.format.uppercase())
                        DetailRow("采样率", "${rec.sampleRate / 1000}kHz")
                        DetailRow("比特率", "${rec.bitRate / 1000}kbps")
                        DetailRow("文件大小", rec.formattedFileSize)
                        DetailRow("时长", rec.formattedDuration)
                    }
                }

                // Markers
                if (markers.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "标记 (${markers.size})",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            markers.forEach { marker ->
                                Surface(
                                    onClick = { viewModel.seekTo(marker.timestampMs) },
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            Icons.Default.Flag,
                                            null,
                                            tint = Primary,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            marker.formattedTimestamp,
                                            style = MaterialTheme.typography.labelMedium,
                                            color = Primary
                                        )
                                        if (!marker.note.isNullOrBlank()) {
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(
                                                marker.note,
                                                style = MaterialTheme.typography.bodySmall,
                                                modifier = Modifier.weight(1f)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

private fun formatDuration(ms: Long): String {
    val totalSeconds = ms / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format("%02d:%02d", minutes, seconds)
}
