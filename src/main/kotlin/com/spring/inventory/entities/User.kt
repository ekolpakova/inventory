package com.spring.inventory.entities
import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.Data
import org.hibernate.annotations.Type
import java.sql.Date
import javax.persistence.*
import javax.validation.constraints.Email

@Entity
@Data
@Table(name = "users")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(name = "username")
    var username: String? = null

    @Column(name = "password")
    var password: String? = null

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER) // automatically pull back child entities
    @JoinTable(
        name = "users_roles",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
    )
    var roles: List<Role>? = null

    @Column(name = "birthdate")
    var birthDate: Date? = null

    @Column(name = "first_name")
    var firstName: String? = null

    @Column(name = "surname")
    var surname: String? = null

    @Column(name = "patronym")
    var patronym: String? = null

    @Email
    @Column(name = "email")
    var email: String? = null

    //@Lob
    @Type(type="org.hibernate.type.BinaryType")
    @Column(name = "image")
    var image: ByteArray? = null

    constructor(username: String, password: String, /*birthDate: Date, firstName: String, surname: String, patronym: String, email: String*/) {
        this.username = username
        this.password = password/*
        this.birthDate = birthDate
        this.firstName = firstName
        this.surname = surname
        this.patronym = patronym
        this.email = email*/
    }
}

