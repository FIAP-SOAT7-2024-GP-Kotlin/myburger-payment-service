package io.github.soat7.myburguercontrol.adapters.controller

import io.github.soat7.myburguercontrol.adapters.mapper.toDomain
import io.github.soat7.myburguercontrol.adapters.mapper.toResponse
import io.github.soat7.myburguercontrol.domain.usecase.PaymentUseCase
import io.github.soat7.myburguercontrol.external.webservice.payment.api.PaymentCreationRequest

class PaymentHandler(
    private val paymentUseCase: PaymentUseCase,
) {
    fun createPayment(request: PaymentCreationRequest) =
        paymentUseCase.create(request.toDomain()).toResponse()

//    fun startPaymentRequest(orderIdentification: UUID): QRCodeData {
//        return paymentUseCase.startPaymentRequest(orderIdentification)
//    }

//    fun getPaymentStatus(orderIdentification: UUID): PaymentResponse {
//        return paymentUseCase.getPaymentStatus(orderIdentification).toResponse()
//    }
}
