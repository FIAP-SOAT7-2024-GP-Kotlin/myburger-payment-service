package io.github.soat7.myburguercontrol.external.webservice.payment

import io.github.soat7.myburguercontrol.adapters.controller.PaymentHandler
import io.github.soat7.myburguercontrol.external.webservice.payment.api.PaymentCreationRequest
import io.github.soat7.myburguercontrol.external.webservice.payment.api.PaymentResponse
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController("payment-controller")
@RequestMapping(
    path = ["payment"],
    produces = [MediaType.APPLICATION_JSON_VALUE],
)
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
class PaymentController(
    private val paymentHandler: PaymentHandler,
) {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createPayment(@RequestBody request: PaymentCreationRequest): ResponseEntity<PaymentResponse>? = run {
        return ResponseEntity.ok(paymentHandler.createPayment(request))
    }

    @GetMapping("/{paymentId}")
    fun getPayment(@PathVariable paymentId: UUID): ResponseEntity<PaymentResponse> {
        return ResponseEntity.ok(paymentHandler.getPayment(paymentId))
    }

    @GetMapping("/order/{orderId}")
    fun getPaymentByOrderId(@PathVariable orderId: UUID): ResponseEntity<PaymentResponse> {
        return ResponseEntity.ok(paymentHandler.getPaymentByOrderId(orderId))
    }
}
