package com.spring.inventory.services

import com.spring.inventory.entities.Contract
import com.spring.inventory.entities.InventoryItem
import com.spring.inventory.repositories.ContractRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ContractService {
    @Autowired
    lateinit var contractsRepository: ContractRepository

    fun getContracts(): MutableList<Contract> {
        return contractsRepository.findAll()
    }

    fun getContractById(id: Int): Contract {
        return contractsRepository.findById(id).orElse(null)
    }
}
