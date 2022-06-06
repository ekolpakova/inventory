package com.spring.inventory.controllers

import com.spring.inventory.dtos.InventoryItemDTO
import com.spring.inventory.dtos.MapperUtils
import com.spring.inventory.dtos.SourceOfFundsDTO
import com.spring.inventory.entities.Contract
import com.spring.inventory.entities.InventoryItem
import com.spring.inventory.entities.SourceOfFunds
import com.spring.inventory.services.InventoryItemService
import com.spring.inventory.services.SourcesOfFundsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@Transactional
@RequestMapping("/api/v1/moderator")
class SourcesOfFundsController(val mapperUtils: MapperUtils) {
    @Autowired
    lateinit var sourcesOfFundsService: SourcesOfFundsService

    @Autowired
    lateinit var inventoryItemService: InventoryItemService

    @GetMapping("/sourcesOfFunds")
    fun findAllSourcesOfFunds(): MutableList<SourceOfFunds> {
        return sourcesOfFundsService.getSourcesOfFunds()
    }

    /*@PutMapping("/sourceOfFunds")
    fun getSourceOfFunds(@RequestParam itemId: Int, @RequestParam id: Int): InventoryItemDTO {
        val item: InventoryItem = inventoryItemService.getInventoryItemById(itemId)
        val src = sourcesOfFundsService.getSourceOfFundsById(id)

        item.sourceOfFunds = src

        val i = mapperUtils.convertToItemDTO(item)
        val s = mapperUtils.convertToSourceDTO(src)

        i.sourceOfFundsDTO = s

        return i
    }*/

    @Transactional
    @PutMapping(value = ["sourcesOfFunds/inventoryDTO/{itemId}"], consumes = ["application/json"], produces = ["application/json"])
    fun updateItemDTO(@PathVariable itemId: Int, @RequestBody sourceOfFunds: SourceOfFunds): InventoryItem? {
        val item: InventoryItem = this.inventoryItemService.getInventoryItemById(itemId)
        mapperUtils.convertToItemDTO(item)
        val src: SourceOfFunds = this.sourcesOfFundsService.getSourceOfFundsById(sourceOfFunds.id!!)
        mapperUtils.convertToSourceDTO(src)
        item.sourceOfFunds = src
        this.inventoryItemService.saveInventoryItem(item)

        src.inventoryItems?.clear()
        src.inventoryItems?.add(item)

        this.sourcesOfFundsService.saveSourceOfFunds(src)

        return item
    }
}
