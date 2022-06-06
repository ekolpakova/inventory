package com.spring.inventory.pojos

import com.fasterxml.jackson.annotation.JsonProperty
import com.spring.inventory.dtos.ContractDTO

data class InventoryItemPOJO(val id: Int, val name: String,
@JsonProperty("contract") val contractDTO: ContractDTO? = null)
