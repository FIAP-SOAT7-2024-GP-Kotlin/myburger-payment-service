package io.github.soat7.myburguercontrol.webservice

import io.github.soat7.myburguercontrol.adapters.gateway.PaymentIntegrationRepository
import io.github.soat7.myburguercontrol.base.BaseIntegrationTest
import io.github.soat7.myburguercontrol.external.db.payment.PaymentGateway
import io.github.soat7.myburguercontrol.external.webservice.payment.api.PaymentResponse
import io.github.soat7.myburguercontrol.fixtures.HttpMockRequest
import io.github.soat7.myburguercontrol.fixtures.PaymentFixtures
import io.github.soat7.myburguercontrol.fixtures.PaymentFixtures.mockQRCode
import io.mockk.clearMocks
import io.mockk.mockk
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import org.springframework.boot.test.web.client.exchange
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import java.util.UUID
import kotlin.test.BeforeTest

class PaymentIT : BaseIntegrationTest() {

    private val paymentIntegrationRepository = mockk<PaymentIntegrationRepository>()
    private val paymentGateway = mockk<PaymentGateway>()

    @BeforeTest
    fun setUp() {
        clearMocks(paymentIntegrationRepository)
    }

    @AfterEach
    fun tearDown() {
        clearMocks(paymentIntegrationRepository, paymentGateway)
    }

    @Test
    fun `should successfully create a new payment`() {
        val orderId = UUID.randomUUID()
        val paymentRequest = PaymentFixtures.mockPaymentRequest(orderId)

        HttpMockRequest.mockQRCodeDataResponse(mockQRCode(orderId.toString()))
        val response = restTemplate.exchange<PaymentResponse>(
            url = "/payment",
            method = HttpMethod.POST,
            requestEntity = HttpEntity(paymentRequest),
        )

        assertAll(
            Executable { assertTrue(response.statusCode.is2xxSuccessful) },
            Executable { assertNotNull(response.body) },
            Executable { assertEquals(orderId, response.body!!.orderId) },
        )
    }

    @Test
    fun `should successfully get payment`() {
        val orderId = UUID.randomUUID()
        val payment = insertPaymentData(PaymentFixtures.mockPayment(orderId))

        val response = restTemplate.exchange<PaymentResponse>(
            url = "/payment/{id}",
            method = HttpMethod.GET,
            requestEntity = null,
            uriVariables = mapOf(
                "id" to payment.id,
            ),
        )

        assertAll(
            Executable { assertTrue(response.statusCode.is2xxSuccessful) },
            Executable { assertNotNull(response.body) },
            Executable { assertEquals(payment.id, UUID.fromString(response.body!!.id)) },
        )
    }

    @Test
    fun `should successfully get payment from orderId`() {
        val orderId = UUID.randomUUID()
        val payment = insertPaymentData(PaymentFixtures.mockPayment(orderId))

        val response = restTemplate.exchange<PaymentResponse>(
            url = "/payment/order/{id}",
            method = HttpMethod.GET,
            requestEntity = null,
            uriVariables = mapOf(
                "id" to payment.orderId,
            ),
        )

        assertAll(
            Executable { assertTrue(response.statusCode.is2xxSuccessful) },
            Executable { assertNotNull(response.body) },
            Executable { assertEquals(payment.orderId, response.body!!.orderId) },
        )
    }
}
