package io.github.soat7.myburguercontrol.external.webservice.payment.api

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import jakarta.validation.constraints.NotBlank
import java.math.BigDecimal
import java.util.UUID

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class PaymentCreationRequest(
    @NotBlank
    val orderId: UUID,

    @NotBlank
    val orderPrice: BigDecimal,

    @NotBlank
    val orderItems: List<OrderItem>,
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class OrderItem(
    val title: String,
    val description: String,
    val unitPrice: BigDecimal,
    val quantity: Int,
    val unitMeasure: String,
    val totalAmount: BigDecimal,
)
