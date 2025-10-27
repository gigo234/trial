package com.autolinkopener

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.autolinkopener.ui.theme.AutoLinkOpenerTheme
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

data class ScheduledLink(
    val id: String = UUID.randomUUID().toString(),
    val url: String,
    val hour: Int,
    val minute: Int,
    val enabled: Boolean = true
)

class MainActivity : ComponentActivity() {
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission granted
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Request notification permission for Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        setContent {
            AutoLinkOpenerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val context = androidx.compose.ui.platform.LocalContext.current
    var scheduledLinks by remember { mutableStateOf(loadScheduledLinks(context)) }
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Auto Link Opener") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Link")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (scheduledLinks.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Tidak ada link terjadwal.\nTekan + untuk menambahkan.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(scheduledLinks) { link ->
                        LinkCard(
                            link = link,
                            onDelete = {
                                scheduledLinks = scheduledLinks.filter { it.id != link.id }
                                saveScheduledLinks(context, scheduledLinks)
                                cancelAlarm(context, link)
                            },
                            onToggle = { enabled ->
                                scheduledLinks = scheduledLinks.map {
                                    if (it.id == link.id) it.copy(enabled = enabled) else it
                                }
                                saveScheduledLinks(context, scheduledLinks)
                                if (enabled) {
                                    scheduleAlarm(context, link)
                                } else {
                                    cancelAlarm(context, link)
                                }
                            }
                        )
                    }
                }
            }
        }

        if (showDialog) {
            AddLinkDialog(
                onDismiss = { showDialog = false },
                onAdd = { url, hour, minute ->
                    val newLink = ScheduledLink(
                        url = url,
                        hour = hour,
                        minute = minute
                    )
                    scheduledLinks = scheduledLinks + newLink
                    saveScheduledLinks(context, scheduledLinks)
                    scheduleAlarm(context, newLink)
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun LinkCard(
    link: ScheduledLink,
    onDelete: () -> Unit,
    onToggle: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = link.url,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = String.format("%02d:%02d", link.hour, link.minute),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Switch(
                    checked = link.enabled,
                    onCheckedChange = onToggle
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = onDelete) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddLinkDialog(
    onDismiss: () -> Unit,
    onAdd: (String, Int, Int) -> Unit
) {
    var url by remember { mutableStateOf("") }
    var hour by remember { mutableStateOf("12") }
    var minute by remember { mutableStateOf("0") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Tambah Link Terjadwal") },
        text = {
            Column {
                OutlinedTextField(
                    value = url,
                    onValueChange = { url = it },
                    label = { Text("URL") },
                    placeholder = { Text("https://example.com") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = hour,
                        onValueChange = { if (it.length <= 2) hour = it },
                        label = { Text("Jam") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = minute,
                        onValueChange = { if (it.length <= 2) minute = it },
                        label = { Text("Menit") },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val h = hour.toIntOrNull() ?: 0
                    val m = minute.toIntOrNull() ?: 0
                    if (url.isNotBlank() && h in 0..23 && m in 0..59) {
                        onAdd(url, h, m)
                    }
                }
            ) {
                Text("Tambah")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Batal")
            }
        }
    )
}

private fun loadScheduledLinks(context: Context): List<ScheduledLink> {
    val prefs = context.getSharedPreferences("scheduled_links", Context.MODE_PRIVATE)
    val json = prefs.getString("links", "[]") ?: "[]"
    val type = object : TypeToken<List<ScheduledLink>>() {}.type
    return Gson().fromJson(json, type)
}

private fun saveScheduledLinks(context: Context, links: List<ScheduledLink>) {
    val prefs = context.getSharedPreferences("scheduled_links", Context.MODE_PRIVATE)
    val json = Gson().toJson(links)
    prefs.edit().putString("links", json).apply()
}

private fun scheduleAlarm(context: Context, link: ScheduledLink) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, AlarmReceiver::class.java).apply {
        putExtra("url", link.url)
        putExtra("link_id", link.id)
    }
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        link.id.hashCode(),
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, link.hour)
        set(Calendar.MINUTE, link.minute)
        set(Calendar.SECOND, 0)
        
        // If time has passed today, schedule for tomorrow
        if (timeInMillis <= System.currentTimeMillis()) {
            add(Calendar.DAY_OF_YEAR, 1)
        }
    }

    alarmManager.setRepeating(
        AlarmManager.RTC_WAKEUP,
        calendar.timeInMillis,
        AlarmManager.INTERVAL_DAY,
        pendingIntent
    )
}

private fun cancelAlarm(context: Context, link: ScheduledLink) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, AlarmReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        link.id.hashCode(),
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
    alarmManager.cancel(pendingIntent)
}
