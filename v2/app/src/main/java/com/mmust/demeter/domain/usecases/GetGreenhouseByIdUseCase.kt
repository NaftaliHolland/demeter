package com.mmust.demeter.domain.usecases

import com.mmust.demeter.domain.model.Greenhouse
import com.mmust.demeter.domain.repository.GreenhouseRepository

class GetGreenhouseByIdUseCase(private val greenhouseRepository: GreenhouseRepository) {
    operator suspend fun invoke(id: String): Greenhouse {
        return greenhouseRepository.getGreenhouseById(id)?: throw NoSuchElementException("Greenhouse not found")
    }
}