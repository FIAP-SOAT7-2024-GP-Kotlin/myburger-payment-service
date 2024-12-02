package io.github.soat7.myburguercontrol.domain.usecase

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.soat7.myburguercontrol.adapters.gateway.PaymentIntegrationRepository
import io.github.soat7.myburguercontrol.domain.entities.Payment
import io.github.soat7.myburguercontrol.domain.entities.enum.PaymentStatus
import io.github.soat7.myburguercontrol.exception.ReasonCode
import io.github.soat7.myburguercontrol.exception.ReasonCodeException
import io.github.soat7.myburguercontrol.external.db.payment.PaymentGateway
import java.time.Instant
import java.util.UUID

private val logger = KotlinLogging.logger {}

class PaymentUseCase(
    private val paymentIntegrationRepository: PaymentIntegrationRepository,
    private val paymentGateway: PaymentGateway,
) {

    fun createPayment(payment: Payment): Payment {
        logger.info { "Creating payment" }

        val qrcode = paymentIntegrationRepository.requestQRCodeDataForPayment(payment)
        val newPayment = payment.copy(
            qrcode = qrcode.qrData,
            status = PaymentStatus.REQUESTED,
            createdAt = Instant.now(),
            updatedAt = Instant.now(),
        )
        return paymentGateway.create(newPayment)
    }

    fun updatePayment(paymentId: String, paymentStatus: String): Payment {
        logger.info { "Update payment: $paymentId status: $paymentStatus" }

        val payment = getPaymentByOrderId(UUID.fromString(paymentId))

        val updatedPayment = payment.copy(
            status = PaymentStatus.fromString(paymentStatus),
            updatedAt = Instant.now(),
        )
        paymentGateway.update(updatedPayment)

        logger.info { "Successfully update with status return: [${updatedPayment.status.name}]" }

        if (updatedPayment.status == PaymentStatus.DENIED) throw ReasonCodeException(ReasonCode.PAYMENT_INTEGRATION_ERROR)

        return updatedPayment
    }

    fun getPayment(paymentId: UUID): Payment {
        logger.info { "Get payment" }
        return paymentGateway.findById(paymentId) ?: throw ReasonCodeException(ReasonCode.PAYMENT_NOT_FOUND)
    }

    fun getPaymentByOrderId(orderId: UUID): Payment {
        logger.info { "Get payment by orderId" }
        return paymentGateway.findByOrderId(orderId) ?: throw ReasonCodeException(ReasonCode.PAYMENT_NOT_FOUND)
    }
}
