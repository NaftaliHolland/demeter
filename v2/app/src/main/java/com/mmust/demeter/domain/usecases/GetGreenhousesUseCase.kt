package com.mmust.demeter.domain.usecases

import com.mmust.demeter.domain.model.Greenhouse
import com.mmust.demeter.domain.repository.GreenhouseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetGreenhousesUseCase(private val greenhouseRepository: GreenhouseRepository) {
    operator fun invoke(userId: String): Flow<List<Greenhouse>> {
        return greenhouseRepository.getGreenhouses(userId)
            .map { greenhouses -> greenhouses.sortedBy { it.name } }
    }
}