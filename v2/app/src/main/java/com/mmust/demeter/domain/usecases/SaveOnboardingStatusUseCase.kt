package com.mmust.demeter.domain.usecases

import com.mmust.demeter.domain.repository.PreferencesRepository

class SaveOnboardingStatusUseCase(
    private val preferencesRepository: PreferencesRepository
) {
    suspend fun execute(isCompelted: Boolean) {
        preferencesRepository.saveOnboardingStatus(isCompelted)
    }
}