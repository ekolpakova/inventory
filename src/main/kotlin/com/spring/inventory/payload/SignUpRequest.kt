package com.spring.inventory.payload

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

class SignUpRequest {
    @NotBlank
    @Size(min = 3)
    @Pattern(regexp = "[А-Яа-я0-9]")
    var username: String? = null

    @NotNull
    @Size(min = 3)
    var password: String? = null
}
