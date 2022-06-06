package com.spring.inventory.services

import com.spring.inventory.entities.InventoryItem
import com.spring.inventory.repositories.InventoryItemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class InventoryItemService {
    @Autowired
    lateinit var inventoryItemRepository: InventoryItemRepository

    fun saveInventoryItem(inventoryItem: InventoryItem): InventoryItem {
        return inventoryItemRepository.saveAndFlush(inventoryItem)
    }

    fun getInventoryItems(): List<InventoryItem> {
        return inventoryItemRepository.findAll()
    }

    fun getInventoryItemById(id: Int): InventoryItem {
        return inventoryItemRepository.findById(id).orElse(null)
    }

    fun deleteInventoryItem(id: Int) {
        return inventoryItemRepository.deleteById(id)
    }
}
