package io.github.soat7.myburguercontrol.external.webservice.config

import io.github.soat7.myburguercontrol.adapters.controller.NotificationHandler
import io.github.soat7.myburguercontrol.adapters.controller.PaymentHandler
import io.github.soat7.myburguercontrol.domain.usecase.NotificationIpnUseCase
import io.github.soat7.myburguercontrol.domain.usecase.NotificationWebhookUseCase
import io.github.soat7.myburguercontrol.domain.usecase.PaymentUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class WebServiceBeanConfiguration {

    @Bean
    fun paymentHandler(paymentUseCase: PaymentUseCase) = PaymentHandler(paymentUseCase = paymentUseCase)

    @Bean
    fun notificationHandler(
        notificationWebhookUseCase: NotificationWebhookUseCase,
        notificationIpnUseCase: NotificationIpnUseCase,
    ) = NotificationHandler(
        notificationWebhookUseCase = notificationWebhookUseCase,
        notificationIpnUseCase = notificationIpnUseCase,
    )
}
