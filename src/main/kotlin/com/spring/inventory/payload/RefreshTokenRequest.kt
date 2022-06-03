package com.spring.inventory.payload

import javax.validation.constraints.NotNull

class RefreshTokenRequest {
    @NotNull
    var refresh_token: String? = null
}
