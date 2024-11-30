package io.github.soat7.myburguercontrol.external.webservice.payment.api

import java.math.BigDecimal
import java.util.UUID

class PaymentResponse(
    val id: String,
    val status: String,
    val authorizationId: String? = null,
    val orderId: UUID,
    val orderPrice: BigDecimal,
    val qrcode: String? = null,
)
