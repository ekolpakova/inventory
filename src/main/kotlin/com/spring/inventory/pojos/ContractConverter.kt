package com.spring.inventory.pojos

import com.spring.inventory.dtos.ContractDTO
import com.spring.inventory.entities.Contract
import org.mapstruct.InheritInverseConfiguration
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
interface ContractConverter {
    @Mapping(source = "inventoryItems", target = "inventoryItems")
    fun convertToDTO(contract: Contract): ContractPOJO

    @InheritInverseConfiguration
    fun convertToModel(contractDTO: ContractDTO): Contract
}
