package com.money.domain.payment.repository;

import com.money.domain.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long>, PaymentRepositoryCustom {

}
