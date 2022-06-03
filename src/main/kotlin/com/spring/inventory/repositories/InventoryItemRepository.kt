package com.spring.inventory.repositories

import com.spring.inventory.entities.InventoryItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.GetMapping
import java.util.*
import javax.persistence.EntityManager

@Repository
interface InventoryItemRepository: JpaRepository<InventoryItem, Int>{
    fun findByName(name: String): InventoryItem


    /*@Query(value = "SELECT contracts.name AS Документ, sources_of_funds.name AS ИФО, classrooms.number AS Закуплено, placements.number_in_classroom AS Номер,\n" +
        "placements.placed_in AS Распределено\n" +
        "FROM inventory_items\n" +
        "INNER JOIN contracts ON inventory_items.contract = contracts.id\n" +
        "INNER JOIN sources_of_funds ON inventory_items.source_of_funds = sources_of_funds.id\n" +
        "INNER JOIN placements ON inventory_items.id = placements.inventory_item_id\n" +
        "INNER JOIN classrooms ON placements.classroom = classrooms.id", nativeQuery = true)
    fun findAllInventoryJoined(): List<InventoryItem>
    //fun updateInventoryItem(inventoryItem: InventoryItem): InventoryItem*/
}
