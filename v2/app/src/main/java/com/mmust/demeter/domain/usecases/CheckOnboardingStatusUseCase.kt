package com.mmust.demeter.domain.usecases

import com.mmust.demeter.domain.repository.PreferencesRepository

class CheckOnboardingStatusUseCase(
    private val preferencesRepository: PreferencesRepository
) {
    suspend fun execute(): Boolean {
        return preferencesRepository.getOnboardingStatus()
    }
}