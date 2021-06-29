package com.paypal.forex.repository;

import com.paypal.forex.entity.TransactionRecord;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TransactionRepository extends JpaRepository<TransactionRecord, Long> {

    @Query("Select t from TransactionRecord t where t.loanAmount > (0.7 * t.blockedAsset * :currentPrice)")
    List<TransactionRecord> getAllNotificationRecords(@Param("currentPrice") double currentPrice);
}
