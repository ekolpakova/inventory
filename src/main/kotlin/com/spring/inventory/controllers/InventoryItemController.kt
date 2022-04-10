package com.spring.inventory.controllers

import com.spring.inventory.entities.InventoryItem
import com.spring.inventory.services.InventoryItemService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class InventoryItemController {
    @Autowired
    lateinit var inventoryItemService: InventoryItemService

    @GetMapping("/inventory")
    fun findAllInventoryItems(): List<InventoryItem> {
        return inventoryItemService.getInventoryItems()
    }

    @GetMapping("/inventoryItemById/{id}")
    fun findInventoryItemById(@PathVariable id: Int): Optional<InventoryItem> {
        return inventoryItemService.getInventoryItemById(id)
    }

    @PostMapping("/addInventoryItem")
    fun addInventoryItem(@RequestBody inventoryItem: InventoryItem): InventoryItem {
        return inventoryItemService.saveInventoryItem(inventoryItem)
    }

    /*@PutMapping("/updateInventoryItem")
    fun updateInventoryItem(@RequestBody inventoryItem: InventoryItem): InventoryItem {
        return inventoryItemService.updateInventoryItem(inventoryItem)
    }*/

    /*@DeleteMapping("/deleteInventoryItem/{id}")
    fun deleteInventoryItem(@PathVariable id: Int): InventoryItem {
      //
    }*/

}
