package com.banking.account_query.domain;

import com.banking.account_common.dto.AccountType;
import com.banking.cqrs_core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class BankAccount extends BaseEntity {

        @Id
        private String id;
        private String accountHolder;
        private Date creationDate;
        private AccountType accountType;
        private double balance;


}
