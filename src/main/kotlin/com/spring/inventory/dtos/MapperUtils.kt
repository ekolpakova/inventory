package com.spring.inventory.dtos

import com.spring.inventory.entities.Contract
import com.spring.inventory.entities.InventoryItem
import com.spring.inventory.entities.SourceOfFunds
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class MapperUtils {
    @Autowired
    lateinit var modelMapper: ModelMapper

    fun convertToItemDTO(item: InventoryItem): InventoryItemDTO {
        val itemDTO = modelMapper.map(item, InventoryItemDTO::class.java)
        itemDTO.contractDTO = convertToContractDTO(item.contract!!)
        return itemDTO
    }

    fun convertToContractDTO(contract: Contract): ContractDTO  {
        return modelMapper.map(contract, ContractDTO::class.java)
    }

    fun convertToSourceDTO(source: SourceOfFunds): SourceOfFundsDTO  {
        return modelMapper.map(source, SourceOfFundsDTO::class.java)
    }
}
