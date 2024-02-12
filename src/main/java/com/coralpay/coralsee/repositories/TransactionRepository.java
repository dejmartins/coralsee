package com.coralpay.coralsee.repositories;

import com.coralpay.coralsee.entities.Transaction;
import com.coralpay.coralsee.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE t.user.id = :userId AND t.paidAt >= :startDate")
    Page<Transaction> findWeekToDateTransactions(@Param("userId") String userId, @Param("startDate") LocalDateTime startDate, Pageable pageable);
}
