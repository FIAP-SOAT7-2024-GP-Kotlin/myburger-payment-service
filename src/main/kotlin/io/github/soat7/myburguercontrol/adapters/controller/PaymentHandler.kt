package io.github.soat7.myburguercontrol.adapters.controller

import io.github.soat7.myburguercontrol.adapters.mapper.toDomain
import io.github.soat7.myburguercontrol.adapters.mapper.toResponse
import io.github.soat7.myburguercontrol.domain.usecase.PaymentUseCase
import io.github.soat7.myburguercontrol.external.webservice.payment.api.PaymentCreationRequest
import java.util.UUID

class PaymentHandler(
    private val paymentUseCase: PaymentUseCase,
) {
    fun createPayment(request: PaymentCreationRequest) =
        paymentUseCase.createPayment(request.toDomain()).toResponse()

    fun getPayment(paymentId: UUID) =
        paymentUseCase.getPayment(paymentId).toResponse()

    fun getPaymentByOrderId(orderId: UUID) =
        paymentUseCase.getPaymentByOrderId(orderId).toResponse()
}
