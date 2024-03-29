package com.Jeeva.TxnService.repository;

import com.Jeeva.TxnService.model.Txn;
import com.Jeeva.TxnService.model.TxnStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;



public interface TxnRepository extends JpaRepository<Txn, Integer> {
    @Transactional
    @Modifying
    @Query("update Txn t set t.txnStatus=:txnStatus, t.message=:message where t.txnId=:txnId")
    void updateTxnStatus(String txnId, TxnStatus txnStatus, String message);

    Page<Txn> findBySenderId(String sender, Pageable pageable);
}
