package com.Jeeva.TxnService.controller;

import com.Jeeva.TxnService.model.Txn;
import com.Jeeva.TxnService.service.TxnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/txn")
public class TxnController {

    @Autowired
    private TxnService txnService;

    @PostMapping("/initTxn")
    public String sendMoney(@RequestParam("amount") String amount,
                            @RequestParam("receiverId") String receiverId,
                            @RequestParam("purpose") String purpose){

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return txnService.initTxn(userDetails.getUsername(), receiverId, amount, purpose);

    }

    public List<Txn> getTxns(@RequestParam("page") int page,
                             @RequestParam("size") int size){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return txnService.getTxns(userDetails.getUsername(), page, size);
    }


}
