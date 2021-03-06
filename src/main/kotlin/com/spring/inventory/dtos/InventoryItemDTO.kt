package com.spring.inventory.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import lombok.*

@Data
@NoArgsConstructor
@Getter @Setter
class InventoryItemDTO {
    @Getter
    @Setter
    val id: Int ? = null
    @Getter
    @Setter
    val name: String ? = null
    @JsonProperty("contract")
    @Getter
    @Setter
    var contractDTO: ContractDTO ? = null
    @JsonProperty("sourceOfFunds")
    @Getter
    @Setter
    var sourceOfFundsDTO: SourceOfFundsDTO ? = null
}
