package com.spring.inventory.services

import com.spring.inventory.entities.Contract
import com.spring.inventory.entities.SourceOfFunds
import com.spring.inventory.repositories.SourceOfFundsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SourcesOfFundsService {
    @Autowired
    lateinit var sourceOfFundsRepository: SourceOfFundsRepository

    fun getSourcesOfFunds(): MutableList<SourceOfFunds> {
        return sourceOfFundsRepository.findAll()
    }

    fun saveSourceOfFunds(sourceOfFunds: SourceOfFunds): SourceOfFunds {
        return sourceOfFundsRepository.saveAndFlush(sourceOfFunds)
    }

    fun getSourceOfFundsById(id: Int): SourceOfFunds {
        return sourceOfFundsRepository.findById(id).orElse(null)
    }
}
