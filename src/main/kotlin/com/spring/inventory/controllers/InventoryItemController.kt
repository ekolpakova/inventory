package com.spring.inventory.controllers

import com.spring.inventory.dtos.Category
import com.spring.inventory.dtos.ContractDTO
import com.spring.inventory.dtos.InventoryItemDTO
import com.spring.inventory.dtos.SourceOfFundsDTO
import com.spring.inventory.entities.*
import com.spring.inventory.pojos.ContractConverter
import com.spring.inventory.services.ContractService
import com.spring.inventory.services.FixService
import com.spring.inventory.services.InventoryItemService
import com.spring.inventory.services.UserService
import org.mapstruct.factory.Mappers
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import javax.persistence.EntityManager
import javax.persistence.criteria.*


@RestController
@Transactional
@RequestMapping("/api/v1/moderator")
public class InventoryItemController(val inventoryItemService: InventoryItemService, val contractService: ContractService, val fixService: FixService, val userService: UserService) {
    @Autowired
    lateinit var em: EntityManager

    @Autowired
    lateinit var modelMapper: ModelMapper

    @Transactional
    @PutMapping(value = ["/inventoryDTO/{itemId}"], consumes = ["application/json"], produces = ["application/json"])
    fun updateItemDTO(@PathVariable itemId: Int, @RequestBody contract: Contract): InventoryItem? {
        val item: InventoryItem = this.inventoryItemService.getInventoryItemById(itemId)
        convertToItemDTO(item)
        val con: Contract = contractService.getContractById(contract.id!!)
        convertToContractDTO(con)
        item.contract = con
        this.inventoryItemService.saveInventoryItem(item)
        con.inventoryItems?.clear()
        con.inventoryItems?.add(item)
        //val i = con.inventoryItems?.get(itemId)
        /*val i = con.inventoryItems?.find { it.id == itemId }*/
        //i?.contract = con
        /*i?.contract = con
        i?.commentary = "Test II"*/
        //con.inventoryItems?.clear()
        //con.inventoryItems?.add(item)
        this.contractService.saveContract(con)
        //this.inventoryItemService.saveInventoryItem(i!!)
        return item
    }

    @Transactional(readOnly = true)
    @GetMapping("/getContract123")
    fun getTeamByName(id: Int): Contract? {
        val entity: InventoryItem = inventoryItemService.getInventoryItemById(id)
        modelMapper.map(entity, InventoryItemDTO::class.java)
        return entity.contract
    }

    //this works
    @PutMapping("/contract")
    fun getContract(@RequestParam itemId: Int, @RequestParam id: Int): InventoryItemDTO {
        val item: InventoryItem = inventoryItemService.getInventoryItemById(itemId)
        val con = contractService.getContractById(id)

        item.name = "Test III"
        item.contract = con

        val i = convertToItemDTO(item)
        val c = convertToContractDTO(con)

        i.contractDTO = c
        //val z = c.inventoryItems?.find { it.id == itemId }
        //z?.contractDTO = c

        //inventoryItemService.saveInventoryItem(item)
        return i
    }

    @PutMapping("/sourceOfFunds")
    fun getSourceOfFunds(@RequestParam itemId: Int, @RequestParam id: Int) {
        var item: InventoryItem = inventoryItemService.getInventoryItemById(itemId)
        var con = contractService.getContractById(id)

        item.name = "Test III"
        item.contract = con

        val i = convertToItemDTO(item)
        val c = convertToContractDTO(con)

        i.contractDTO = c
        //val z = c.inventoryItems?.find { it.id == itemId }
        //z?.contractDTO = c

        //inventoryItemService.saveInventoryItem(i)
        //return i
    }

    @GetMapping("c")
    fun c() {
        val conv = Mappers.getMapper(ContractConverter::class.java)
        val contract = Contract("Doc1")
        val contractDto = conv.convertToDTO(contract)
        println(contractDto)
    }

    /*fun Contract.toContractDTO() = ContractDTO(
        id = "$id".toInt(),
        name = "$name"
    );*/

    fun convertToItemDTO(item: InventoryItem): InventoryItemDTO {
        val itemDTO = modelMapper.map(item, InventoryItemDTO::class.java)
        itemDTO.contractDTO = convertToContractDTO(item.contract!!)
        return itemDTO
    }

    fun convertToContractDTO(contract: Contract): ContractDTO  {
        return modelMapper.map(contract, ContractDTO::class.java)
    }

    fun convertToSourceDTO(source: SourceOfFunds): SourceOfFundsDTO  {
        return modelMapper.map(source, SourceOfFundsDTO::class.java)
    }

    @PutMapping("/changeContract")
    fun addRoleToUser(@RequestParam id: Int, @RequestParam conId: Int): Contract? {
        val item = inventoryItemService.getInventoryItemById(id)
        item.contract = null
        val con = contractService.getContractById(conId)
        item.contract = con
        inventoryItemService.saveInventoryItem(item)
        return item.contract
    }

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
    fun updateInventoryItemCell(@RequestParam id: Int, @RequestParam col: String, @RequestParam colVal: String): Int {
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

    @PutMapping(value = ["/inventory/fix/{itemId}"], consumes = ["application/json"], produces = ["application/json"])
    fun updateFixItem(@PathVariable itemId: Int, @RequestBody fix: Fix, @RequestParam userId: Int): Fix? {
        val item: InventoryItem = inventoryItemService.getInventoryItemById(itemId)
        val user: User = userService.getUserById(userId)
        val f: Fix = fixService.saveFix(fix)
        item.fix = f
        item.responsiblePerson = user
        f.inventoryItems?.add(item)
        user.inventoryItems?.add(item)
        inventoryItemService.saveInventoryItem(item)
        fixService.saveFix(f)
        userService.saveUser(user)
        return item.fix
    }

    @GetMapping("/searchItem")
    fun search(@RequestParam param: String, @RequestParam value: String): MutableList<InventoryItem>? {
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
