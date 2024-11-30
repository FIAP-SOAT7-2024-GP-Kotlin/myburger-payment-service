package io.github.soat7.myburguercontrol.external.thirdparty.api

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import io.github.soat7.myburguercontrol.external.webservice.payment.api.OrderItem
import java.math.BigDecimal

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class PaymentIntegrationRequest(
    val description: String,
    val externalReference: String,
    val items: List<OrderItem>,
    val notificationUrl: String,
    val totalAmount: BigDecimal,
    val title: String = "Order",
)
