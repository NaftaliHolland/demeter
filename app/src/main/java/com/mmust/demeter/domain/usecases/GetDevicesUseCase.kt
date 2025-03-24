package com.mmust.demeter.domain.usecases

import com.mmust.demeter.domain.model.Device
import com.mmust.demeter.domain.repository.GreenhouseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetDevicesUseCase(private val greenhouseRepository: GreenhouseRepository){
    operator fun invoke(greenhouseId: String): Flow<List<Device>> {
        return greenhouseRepository.getDevices(greenhouseId)
            .map { devices -> devices.sortedBy { it.id } }
    }
}