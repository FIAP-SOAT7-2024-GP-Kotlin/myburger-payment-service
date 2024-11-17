package io.github.soat7.myburguercontrol.adapters.mapper

import io.github.soat7.myburguercontrol.domain.entities.Payment
import io.github.soat7.myburguercontrol.domain.entities.enum.PaymentStatus
import io.github.soat7.myburguercontrol.external.db.payment.entity.PaymentEntity
import io.github.soat7.myburguercontrol.external.thirdparty.api.PaymentIntegrationRequest
import io.github.soat7.myburguercontrol.external.webservice.payment.api.PaymentCreationRequest
import io.github.soat7.myburguercontrol.external.webservice.payment.api.PaymentResponse
import java.util.UUID

fun PaymentCreationRequest.toDomain() = Payment(
    id = UUID.randomUUID(),
    orderId = this.orderId,
    orderPrice = this.orderPrice,
)

fun Payment.toPersistence() = PaymentEntity(
    id = this.id,
    authorizationId = this.authorizationId,
    status = this.status.toString(),
    orderId = this.orderId,
    orderPrice = this.orderPrice,
    qrcode = this.qrcode,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
)

fun PaymentEntity.toDomain() = Payment(
    id = this.id,
    authorizationId = this.authorizationId,
    status = PaymentStatus.valueOf(this.status),
    orderId = this.orderId,
    orderPrice = this.orderPrice,
    qrcode = this.qrcode,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
)

fun Payment.toResponse() = PaymentResponse(
    id = this.id.toString(),
    status = this.status.toString(),
    authorizationId = this.authorizationId,
    orderId = this.orderId,
    orderPrice = this.orderPrice,
    qrcode = this.qrcode,
)

fun Payment.toPaymentRequest(notificationUrl: String) = PaymentIntegrationRequest(
    description = "",
    externalReference = this.id.toString(),
    totalAmount = this.orderPrice,
    notificationUrl = notificationUrl,
)
