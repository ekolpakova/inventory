package com.spring.inventory.entities

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

    @OneToMany(mappedBy = "fix", cascade = [CascadeType.ALL])
    var inventoryItems: MutableList<InventoryItem> ? = null
}
