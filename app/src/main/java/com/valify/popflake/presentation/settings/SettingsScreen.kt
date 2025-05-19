package com.valify.popflake.presentation.settings

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(viewModel: SettingsViewModel) {
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()
    val language by viewModel.language.collectAsState()
    val cacheSize by viewModel.cacheSize.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val showLanguageDialog by viewModel.showLanguageDialog.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                item {
                    SectionHeader(title = "Appearance")

                    // Theme toggle
                    SettingsSwitch(
                        title = "Dark Theme",
                        description = "Toggle between light and dark theme",
                        icon = Icons.Default.Settings,
                        checked = isDarkTheme,
                        onCheckedChange = { viewModel.toggleTheme() }
                    )

                    Divider(modifier = Modifier.padding(vertical = 8.dp))

                    // Language selection
                    SettingsItem(
                        title = "Language",
                        description = language,
                        icon = Icons.Default.Info,
                        onClick = { viewModel.showLanguageDialog() }
                    )

                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                }

                item {
                    SectionHeader(title = "Storage")

                    // Cache information
                    SettingsButton(
                        title = "Clear Cache",
                        description = "Current cache size: $cacheSize",
                        icon = Icons.Default.Clear,
                        onClick = { viewModel.clearCache() },
                        loading = isLoading
                    )

                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                }

                item {
                    SectionHeader(title = "Support")

                    // Help center
                    SettingsItem(
                        title = "Help Center",
                        description = "Get help with using Pop-flake",
                        icon = Icons.Default.Info,
                        onClick = { viewModel.openHelpCenter() }
                    )

                    Divider(modifier = Modifier.padding(vertical = 8.dp))

                    // Send feedback
                    SettingsItem(
                        title = "Send Feedback",
                        description = "Help us improve Pop-flake",
                        icon = Icons.Default.MoreVert,
                        onClick = { viewModel.sendFeedback() }
                    )

                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                }

                item {
                    SectionHeader(title = "Legal")

                    // Privacy policy
                    SettingsItem(
                        title = "Privacy Policy",
                        description = "Read our privacy policy",
                        icon = Icons.Default.Info,
                        onClick = { viewModel.openPrivacyPolicy() }
                    )

                    Divider(modifier = Modifier.padding(vertical = 8.dp))

                    // Terms of service
                    SettingsItem(
                        title = "Terms of Service",
                        description = "Read our terms of service",
                        icon = Icons.Default.Info,
                        onClick = { viewModel.openTermsOfService() }
                    )

                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                }

                item {
                    // Version information
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Pop-flake v1.0.0",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Show language selection dialog
            if (showLanguageDialog) {
                LanguageSelectionDialog(
                    selectedLanguage = language,
                    availableLanguages = viewModel.availableLanguages,
                    onLanguageSelected = { viewModel.setLanguage(it) },
                    onDismiss = { viewModel.hideLanguageDialog() }
                )
            }

            // Show loading indicator
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold
        ),
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun SettingsItem(
    title: String,
    description: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(vertical = 12.dp, horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun SettingsSwitch(
    title: String,
    description: String,
    icon: ImageVector,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
fun SettingsButton(
    title: String,
    description: String,
    icon: ImageVector,
    onClick: () -> Unit,
    loading: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable(enabled = !loading) { onClick() }
            .padding(vertical = 12.dp, horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                strokeWidth = 2.dp
            )
        } else {
            FilledTonalButton(
                onClick = onClick,
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(text = "Clear")
            }
        }
    }
}

@Composable
fun LanguageSelectionDialog(
    selectedLanguage: String,
    availableLanguages: List<String>,
    onLanguageSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Select Language",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn {
                    items(availableLanguages) { language ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { onLanguageSelected(language) }
                                .padding(vertical = 12.dp, horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = language == selectedLanguage,
                                onClick = { onLanguageSelected(language) }
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = language,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }
                }
            }
        }
    }
}