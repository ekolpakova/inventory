package com.spring.inventory.payload

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

class SignUpRequest {
    @NotBlank
    var username: String? = null

    @NotNull
    @Size(min = 3)
    var password: String? = null
}
