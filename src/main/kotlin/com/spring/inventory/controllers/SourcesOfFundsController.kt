package com.spring.inventory.controllers

import com.spring.inventory.entities.SourceOfFunds
import com.spring.inventory.services.SourcesOfFundsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Transactional
@RequestMapping("/api/v1/moderator")
class SourcesOfFundsController {
    @Autowired
    lateinit var sourcesOfFundsService: SourcesOfFundsService

    @GetMapping("/sourcesOfFunds")
    fun findAllSourcesOfFunds(): MutableList<SourceOfFunds> {
        return sourcesOfFundsService.getSourcesOfFunds()
    }
}
