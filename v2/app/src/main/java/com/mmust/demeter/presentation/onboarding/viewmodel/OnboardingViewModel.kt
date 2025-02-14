package com.mmust.demeter.presentation.onboarding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmust.demeter.domain.repository.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    private val _hasCompletedOnboarding = MutableStateFlow(false)
    val hasCompletedOnboarding: StateFlow<Boolean> = _hasCompletedOnboarding

    init {
        checkIfUserCompletedOnboarding()
    }
    private fun checkIfUserCompletedOnboarding() {
        viewModelScope.launch {
           _hasCompletedOnboarding.value = preferencesRepository.getOnboardingStatus()
        }
    }

    fun saveOnboardingCompletion() {
        viewModelScope.launch {
            preferencesRepository.saveOnboardingStatus(true)
        }
    }
}