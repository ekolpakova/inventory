package com.spring.inventory.entities

import lombok.Data
import java.sql.Date
import javax.persistence.*

@Entity
@Data
@Table(name = "inventory_items")
class InventoryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(name = "serial_number")
    var serialNumber: Int? = null

    @Column(name = "date_taken")
    var dateTaken: Date? = null

    @Column(name = "lab_number")
    var labNumber: String? = null

    @Column(name = "name")
    var name: String? = null

    @Column(name = "specs")
    var specs: String? = null

    @Column(name = "belongsTo")
    var belongsTo: String? = null

    @Column(name = "given")
    var given: String? = null

    @Column(name = "contract")
    var contract: String? = null

    @Column(name = "responsible_person")
    var responsiblePerson: String? = null

    @Column(name = "commentary")
    var commentary: String? = null

    @Column(name = "boughtByRequest")
    var boughtByRequest: String? = null

    @Column(name = "commentary_chernega")
    var commentaryChernega: String? = null

    @Column(name = "ifo")
    var ifo: String? = null
}
