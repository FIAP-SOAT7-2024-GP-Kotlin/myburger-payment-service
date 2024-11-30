package io.github.soat7.myburguercontrol.domain.usecase

import io.github.soat7.myburguercontrol.adapters.gateway.PaymentIntegrationRepository
import io.github.soat7.myburguercontrol.domain.entities.Payment
import io.github.soat7.myburguercontrol.domain.entities.enum.PaymentStatus
import io.github.soat7.myburguercontrol.exception.ReasonCodeException
import io.github.soat7.myburguercontrol.external.db.payment.PaymentGateway
import io.github.soat7.myburguercontrol.fixtures.PaymentFixtures.mockPayment
import io.github.soat7.myburguercontrol.fixtures.PaymentFixtures.mockPaymentUpdateById
import io.github.soat7.myburguercontrol.fixtures.PaymentFixtures.mockQRCode
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.function.Executable
import java.util.UUID
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class PaymentUseCaseTest {

    private val paymentIntegrationRepository = mockk<PaymentIntegrationRepository>()
    private val paymentGateway = mockk<PaymentGateway>()
    private val service = PaymentUseCase(paymentIntegrationRepository, paymentGateway)

    @BeforeTest
    fun setUp() {
        clearMocks(paymentIntegrationRepository)
    }

    @AfterEach
    fun tearDown() {
        clearMocks(paymentIntegrationRepository, paymentGateway)
    }

    @Test
    @Order(1)
    fun `should create a new payment`() {
        val orderId = UUID.randomUUID()
        val paymentRequest = mockPayment(orderId)

        every { paymentIntegrationRepository.requestQRCodeDataForPayment(any<Payment>()) } returns mockQRCode(
            UUID.randomUUID().toString(),
        )
        every { paymentGateway.findById(any()) } returns mockPayment(orderId)
        every { paymentGateway.create(any()) } returns mockPayment(orderId)
        every { paymentGateway.update(any()) } returns mockPayment(orderId)

        val paymentResponse = service.createPayment(paymentRequest)

        verify(exactly = 1) { paymentGateway.create(any()) }
        verify(exactly = 0) { paymentGateway.findById(any()) }
        verify(exactly = 0) { paymentGateway.update(any()) }
        verify(exactly = 0) { paymentGateway.findByOrderId(any()) }

        Assertions.assertAll(
            Executable { assertNotNull(paymentResponse) },
            Executable { assertEquals(paymentRequest.status, paymentResponse.status) },
            Executable { assertEquals(paymentRequest.authorizationId, paymentResponse.authorizationId) },
            Executable { assertEquals(paymentRequest.orderPrice, paymentResponse.orderPrice) },
            Executable { assertEquals(paymentRequest.orderItems, paymentResponse.orderItems) },
            Executable { assertEquals(paymentRequest.orderId, paymentResponse.orderId) },
            Executable { assertEquals(paymentRequest.qrcode, paymentResponse.qrcode) },
            Executable { assertEquals(paymentRequest.status, paymentResponse.status) },
            Executable { assertNotEquals(paymentRequest.createdAt, paymentResponse.createdAt) },
            Executable { assertNotEquals(paymentRequest.updatedAt, paymentResponse.updatedAt) },
        )

        val response = assertDoesNotThrow {
            service.createPayment(paymentRequest)
        }

        assertNotNull(response)
    }

    @Test
    @Order(2)
    fun `should update a payment`() {
        val orderId = UUID.randomUUID()
        val payment = mockPayment(orderId)

        every { paymentIntegrationRepository.requestQRCodeDataForPayment(any<Payment>()) } returns mockQRCode(
            UUID.randomUUID().toString(),
        )
        every { paymentGateway.findById(any()) } returns mockPayment(orderId)
        every { paymentGateway.create(any()) } returns mockPayment(orderId)
        every { paymentGateway.update(any()) } returns mockPaymentUpdateById(payment.id, PaymentStatus.APPROVED)

        val paymentUpdated = service.updatePayment(payment.id.toString(), "paid")
        verify(exactly = 1) { paymentGateway.findById(any()) }
        verify(exactly = 1) { paymentGateway.update(any()) }
        verify(exactly = 0) { paymentGateway.findByOrderId(any()) }

        Assertions.assertAll(
            Executable { assertNotNull(paymentUpdated) },
            Executable { assertEquals(payment.authorizationId, paymentUpdated.authorizationId) },
            Executable { assertEquals(payment.authorizationId, paymentUpdated.authorizationId) },
            Executable { assertEquals(payment.orderPrice, paymentUpdated.orderPrice) },
            Executable { assertEquals(payment.orderItems, paymentUpdated.orderItems) },
            Executable { assertEquals(payment.orderId, paymentUpdated.orderId) },
            Executable { assertEquals(payment.qrcode, paymentUpdated.qrcode) },
            Executable { assertEquals(paymentUpdated.status, PaymentStatus.APPROVED) },
        )

        val response = assertDoesNotThrow {
            service.createPayment(paymentUpdated)
        }

        assertNotNull(response)
    }

    @Test
    @Order(3)
    fun `can't find payment in update`() {
        every { paymentGateway.findById(any()) } returns null

        Assertions.assertThrows(ReasonCodeException::class.java) {
            service.updatePayment(UUID.randomUUID().toString(), "paid")
        }

        verify(exactly = 1) { paymentGateway.findById(any()) }
        verify(exactly = 0) { paymentGateway.update(any()) }
    }

    @Test
    @Order(4)
    fun `should find a payment by orderId`() {
        val orderId = UUID.randomUUID()
        val payment = mockPayment(orderId)

        every { paymentGateway.findByOrderId(orderId) } returns payment

        val paymentResponse = service.getPaymentByOrderId(orderId)

        verify(exactly = 1) { paymentGateway.findByOrderId(orderId) }
        verify(exactly = 0) { paymentGateway.findById(any()) }
        verify(exactly = 0) { paymentGateway.create(any()) }
        verify(exactly = 0) { paymentGateway.update(any()) }

        Assertions.assertAll(
            Executable { assertNotNull(paymentResponse) },
            Executable { assertEquals(payment.id, paymentResponse.id) },
            Executable { assertEquals(payment.authorizationId, paymentResponse.authorizationId) },
            Executable { assertEquals(payment.orderPrice, paymentResponse.orderPrice) },
            Executable { assertEquals(payment.orderItems, paymentResponse.orderItems) },
            Executable { assertEquals(payment.orderId, paymentResponse.orderId) },
            Executable { assertEquals(payment.qrcode, paymentResponse.qrcode) },
            Executable { assertEquals(payment.status, paymentResponse.status) },
        )
    }
}
