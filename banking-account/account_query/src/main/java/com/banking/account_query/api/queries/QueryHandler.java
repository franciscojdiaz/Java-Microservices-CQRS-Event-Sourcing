package com.banking.account_query.api.queries;

import com.banking.cqrs_core.domain.BaseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


public interface QueryHandler {

    List<BaseEntity> handle(FindAllAccountsQuery query);
    List<BaseEntity> handle(FindAccountByIdQuery query);
    List<BaseEntity> handle(FindAccountByHolder query);
    List<BaseEntity> handle(FindAccountWithBalanceQuery query);
}
