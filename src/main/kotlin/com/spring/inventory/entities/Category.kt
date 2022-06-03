package com.spring.inventory.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.Data
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.validation.annotation.Validated
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

@Entity
@Data
@Validated
@Table(name = "categories")
class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @NotBlank(message = "Логин не должен быть пустым")
    @Pattern(regexp = "([A-Za-z0-9])\\w+", message = "Логин должен содержать только цифры и буквы английского алфавита")
    @Column(name = "name")
    var name: String? = null

    @JsonIgnore
    @OneToMany
    var inventoryItems: MutableList<InventoryItem> ? = null
}
