package com.spring.inventory.entities

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import com.fasterxml.jackson.annotation.JsonProperty
import com.spring.inventory.dtos.ContractDTO
import lombok.Data
import org.springframework.format.annotation.DateTimeFormat
import java.sql.Date
import javax.persistence.*
import javax.validation.constraints.NotBlank


@Entity
@Data
@Table(name = "inventory_items")
class InventoryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(name = "serial_number")
    var serialNumber: String? = null

    //@DateTimeFormat(pattern = "dd.mm.yyyy")
    //@Temporal(TemporalType.DATE)
    @Column(name = "date_taken")
    var dateTaken: Date? = null

    @NotBlank(message = "Названия элемента инвентаря не должно быть пустым")
    //@Pattern(regexp = "([А-Яа-яA-Za-z0-9])\\w+", message = "Название должно содержать только цифры и буквы русского и английского алфавитов")
    @Column(name = "name")
    var name: String? = null

    @Column(name = "specs")
    var specs: String? = null

    @Column(name = "belongs_to")
    var belongsTo: Int? = null

    @Column(name = "given")
    var given: Boolean? = null

    /*@ManyToOne
    @JoinColumn(name = "id", insertable = false, updatable = false)
    val contract: Contract ? = null*/

    @Column(name = "commentary")
    var commentary: String? = null

    @Column(name = "bought_by_request")
    var boughtByRequest: Boolean? = null

    @Column(name = "commentary_chernega")
    var commentaryChernega: String? = null

    @ManyToOne(fetch = FetchType.EAGER)
    var category: Category ? = null

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    var classroom: Classroom ? = null

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contract_id")
    var contract: Contract ? = null

    @ManyToOne(fetch = FetchType.EAGER)
    var sourceOfFunds: SourceOfFunds ? = null

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fix_id")
    var fix: Fix ? = null

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    var responsiblePerson: User ? = null

    @Column(name = "number_in_classroom")
    var numberInClassroom: String? = null

    @JsonIgnore
    @OneToMany(cascade = [CascadeType.ALL])
    var fixes: MutableList<Fix> ? = null

}
