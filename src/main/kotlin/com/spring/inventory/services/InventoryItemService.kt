package com.spring.inventory.services

import com.spring.inventory.entities.InventoryItem
import com.spring.inventory.repositories.InventoryItemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class InventoryItemService {
    @Autowired
    lateinit var inventoryItemRepository: InventoryItemRepository

    fun saveInventoryItem(inventoryItem: InventoryItem): InventoryItem {
        return inventoryItemRepository.save(inventoryItem)
    }

    fun getInventoryItems(): List<InventoryItem> {
        return inventoryItemRepository.findAll()
    }

    fun getInventoryItemById(id: Int): Optional<InventoryItem> {
        return inventoryItemRepository.findById(id)
    }

    /*fun updateInventoryItem(inventoryItem: InventoryItem): InventoryItem {
        return inventoryItemRepository.updateInventoryItem(inventoryItem)
    }*/

    fun deleteInventoryItem(inventoryItem: InventoryItem) {
        return inventoryItemRepository.delete(inventoryItem)
    }
}
