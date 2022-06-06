package com.spring.inventory.dtos

import com.spring.inventory.entities.InventoryItem
import lombok.Data
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

@Data
@NoArgsConstructor
@Getter @Setter
class ContractDTO {
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
