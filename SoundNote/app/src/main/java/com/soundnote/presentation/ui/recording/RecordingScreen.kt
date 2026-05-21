package com.soundnote.presentation.ui.recording

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.soundnote.domain.model.RecordingFormat
import com.soundnote.domain.model.RecordingQuality
import com.soundnote.domain.model.RecordingState
import com.soundnote.domain.model.RecordingState.Recording as RecordingData
import com.soundnote.presentation.theme.*
import com.soundnote.presentation.ui.components.RecordingButton
import com.soundnote.presentation.ui.components.WaveformVisualizer
import com.soundnote.presentation.viewmodel.RecordingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordingScreen(
    onNavigateToLibrary: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToPlayer: (String) -> Unit,
    viewModel: RecordingViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val recordingState by viewModel.recordingState.collectAsState()
    val elapsedTime by viewModel.elapsedTime.collectAsState()
    val amplitudes by viewModel.amplitudes.collectAsState()
    val currentFormat by viewModel.currentFormat.collectAsState()
    val currentQuality by viewModel.currentQuality.collectAsState()
    val isProfessionalMode by viewModel.isProfessionalMode.collectAsState()
    val recentRecordings by viewModel.recentRecordings.collectAsState()
    val saveResult by viewModel.saveResult.collectAsState(initial = false)

    // Permission state
    var showPermissionDialog by remember { mutableStateOf(false) }
    var permissionDenied by remember { mutableStateOf(false) }

    // Check if permissions are already granted
    val hasRecordAudioPermission = remember {
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED
    }

    val hasNotificationPermission = remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    val hasPermissions = hasRecordAudioPermission && hasNotificationPermission

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.values.all { it }
        if (!allGranted) {
            permissionDenied = true
        }
        showPermissionDialog = false
    }

    LaunchedEffect(saveResult) {
        if (saveResult) {
            // Show snackbar
        }
    }

    val isRecording = recordingState is RecordingData
    val isPaused = recordingState is RecordingState.Paused

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "SoundNote",
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    // Mode toggle
//                    FilterChip(
//                        selected = isProfessionalMode,
//                        onClick = { viewModel.toggleProfessionalMode() },
//                        label = {
//                            Text(
//                                if (isProfessionalMode) "专业" else "基础",
//                                color = if (isProfessionalMode) Primary else Color.White
//                            )
//                        },
//                        colors = FilterChipDefaults.filterChipColors(
//                            containerColor = Color.White.copy(alpha = 0.2f),
//                            labelColor = Color.White,
//                            selectedContainerColor = Color.White,
//                            selectedLabelColor = Primary
//                        ),
//                        modifier = Modifier.padding(end = 8.dp)
//                    )
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(Icons.Default.Settings, "设置")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Primary,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Recording area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Primary.copy(alpha = 0.05f),
                                MaterialTheme.colorScheme.background
                            )
                        )
                    )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp, vertical = 16.dp)
                ) {
                    // Waveform
                    WaveformVisualizer(
                        amplitudes = amplitudes,
                        isRecording = isRecording && !isPaused,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Timer
                    Text(
                        text = formatTime(elapsedTime),
                        fontSize = 44.sp,
                        fontWeight = FontWeight.Light,
                        color = when {
                            isRecording && !isPaused -> RecordingRed
                            isPaused -> RecordingPaused
                            else -> MaterialTheme.colorScheme.onBackground
                        }
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Format & Quality info
                    Text(
                        text = "${currentFormat.displayName} · ${currentQuality.displayName}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    // Status text
                    AnimatedVisibility(
                        visible = isRecording || isPaused,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Text(
                            text = when {
                                isRecording && !isPaused -> "正在录音..."
                                isPaused -> "已暂停"
                                else -> ""
                            },
                            style = MaterialTheme.typography.bodyMedium,
                            color = when {
                                isPaused -> RecordingPaused
                                else -> RecordingRed
                            },
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f, fill = false))

                    // Recording controls
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(24.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (isRecording || isPaused) {
                            // Stop button
                            FilledIconButton(
                                onClick = { viewModel.stopRecording() },
                                colors = IconButtonDefaults.filledIconButtonColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                                ),
                                modifier = Modifier.size(56.dp)
                            ) {
                                Icon(
                                    Icons.Default.Stop,
                                    "停止",
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        }

                        // Main recording button
                        RecordingButton(
                            isRecording = isRecording,
                            isPaused = isPaused,
                            onStart = {
                                if (hasPermissions) {
                                    viewModel.startRecording()
                                } else {
                                    showPermissionDialog = true
                                }
                            },
                            onPause = { viewModel.pauseRecording() },
                            onResume = { viewModel.resumeRecording() },
                            onStop = { viewModel.stopRecording() },
                            modifier = Modifier.size(72.dp)
                        )

                        if (isRecording || isPaused) {
                            // Marker button (professional mode)
                            if (isProfessionalMode) {
                                FilledIconButton(
                                    onClick = { /* Add marker */ },
                                    colors = IconButtonDefaults.filledIconButtonColors(
                                        containerColor = Primary
                                    ),
                                    modifier = Modifier.size(56.dp)
                                ) {
                                    Icon(
                                        Icons.Default.Flag,
                                        "标记",
                                        modifier = Modifier.size(28.dp)
                                    )
                                }
                            } else {
                                Spacer(modifier = Modifier.size(56.dp))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Format & Quality selector (only when not recording)
                    AnimatedVisibility(
                        visible = !isRecording && !isPaused,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Format selector
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                RecordingFormat.entries.forEach { format ->
                                    FilterChip(
                                        selected = currentFormat == format,
                                        onClick = { viewModel.setFormat(format) },
                                        label = { Text(format.displayName) }
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            // Quality selector
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                RecordingQuality.entries.forEach { quality ->
                                    FilterChip(
                                        selected = currentQuality == quality,
                                        onClick = { viewModel.setQuality(quality) },
                                        label = {
                                            Text(
                                                quality.displayName,
                                                style = MaterialTheme.typography.labelSmall
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Recent recordings - 只在非录音状态下显示，避免遮挡录音按钮
            AnimatedVisibility(
                visible = recentRecordings.isNotEmpty() && !isRecording && !isPaused,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "最近录音",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold
                        )
                        TextButton(
                            onClick = onNavigateToLibrary,
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp),
                            modifier = Modifier.height(28.dp)
                        ) {
                            Text("查看全部", style = MaterialTheme.typography.labelSmall)
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.heightIn(max = 120.dp)
                    ) {
                        items(
                            items = recentRecordings.take(2),
                            key = { it.id }
                        ) { recording ->
                            RecentRecordingItem(
                                recording = recording,
                                onClick = { onNavigateToPlayer(recording.id) }
                            )
                        }
                    }
                }
            }
        }
    }

    // Permission Dialog - only show when user clicks record without permission
    if (showPermissionDialog) {
        AlertDialog(
            onDismissRequest = { showPermissionDialog = false },
            title = { Text("需要权限") },
            text = { Text("录音功能需要麦克风权限，点击授权按钮请求权限。") },
            confirmButton = {
                TextButton(
                    onClick = {
                        val permissions = buildList {
                            add(Manifest.permission.RECORD_AUDIO)
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                add(Manifest.permission.POST_NOTIFICATIONS)
                            }
                        }
                        permissionLauncher.launch(permissions.toTypedArray())
                    }
                ) {
                    Text("授权")
                }
            },
            dismissButton = {
                TextButton(onClick = { showPermissionDialog = false }) {
                    Text("取消")
                }
            }
        )
    }

    // Permission denied info snackbar
    if (permissionDenied) {
        LaunchedEffect(permissionDenied) {
            // Reset after showing
            permissionDenied = false
        }
    }
}

@Composable
private fun RecentRecordingItem(
    recording: com.soundnote.domain.model.Recording,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.PlayCircle,
                null,
                tint = Primary,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    recording.fileName,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    "${recording.formattedDuration} · ${recording.formattedFileSize}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

private fun formatTime(ms: Long): String {
    val totalSeconds = ms / 1000
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60
    return if (hours > 0) {
        String.format("%02d:%02d:%02d", hours, minutes, seconds)
    } else {
        String.format("%02d:%02d", minutes, seconds)
    }
}
