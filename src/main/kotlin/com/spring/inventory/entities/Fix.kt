package com.spring.inventory.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import lombok.Data
import javax.persistence.*

@Entity
@Data
@Table(name = "fixes")
class Fix {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(name = "description")
    var description: String ? = null

    @Column(name = "phone")
    val phone: String ? = null

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "responsible_person_id")
    var responsiblePerson: User ? = null

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "inventory_items_id")
    var inventoryItem: InventoryItem ? = null
}
