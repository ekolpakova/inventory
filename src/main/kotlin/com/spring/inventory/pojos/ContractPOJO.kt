package com.spring.inventory.pojos

import com.spring.inventory.entities.InventoryItem

data class ContractPOJO(val id: Int, val name: Int, val inventoryItems: List<InventoryItem> ? = null)
