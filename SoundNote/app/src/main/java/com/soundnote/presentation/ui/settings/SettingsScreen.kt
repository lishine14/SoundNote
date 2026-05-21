package com.soundnote.presentation.ui.settings

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.soundnote.domain.model.RecordingFormat
import com.soundnote.domain.model.RecordingQuality
import com.soundnote.presentation.theme.Primary
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("设置", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "返回")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Primary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Text(
                    "录音设置",
                    style = MaterialTheme.typography.titleSmall,
                    color = Primary,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            item {
                SettingsCard {
                    SettingsItem(
                        icon = Icons.Default.HighQuality,
                        title = "默认录音格式",
                        subtitle = uiState.defaultFormat.displayName,
                        onClick = { viewModel.showFormatDialog() }
                    )
                    HorizontalDivider()
                    SettingsItem(
                        icon = Icons.Default.Speed,
                        title = "默认录音品质",
                        subtitle = uiState.defaultQuality.displayName,
                        onClick = { viewModel.showQualityDialog() }
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "存储",
                    style = MaterialTheme.typography.titleSmall,
                    color = Primary,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            item {
                SettingsCard {
                    val recordingsDir = File(context.filesDir, "recordings")
                    val usedSpace = remember { calculateFolderSize(recordingsDir) }
                    val freeSpace = context.filesDir.freeSpace

                    SettingsItem(
                        icon = Icons.Default.Folder,
                        title = "存储位置",
                        subtitle = "应用私有目录",
                        onClick = {
                            Toast.makeText(context, "存储位置: ${recordingsDir.absolutePath}", Toast.LENGTH_LONG).show()
                        }
                    )
                    HorizontalDivider()
                    SettingsItem(
                        icon = Icons.Default.Storage,
                        title = "存储空间",
                        subtitle = "已使用 ${formatFileSize(usedSpace)} / 可用 ${formatFileSize(freeSpace)}",
                        onClick = { viewModel.showStorageInfo() }
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "关于",
                    style = MaterialTheme.typography.titleSmall,
                    color = Primary,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            item {
                SettingsCard {
                    SettingsItem(
                        icon = Icons.Default.Info,
                        title = "版本",
                        subtitle = "1.0.0",
                        onClick = {
                            Toast.makeText(context, "SoundNote v1.0.0", Toast.LENGTH_SHORT).show()
                        }
                    )
                    HorizontalDivider()
                    SettingsItem(
                        icon = Icons.AutoMirrored.Filled.Help,
                        title = "使用帮助",
                        subtitle = "了解如何使用 SoundNote",
                        onClick = {
                            Toast.makeText(context, "使用帮助功能即将上线", Toast.LENGTH_SHORT).show()
                        }
                    )
                    HorizontalDivider()
                    SettingsItem(
                        icon = Icons.Default.Feedback,
                        title = "意见反馈",
                        subtitle = "告诉我们您的想法",
                        onClick = {
                            val intent = Intent(Intent.ACTION_SENDTO).apply {
                                data = Uri.parse("mailto:")
                                putExtra(Intent.EXTRA_SUBJECT, "SoundNote 意见反馈")
                            }
                            try {
                                context.startActivity(intent)
                            } catch (e: Exception) {
                                Toast.makeText(context, "没有可用的邮件应用", Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
    }

    // Format Selection Dialog
    if (uiState.showFormatDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissDialogs() },
            title = { Text("选择录音格式") },
            text = {
                Column {
                    RecordingFormat.entries.forEachIndexed { index, format ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.setDefaultFormat(format)
                                }
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = uiState.defaultFormat == format,
                                onClick = { viewModel.setDefaultFormat(format) }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(format.displayName)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { viewModel.dismissDialogs() }) {
                    Text("取消")
                }
            }
        )
    }

    // Quality Selection Dialog
    if (uiState.showQualityDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissDialogs() },
            title = { Text("选择录音品质") },
            text = {
                Column {
                    RecordingQuality.entries.forEach { quality ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.setDefaultQuality(quality)
                                }
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = uiState.defaultQuality == quality,
                                onClick = { viewModel.setDefaultQuality(quality) }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(quality.displayName)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { viewModel.dismissDialogs() }) {
                    Text("取消")
                }
            }
        )
    }

    // Storage Info Dialog
    if (uiState.showStorageInfo) {
        val recordingsDir = File(context.filesDir, "recordings")
        val usedSpace = remember { calculateFolderSize(recordingsDir) }
        val freeSpace = context.filesDir.freeSpace

        AlertDialog(
            onDismissRequest = { viewModel.dismissDialogs() },
            title = { Text("存储空间") },
            text = {
                Column {
                    Text("已使用空间: ${formatFileSize(usedSpace)}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("可用空间: ${formatFileSize(freeSpace)}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("总空间: ${formatFileSize(usedSpace + freeSpace)}")
                }
            },
            confirmButton = {
                TextButton(onClick = { viewModel.dismissDialogs() }) {
                    Text("确定")
                }
            }
        )
    }
}

@Composable
private fun SettingsCard(content: @Composable ColumnScope.() -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(content = content)
    }
}

@Composable
private fun SettingsItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = Primary, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.bodyLarge)
            Text(
                subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Icon(
            Icons.Default.ChevronRight,
            null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

private fun calculateFolderSize(folder: File): Long {
    if (!folder.exists()) return 0
    var size = 0L
    folder.listFiles()?.forEach { file ->
        size += if (file.isDirectory) calculateFolderSize(file) else file.length()
    }
    return size
}

private fun formatFileSize(size: Long): String {
    return when {
        size >= 1024 * 1024 * 1024 -> String.format("%.2f GB", size / (1024.0 * 1024.0 * 1024.0))
        size >= 1024 * 1024 -> String.format("%.2f MB", size / (1024.0 * 1024.0))
        size >= 1024 -> String.format("%.2f KB", size / 1024.0)
        else -> "$size B"
    }
}
