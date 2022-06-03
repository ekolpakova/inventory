package com.spring.inventory.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.Data
import java.sql.Date
import javax.persistence.*

@Entity
@Data
@Table(name = "classrooms")
class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(name = "number")
    var name: Int? = null

    @OneToMany
    var inventoryItems: MutableList<InventoryItem> ? = null
}
