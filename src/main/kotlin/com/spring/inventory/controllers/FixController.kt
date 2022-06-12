package com.spring.inventory.controllers

import com.spring.inventory.entities.Contract
import com.spring.inventory.entities.Fix
import com.spring.inventory.entities.InventoryItem
import com.spring.inventory.entities.User
import com.spring.inventory.services.FixService
import com.spring.inventory.services.InventoryItemService
import com.spring.inventory.services.UserService
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import javax.persistence.EntityManager
import javax.persistence.criteria.CriteriaUpdate
import javax.persistence.criteria.Path
import javax.persistence.criteria.Root

@RestController
@Transactional
@RequestMapping("/api/v1/moderator")
class FixController {
    @Autowired
    lateinit var fixService: FixService

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var inventoryItemService: InventoryItemService

    @GetMapping("/fixes")
    fun findFixes(): MutableList<Fix> {
        return fixService.getFixes()
    }

    @GetMapping("/fixById/{id}")
    fun findFixById(@PathVariable id: Int): Fix {
        return fixService.findFixById(id)
    }

    @Autowired
    lateinit var em: EntityManager

    @Autowired
    lateinit var modelMapper: ModelMapper

    @Transactional
    @PutMapping(value = ["/fixDTO/{itemId}"], consumes = ["application/json"], produces = ["application/json"])
    fun updateItemDTO(@PathVariable itemId: Int, @RequestBody fix: Fix): InventoryItem? {
        val item: InventoryItem = this.inventoryItemService.getInventoryItemById(itemId)
        val f: Fix = this.fixService.findFixById(fix.id!!)
        item.fix = f
        this.inventoryItemService.saveInventoryItem(item)
        f.inventoryItems?.clear()
        f.inventoryItems?.add(item)
        this.fixService.saveFix(f)
        return item
    }

    @PutMapping("/updateFixCell")
    fun updateFixCell(@RequestParam id: Int, @RequestParam col: String, @RequestParam colVal: String): Int {
        val builder = em.criteriaBuilder

        val update: CriteriaUpdate<Fix> = builder.createCriteriaUpdate(Fix::class.java)

        val fix: Root<Fix> = update.from(Fix::class.java)

        val getCol: Path<String> = fix.get(col);
        val idx: Path<String> = fix.get("id");

        update.set(getCol, colVal)

        update.where(builder.equal(idx, id))

        return em.createQuery(update).executeUpdate()
    }

    @DeleteMapping("/deleteFix")
    fun deleteFix(@RequestParam id: Int): Int {
        fixService.deleteById(id)
        return 1
    }

    @PostMapping("/addFix")
    fun addFix(@RequestBody f: Fix, @RequestParam userId: Int, @RequestParam itemId: Int): Fix {
        val fix:Fix = f
        val user:User = userService.getUserById(userId)
        val item:InventoryItem = inventoryItemService.getInventoryItemById(itemId)

        fix.responsiblePerson = user
        fix.inventoryItems?.add(item)

        fixService.saveFix(fix)
        return fixService.addFix(fix)
    }

    @PutMapping("/updateFix/{fixId}")
    fun updateFix(@PathVariable fixId: Int, @RequestParam userId: Int, @RequestParam itemId: Int) {
        val fix:Fix = fixService.findFixById(fixId)
        val user:User = userService.getUserById(userId)
        val item:InventoryItem = inventoryItemService.getInventoryItemById(itemId)

        fix.responsiblePerson = user
        fix.inventoryItems?.add(item)

        fixService.saveFix(fix)
    }

}
