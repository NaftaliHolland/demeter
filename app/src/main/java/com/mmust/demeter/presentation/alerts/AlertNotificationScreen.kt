package com.mmust.demeter.presentation.alerts

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

// Notification data class (remains the same)
data class Notification(
    val id: String,
    val title: String,
    val description: String,
    val timestamp: String,
    val severity: NotificationSeverity,
    val isRead: Boolean = false
)

// Severity enum for notifications
enum class NotificationSeverity {
    CRITICAL, WARNING, INFO
}

// Notifications ViewModel
class NotificationsViewModel : ViewModel() {
    private val _notifications = MutableLiveData<List<Notification>>(emptyList())
    val notifications: LiveData<List<Notification>> = _notifications

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        fetchNotifications()
    }

    fun fetchNotifications() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Simulate fetching notifications (replace with actual data source)
                val fetchedNotifications = listOf(
                    Notification(
                        id = "1",
                        title = "Humidity Alert",
                        description = "Greenhouse 3 humidity levels are critically low",
                        timestamp = "2 hours ago",
                        severity = NotificationSeverity.CRITICAL
                    ),
                    Notification(
                        id = "2",
                        title = "Temperature Warning",
                        description = "Greenhouse 2 temperature exceeded threshold",
                        timestamp = "4 hours ago",
                        severity = NotificationSeverity.WARNING,
                        isRead = true
                    )
                )
                _notifications.value = fetchedNotifications
            } catch (e: Exception) {
                // Handle error (could add error state)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearAllNotifications() {
        viewModelScope.launch {
            _notifications.value = emptyList()
        }
    }

    fun markNotificationAsRead(notificationId: String) {
        val currentNotifications = _notifications.value?.toMutableList() ?: return
        val index = currentNotifications.indexOfFirst { it.id == notificationId }
        if (index != -1) {
            val updatedNotification = currentNotifications[index].copy(isRead = true)
            currentNotifications[index] = updatedNotification
            _notifications.value = currentNotifications
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(viewModel: NotificationsViewModel) {
    val notifications by viewModel.notifications.observeAsState(initial = emptyList())
    val isLoading by viewModel.isLoading.observeAsState(initial = false)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Alerts & Notifications") },
                actions = {
                    IconButton(onClick = { viewModel.clearAllNotifications() }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear All Notifications")
                    }
                }
            )
        }
    ) { innerPadding ->
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                NotificationFilterRow()
                NotificationList(
                    notifications = notifications,
                    onMarkAsRead = { notificationId ->
                        viewModel.markNotificationAsRead(notificationId)
                    }
                )
            }
        }
    }
}

@Composable
fun NotificationFilterRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = "All Greenhouses",
            onValueChange = {},
            label = { Text("Greenhouse") },
            modifier = Modifier.weight(1f),
            readOnly = true
        )

        OutlinedTextField(
            value = "All Severities",
            onValueChange = {},
            label = { Text("Severity") },
            modifier = Modifier.weight(1f),
            readOnly = true
        )

        IconButton(onClick = { /* Date filter logic */ }) {
            Icon(Icons.Default.DateRange, contentDescription = "Filter by Date")
        }
    }
}

@Composable
fun NotificationList(
    notifications: List<Notification>,
    onMarkAsRead: (String) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(notifications) { notification ->
            NotificationCard(
                notification = notification,
                onMarkAsRead = { onMarkAsRead(notification.id) }
            )
        }
    }
}

@Composable
fun NotificationCard(
    notification: Notification,
    onMarkAsRead: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Severity Icon
            Icon(
                imageVector = when (notification.severity) {
                    NotificationSeverity.CRITICAL -> Icons.Default.Error
                    NotificationSeverity.WARNING -> Icons.Default.Warning
                    NotificationSeverity.INFO -> Icons.Default.Info
                },
                contentDescription = "Severity",
                tint = when (notification.severity) {
                    NotificationSeverity.CRITICAL -> Color.Red
                    NotificationSeverity.WARNING -> Color.Yellow
                    NotificationSeverity.INFO -> Color.Blue
                },
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = notification.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = if (!notification.isRead) FontWeight.Bold else FontWeight.Normal
                )
                Text(
                    text = notification.timestamp,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = notification.description,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

// Example of how to use in an Activity or Fragment
/*@Composable
fun NotificationsScreenExample() {
    val viewModel: NotificationsViewModel = viewModel()
    NotificationsScreen(viewModel)
}*/