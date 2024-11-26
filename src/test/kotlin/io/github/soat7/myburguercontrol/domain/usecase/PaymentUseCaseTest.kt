package io.github.soat7.myburguercontrol.domain.usecase

import io.github.soat7.myburguercontrol.adapters.gateway.PaymentIntegrationRepository
import io.github.soat7.myburguercontrol.domain.entities.Payment
import io.github.soat7.myburguercontrol.external.db.payment.PaymentGateway
import io.github.soat7.myburguercontrol.fixtures.PaymentFixtures.mockPayment
import io.github.soat7.myburguercontrol.fixtures.PaymentFixtures.mockQRCode
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
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

    @Test
    @Order(1)
    fun `should create a new order using cpf`() {
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
            { assertNotNull(paymentResponse) },
            Executable { assertEquals(paymentRequest.orderId, paymentResponse.orderId) },
        )

        val response = assertDoesNotThrow {
            service.createPayment(paymentRequest)
        }

        assertNotNull(response)
    }
}
