package com.banking.account_query.api.queries;

import com.banking.account_query.api.dto.EquelityType;
import com.banking.cqrs_core.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.bytebuddy.implementation.bytecode.assign.Assigner;

@Data
@AllArgsConstructor
public class FindAccountWithBalanceQuery extends BaseQuery {

    private double balance;
    private EquelityType equelityType;
}
