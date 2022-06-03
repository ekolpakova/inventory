package com.spring.inventory.specs

import com.spring.inventory.entities.InventoryItem
import org.springframework.data.jpa.domain.Specification
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

class InventoryItemSpecification(val col: String, val colVal: String) {
    fun toPredicate(
        root: Root<InventoryItem>,
        query: CriteriaQuery<*>,
        builder: CriteriaBuilder
    ): Predicate? {
        val update = builder.createCriteriaUpdate(InventoryItem::class.java)
        val inventoryItem = update.from(InventoryItem::class.java)
        update.set(inventoryItem.get(col), colVal)
        return null
    }


}
