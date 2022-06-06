package com.spring.inventory.controllers

import com.spring.inventory.dtos.InventoryItemDTO
import com.spring.inventory.dtos.MapperUtils
import com.spring.inventory.entities.Contract
import com.spring.inventory.entities.InventoryItem
import com.spring.inventory.entities.Role
import com.spring.inventory.services.ContractService
import com.spring.inventory.services.InventoryItemService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@Transactional
@RequestMapping("/api/v1/moderator")
class ContractsController(val mapperUtils: MapperUtils) {
    @Autowired
    lateinit var contractService: ContractService

    @Autowired
    lateinit var inventoryItemService: InventoryItemService

    @GetMapping("/contracts")
    fun findAllCategories(): MutableList<Contract> {
        return contractService.getContracts()
    }

    @PutMapping("/addContractToItem")
    fun addContractToItem(@RequestParam id: Int, @RequestParam conId: Int): Contract? {
        val item = inventoryItemService.getInventoryItemById(id)
        val con = contractService.getContractById(conId)

        if(item.contract !== con) {
            item.contract = con
            inventoryItemService.saveInventoryItem(item)
        }

        return item.contract
    }

    @DeleteMapping("/removeContractFromItem")
    fun removeContractFromItem(@RequestParam id: Int): Contract? {
        val item = inventoryItemService.getInventoryItemById(id)
        item.contract = null
        inventoryItemService.saveInventoryItem(item)
        return item.contract
    }

    /*@PutMapping("/contract")
    fun getSourceOfFunds(@RequestParam itemId: Int, @RequestParam id: Int): InventoryItemDTO {
        val item: InventoryItem = inventoryItemService.getInventoryItemById(itemId)
        val con = contractService.getContractById(id)

        item.contract = con

        val i = mapperUtils.convertToItemDTO(item)
        val c = mapperUtils.convertToContractDTO(con)

        i.contractDTO = c

        return i
    }*/
}
