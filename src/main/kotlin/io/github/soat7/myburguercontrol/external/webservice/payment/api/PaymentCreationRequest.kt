package io.github.soat7.myburguercontrol.external.webservice.payment.api

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import java.math.BigDecimal
import java.util.UUID

data class PaymentCreationRequest(
    @NotBlank
    @JsonProperty("orderId")
    val orderId: UUID,
    @NotBlank
    @JsonProperty("orderPrice")
    val orderPrice: BigDecimal,
)
