package com.spring.inventory.repositories

import com.spring.inventory.entities.InventoryItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface InventoryItemRepository: JpaRepository<InventoryItem, Int>{
    fun findByName(name: String): InventoryItem
    //fun updateInventoryItem(inventoryItem: InventoryItem): InventoryItem
}
