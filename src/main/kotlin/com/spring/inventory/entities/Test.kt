package com.spring.inventory.entities

import lombok.Data
import javax.persistence.*

@Entity
@Data
@Table(name = "tests")
class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Int? = null

    @Column(name = "name")
    var name: String? = null
}
