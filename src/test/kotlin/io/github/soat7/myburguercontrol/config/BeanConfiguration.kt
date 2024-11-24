package io.github.soat7.myburguercontrol.config

import io.github.soat7.myburguercontrol.adapters.gateway.PaymentIntegrationRepository
import io.github.soat7.myburguercontrol.domain.usecase.PaymentUseCase
import io.github.soat7.myburguercontrol.external.db.payment.PaymentGateway
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile

@TestConfiguration
@Profile("test")
class BeanConfiguration{

    @Bean
    fun paymentUseCase(
        paymentIntegrationRepository: PaymentIntegrationRepository,
        paymentGateway: PaymentGateway,
    ) = PaymentUseCase(
        paymentIntegrationRepository = paymentIntegrationRepository,
        paymentGateway = paymentGateway,
    )
}
