package io.github.soat7.myburguercontrol.external.db.payment

import io.github.soat7.myburguercontrol.adapters.gateway.PaymentRepository
import io.github.soat7.myburguercontrol.adapters.mapper.toDomain
import io.github.soat7.myburguercontrol.adapters.mapper.toPersistence
import io.github.soat7.myburguercontrol.domain.entities.Payment
import io.github.soat7.myburguercontrol.external.db.payment.repository.PaymentJpaRepository
import org.springframework.stereotype.Component
import java.util.UUID
import kotlin.jvm.optionals.getOrNull

@Component
class PaymentGateway(
    private val repository: PaymentJpaRepository,
) : PaymentRepository {

    override fun create(payment: Payment): Payment {
        return repository.save(payment.toPersistence()).toDomain()
    }

    override fun findById(id: UUID): Payment? {
        return repository.findById(id).getOrNull()?.toDomain()
    }

    override fun findByOrderId(orderId: UUID): Payment? {
        return repository.findByOrderId(orderId).getOrNull()?.toDomain()
    }

    override fun update(payment: Payment): Payment {
        return repository.save(payment.toPersistence()).toDomain()
    }
}
