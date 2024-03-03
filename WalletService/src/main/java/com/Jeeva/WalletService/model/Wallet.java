package com.Jeeva.WalletService.model;

import com.Jeeva.Utils.UserIdentifier;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.*;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private String contact;

    @Enumerated(EnumType.STRING)
    private UserIdentifier userIdentifier;

    private String userIdentifierValue;

    private Double balance;

    @Timestamp
    private Date createdOn;

    @Timestamp
    private Date updatedOn;
}
