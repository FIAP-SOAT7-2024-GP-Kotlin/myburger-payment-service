package io.github.soat7.myburguercontrol.webservice

import io.github.soat7.myburguercontrol.base.BaseIntegrationTest
import io.github.soat7.myburguercontrol.external.webservice.payment.api.PaymentResponse
import io.github.soat7.myburguercontrol.fixtures.PaymentFixtures
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

class PaymentIT : BaseIntegrationTest() {

    @Test
    fun `should successfully create a new payment`() {
        val orderId = UUID.randomUUID()
        val inputCustomerData = PaymentFixtures.mockPayment(orderId)

        val response = restTemplate.exchange<PaymentResponse>(
            url = "/payment",
            method = HttpMethod.POST,
            requestEntity = HttpEntity(inputCustomerData),
        )

        assertAll(
            Executable { assertTrue(response.statusCode.is2xxSuccessful) },
            Executable { assertNotNull(response.body) },
            Executable { assertEquals(orderId, response.body!!.orderId) },
        )
        val savedPayment = paymentJpaRepository.findById(UUID.fromString(response.body!!.id))

        assertAll(
            Executable { assertNotNull(savedPayment) },
            Executable { assertEquals(orderId, savedPayment.get().orderId) },
            Executable { assertEquals(response.body!!.id, savedPayment.get().id) },
        )
    }
}
