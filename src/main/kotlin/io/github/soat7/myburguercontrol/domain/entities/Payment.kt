package io.github.soat7.myburguercontrol.domain.entities

import io.github.soat7.myburguercontrol.domain.entities.enum.PaymentStatus
import io.github.soat7.myburguercontrol.external.webservice.payment.api.OrderItem
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

data class Payment(
    val id: UUID = UUID.randomUUID(),
    val status: PaymentStatus = PaymentStatus.REQUESTED,
    val authorizationId: String? = null,
    val orderId: UUID,
    val orderPrice: BigDecimal,
    val orderItems: List<OrderItem>,
    val qrcode: String? = null,
    val createdAt: Instant = Instant.now(),
    val updatedAt: Instant = Instant.now(),
)
