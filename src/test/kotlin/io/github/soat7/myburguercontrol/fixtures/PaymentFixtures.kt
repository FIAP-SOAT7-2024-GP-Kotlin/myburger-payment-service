package io.github.soat7.myburguercontrol.fixtures

import io.github.soat7.myburguercontrol.domain.entities.Payment
import io.github.soat7.myburguercontrol.domain.entities.enum.PaymentStatus
import io.github.soat7.myburguercontrol.external.thirdparty.api.QRCodeData
import io.github.soat7.myburguercontrol.external.webservice.payment.api.OrderItem
import io.github.soat7.myburguercontrol.external.webservice.payment.api.PaymentCreationRequest
import io.github.soat7.myburguercontrol.util.toBigDecimal
import java.util.UUID

object PaymentFixtures {
    fun mockPaymentRequest(orderId: UUID): PaymentCreationRequest {
        return PaymentCreationRequest(
            orderId = orderId,
            orderPrice = 10.0.toBigDecimal(),
            orderItems = listOf(
                OrderItem(
                    title = "item1",
                    description = "descricao1",
                    unitPrice = 10.0.toBigDecimal(),
                    quantity = 1,
                    unitMeasure = "Unit",
                    totalAmount = 10.0.toBigDecimal(),
                ),
            ),
        )
    }

    fun mockPayment(orderId: UUID): Payment {
        return Payment(
            id = UUID.randomUUID(),
            status = PaymentStatus.REQUESTED,
            orderId = orderId,
            orderPrice = 10.0.toBigDecimal(),
            authorizationId = null,
            orderItems = listOf(
                OrderItem(
                    title = "item1",
                    description = "descricao1",
                    unitPrice = 10.0.toBigDecimal(),
                    quantity = 1,
                    unitMeasure = "Unit",
                    totalAmount = 10.0.toBigDecimal(),
                ),
            ),
        )
    }

    fun mockQRCode(orderId: String) = QRCodeData("QRCodeData", orderId)

    fun mockPaymentUpdateById(paymentId: UUID, status: PaymentStatus): Payment {
        return Payment(
            id = paymentId,
            status = status,
            orderId = UUID.randomUUID(),
            orderPrice = 10.0.toBigDecimal(),
            authorizationId = null,
            orderItems = listOf(
                OrderItem(
                    title = "item1",
                    description = "descricao1",
                    unitPrice = 10.0.toBigDecimal(),
                    quantity = 1,
                    unitMeasure = "Unit",
                    totalAmount = 10.0.toBigDecimal(),
                ),
            ),
        )
    }
}
