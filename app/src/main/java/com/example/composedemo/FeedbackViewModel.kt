package com.example.composedemo

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged

class FeedbackViewModel : ViewModel() {
    private val _privacyPolicyDisplayState = MutableStateFlow(false)
    val privacyPolicyDisplayState: StateFlow<Boolean> = _privacyPolicyDisplayState
    private val _termsOfServiceDisplayState = MutableStateFlow(false)
    val termsOfServiceDisplayState: StateFlow<Boolean> = _termsOfServiceDisplayState
    val isWebOpened = combine(privacyPolicyDisplayState, termsOfServiceDisplayState) { a, b ->
        Log.d("zxc", "(a, b) = ($a, $b)")
        a || b
    }.distinctUntilChanged()

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
}