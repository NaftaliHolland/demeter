package com.mmust.demeter.domain.repository

interface PreferencesRepository {
    suspend fun saveOnboardingStatus(isComplete: Boolean)
    suspend fun getOnboardingStatus(): Boolean
}