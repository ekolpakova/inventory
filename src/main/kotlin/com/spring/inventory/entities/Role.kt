package com.spring.inventory.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.Data
import javax.persistence.*

@Entity
@Data
@Table(name = "roles")
class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Int? = null

    @Column(name = "name")
    var name: String? = null

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "roles_permissions", joinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")], inverseJoinColumns = [JoinColumn(name = "permission_id", referencedColumnName = "id")])
    var permissions: List<Permission>? = null
}
