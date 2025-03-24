package com.mmust.demeter.domain.usecases

import com.mmust.demeter.domain.repository.GreenhouseRepository

class RefreshGreenhousesUseCase(private val greenhouseRepository: GreenhouseRepository) {
    suspend operator fun invoke(userId: String): Result<Unit> {
        return greenhouseRepository.refreshGreenhouses(userId)
    }
}