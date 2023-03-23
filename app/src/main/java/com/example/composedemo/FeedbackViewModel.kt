package com.example.composedemo

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.*

class FeedbackViewModel : ViewModel() {
    private val _privacyPolicyDisplayState = MutableStateFlow(false)
    val privacyPolicyDisplayState = _privacyPolicyDisplayState.asStateFlow()
    private val _termsOfServiceDisplayState = MutableStateFlow(false)
    val termsOfServiceDisplayState = _termsOfServiceDisplayState.asStateFlow()
    val isWebOpened = combine(privacyPolicyDisplayState, termsOfServiceDisplayState) { a, b ->
        Log.d("zxc", "(a, b) = ($a, $b)")
        a || b
    }.distinctUntilChanged()
    private val _imageListState = MutableStateFlow<List<Uri>>(listOf())
    val imageListState = _imageListState.asStateFlow()
    var inputMsg = ""

    fun openPrivacyPolicy() {
        _privacyPolicyDisplayState.value = true
    }

    fun closePrivacyPolicy() {
        _privacyPolicyDisplayState.value = false
    }

    fun openTermsOfService() {
        _termsOfServiceDisplayState.value = true
    }

    fun closeTermsOfService() {
        _termsOfServiceDisplayState.value = false
    }

    fun closeAll() {
        if (_privacyPolicyDisplayState.value) _privacyPolicyDisplayState.value = false
        if (_termsOfServiceDisplayState.value) _termsOfServiceDisplayState.value = false
    }

    fun addImageItem(uri: Uri) {
        _imageListState.update { it + listOf(uri) }
    }

    fun removeImageItem(index: Int) {
        _imageListState.update {
            val list = _imageListState.value.toMutableList()
            list.removeAt(index)
            list
        }
    }

    fun updateInput(input: String) {
        inputMsg = input
    }
}