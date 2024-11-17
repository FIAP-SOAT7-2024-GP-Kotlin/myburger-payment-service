package io.github.soat7.myburguercontrol.domain.usecase

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class PaymentUseCaseTest {

//    private val paymentIntegrationRepository = mockk<PaymentIntegrationRepository>()
//    private val paymentGateway = mockk<PaymentGateway>()
//    private val orderGateway = mockk<OrderGateway>()
//    private val service = PaymentUseCase(paymentIntegrationRepository, paymentGateway, orderGateway)
//
//    @BeforeTest
//    fun setUp() {
//        clearMocks(paymentIntegrationRepository)
//    }
//
//    @Test
//    @Order(1)
//    fun `should try to request QRCode successfully using an external service`() {
//        val order = mockOrder(mockDomainCustomer(cpf = "12312312312"))
//
//        every { paymentIntegrationRepository.requestQRCodeDataForPayment(any<OrderModel>()) } returns mockQRCode(
//            UUID.randomUUID().toString(),
//        )
//        every { orderGateway.findById(any()) } returns order
//        every { orderGateway.update(any()) } returns order
//        every { paymentGateway.findById(any()) } returns mockPayment()
//        every { paymentGateway.create(any()) } returns mockPayment()
//        every { paymentGateway.update(any()) } returns mockPayment()
//
//        val response = assertDoesNotThrow {
//            service.startPaymentRequest(order.id)
//        }
//
//        assertNotNull(response)
//    }
}
