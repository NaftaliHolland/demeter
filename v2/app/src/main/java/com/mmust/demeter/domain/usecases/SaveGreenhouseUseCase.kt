package com.mmust.demeter.domain.usecases

import com.mmust.demeter.domain.model.Greenhouse
import com.mmust.demeter.domain.repository.GreenhouseRepository

class SaveGreenhousesUseCase(private val greenhouseRepository: GreenhouseRepository) {
    suspend fun invoke(greenhouses: List<Greenhouse>): Result<Unit> {
        return greenhouseRepository.saveGreenhouses(greenhouses)
    }
}