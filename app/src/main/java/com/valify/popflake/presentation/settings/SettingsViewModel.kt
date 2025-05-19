package com.valify.popflake.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
) : ViewModel() {

    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

    private val _language = MutableStateFlow("English")
    val language: StateFlow<String> = _language.asStateFlow()

    private val _cacheSize = MutableStateFlow("0 MB")
    val cacheSize: StateFlow<String> = _cacheSize.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _showLanguageDialog = MutableStateFlow(false)
    val showLanguageDialog: StateFlow<Boolean> = _showLanguageDialog.asStateFlow()

    init {
        loadSettings()
        calculateCacheSize()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            _isDarkTheme.value = false
            _language.value = "English"
        }
    }

    private fun calculateCacheSize() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                kotlinx.coroutines.delay(1000)
                val sizeInBytes = 1024 * 1024 * 5 // 5 MB
                _cacheSize.value = formatFileSize(sizeInBytes.toLong())
            } catch (e: Exception) {
                _cacheSize.value = "Unknown"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun toggleTheme() {
        viewModelScope.launch {
            val newTheme = !_isDarkTheme.value
            _isDarkTheme.value = newTheme

            // Save to preferences
            // preferencesRepository.setDarkTheme(newTheme)
        }
    }

    fun showLanguageDialog() {
        _showLanguageDialog.value = true
    }

    fun hideLanguageDialog() {
        _showLanguageDialog.value = false
    }

    fun setLanguage(language: String) {
        viewModelScope.launch {
            _language.value = language
            hideLanguageDialog()

            // Save to preferences
            // preferencesRepository.setLanguage(language)
        }
    }

    fun clearCache() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Clear actual cache
                // This would involve:
                // 1. Clearing image cache
                // 2. Clearing database cache
                // 3. Clearing network cache
                // 4. Any other cached data

                // Simulate clearing delay
                kotlinx.coroutines.delay(2000)

                // Mock clearing - replace with actual implementation
                _cacheSize.value = "0 MB"

                // _snackbarMessage.value = "Cache cleared successfully"

            } catch (e: Exception) {
                // Handle error
                // _snackbarMessage.value = "Failed to clear cache: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun openPrivacyPolicy() {
        // Open privacy policy URL
    }

    fun openTermsOfService() {
        // Open terms of service URL
    }

    fun openHelpCenter() {
        // Open help center
    }

    fun sendFeedback() {
        // Open feedback mechanism
    }

    private fun formatFileSize(sizeInBytes: Long): String {
        val kilobyte = 1024L
        val megabyte = kilobyte * 1024L
        val gigabyte = megabyte * 1024L

        return when {
            sizeInBytes >= gigabyte -> {
                String.format("%.1f GB", sizeInBytes.toDouble() / gigabyte)
            }
            sizeInBytes >= megabyte -> {
                String.format("%.1f MB", sizeInBytes.toDouble() / megabyte)
            }
            sizeInBytes >= kilobyte -> {
                String.format("%.1f KB", sizeInBytes.toDouble() / kilobyte)
            }
            else -> {
                "$sizeInBytes B"
            }
        }
    }

    // Language options for the dialog
    val availableLanguages = listOf(
        "English",
        "Spanish",
        "French",
        "German",
        "Italian",
        "Portuguese",
        "Chinese",
        "Japanese",
        "Korean",
        "Arabic"
    )
}