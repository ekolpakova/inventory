package com.spring.inventory.controllers

import com.spring.inventory.dtos.Category
import com.spring.inventory.entities.Contract
import com.spring.inventory.entities.InventoryItem
import com.spring.inventory.repositories.InventoryItemRepository
import com.spring.inventory.services.ContractService
import com.spring.inventory.services.InventoryItemService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.persistence.EntityManager
import javax.persistence.criteria.*
import javax.validation.Valid


@RestController
@Transactional
@RequestMapping("/api/v1/moderator")
public class InventoryItemController(val inventoryItemService: InventoryItemService, val contractService: ContractService, val inventoryItemRepository: InventoryItemRepository) {
    @Autowired
    lateinit var em: EntityManager

    //lateinit var inventoryItemService: InventoryItemService

    @GetMapping("/inventory")
    fun findAllInventoryItems(): List<InventoryItem> {
        return inventoryItemService.getInventoryItems()
    }

    @GetMapping("/inventoryItemById/{id}")
    fun findInventoryItemById(@PathVariable id: Int): InventoryItem {
        return inventoryItemService.getInventoryItemById(id)
    }

    @PostMapping("/addInventoryItem")
    fun addInventoryItem(@RequestBody inventoryItem: InventoryItem): InventoryItem {
        return inventoryItemService.saveInventoryItem(inventoryItem)
    }

    @PutMapping("/updateInventoryItemCell")
    fun updateInventoryItemCell(@RequestParam id: Int, @RequestParam col: String, @RequestBody colVal: String): Int {
        val builder = em.criteriaBuilder

        val update: CriteriaUpdate<InventoryItem> = builder.createCriteriaUpdate(InventoryItem::class.java)

        val inventoryItem: Root<InventoryItem> = update.from(InventoryItem::class.java)

        val getCol: Path<String> = inventoryItem.get(col);
        val idx: Path<String> = inventoryItem.get("id");

        update.set(getCol, colVal)

        update.where(builder.equal(idx, id))
        //update.where(builder.equal(getCol, prev))

        return em.createQuery(update).executeUpdate()
    }


    @PutMapping(value = ["/inventory/{itemId}"], consumes = ["application/json"], produces = ["application/json"])
    fun updateItem(@PathVariable itemId: Int, @RequestBody contract: Contract): InventoryItem {
        val item: InventoryItem = inventoryItemService.getInventoryItemById(itemId)
        val con = contractService.getContractById(contract.id!!)
        item.contract = con
        inventoryItemService.saveInventoryItem(item)
        return item
    }

    @GetMapping("/searchItem")
    fun search(@RequestParam param: String, @RequestParam value: String): MutableList<InventoryItem>? {
        /*val builder = em.criteriaBuilder

        val query = builder.createQuery(InventoryItem::class.java)
        val root: Root<InventoryItem> = query.from(InventoryItem::class.java)

        val getCol: Path<String> = root.get(param)
        query.where(builder.like(getCol, "%$value%"))

        return em.createQuery(query).resultList*/
        val builder = em.criteriaBuilder
        val cr: CriteriaQuery<InventoryItem> = builder.createQuery(InventoryItem::class.java)
        val root: Root<InventoryItem> = cr.from(InventoryItem::class.java)
        cr.select(root)

        cr.select(root).where(builder.like(root.get(param), value))

        val query = em.createQuery(cr)
        return query.resultList
    }



    /*@PutMapping("/updateInventoryItem")
    fun updateInventoryItem(@RequestBody inventoryItem: InventoryItem): InventoryItem {
        return inventoryItemService.updateInventoryItem(inventoryItem)
    }*/

    @PostMapping("/searchByCategory")
    fun searchByCategory(@RequestBody category: Category): MutableList<InventoryItem>? {
        val builder = em.criteriaBuilder
        val cr: CriteriaQuery<InventoryItem> = builder.createQuery(InventoryItem::class.java)
        val root: Root<InventoryItem> = cr.from(InventoryItem::class.java)
        cr.select(root)

        cr.select(root).where(builder.like(root.get(category.param), category.value))

        val query = em.createQuery(cr)
        return query.resultList
    }

    @DeleteMapping("/deleteInventoryItem")
    fun deleteInventoryItem(@RequestParam id: Int): Int {
      inventoryItemService.deleteInventoryItem(id)
      return 1
    }

}
