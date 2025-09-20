package com.khan.hospital_management.repository;

import com.khan.hospital_management.model.Bill;
import com.khan.hospital_management.model.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    @Query("SELECT COALESCE(SUM(b.amount), 0) FROM Bill b WHERE b.createdAt BETWEEN :start AND :end AND b.status = :status")
    BigDecimal sumAmountByCreatedAtBetweenAndStatus(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("status") PaymentStatus status);
}
