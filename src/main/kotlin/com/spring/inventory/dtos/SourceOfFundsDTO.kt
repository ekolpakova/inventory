package com.spring.inventory.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import lombok.Data
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

@Data
@NoArgsConstructor
@Getter
@Setter
class SourceOfFundsDTO {
    @Getter
    @Setter
    val id: Int ? = null
    @Getter
    @Setter
    val name: String ? = null
    @Getter
    @Setter
    val inventoryItems: List<InventoryItemDTO> ? = null
}
