package com.spring.inventory.controllers

import com.spring.inventory.entities.Contract
import com.spring.inventory.services.ContractService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Transactional
@RequestMapping("/api/v1/moderator")
class ContractsController {
    @Autowired
    lateinit var contractsService: ContractService

    @GetMapping("/contracts")
    fun findAllCategories(): MutableList<Contract> {
        return contractsService.getContracts()
    }
}
