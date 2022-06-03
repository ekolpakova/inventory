package com.spring.inventory.entities

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import lombok.Data
import javax.persistence.*

@Entity
@Data
@Table(name = "contracts")
class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(name = "name")
    var name: String? = null

    @JsonManagedReference
    @OneToMany
    var inventoryItems: MutableList<InventoryItem> ? = null
}
