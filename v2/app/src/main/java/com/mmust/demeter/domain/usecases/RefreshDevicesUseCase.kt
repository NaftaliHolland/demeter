package com.mmust.demeter.domain.usecases

import com.mmust.demeter.domain.repository.GreenhouseRepository

class RefreshDevicesUseCase(private val greenhouseRepository: GreenhouseRepository) {
    suspend operator fun invoke(greenhouseId: String): Result<Unit> {
        return greenhouseRepository.refreshDevices(greenhouseId)
    }
}