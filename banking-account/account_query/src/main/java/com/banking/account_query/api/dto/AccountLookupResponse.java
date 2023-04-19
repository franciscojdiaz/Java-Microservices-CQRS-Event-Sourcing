package com.banking.account_query.api.dto;

import com.banking.account_common.dto.BaseResponse;
import com.banking.account_query.domain.BankAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AccountLookupResponse extends BaseResponse {

    private List<BankAccount> accounts;

    public AccountLookupResponse(String message) {
        super(message);

    }
}
