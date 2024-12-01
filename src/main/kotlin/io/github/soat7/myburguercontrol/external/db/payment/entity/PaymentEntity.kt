package io.github.soat7.myburguercontrol.external.db.payment.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

@Entity
@Table(
    name = "payment",
    schema = "myburguer_payment",
)
class PaymentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    var id: UUID,

    @Column(name = "status", length = 255, nullable = false)
    var status: String,

    @Column(name = "authorization_id", length = 255)
    var authorizationId: String? = null,

    @Column(name = "order_id", nullable = false)
    var orderId: UUID,

    @Column(name = "order_price", nullable = false)
    var orderPrice: BigDecimal,

    @Column(name = "qrcode", nullable = true)
    var qrcode: String? = null,

    @Column(name = "created_at", nullable = false)
    val createdAt: Instant,

    @Column(name = "updated_at", nullable = false)
    val updatedAt: Instant,
)
