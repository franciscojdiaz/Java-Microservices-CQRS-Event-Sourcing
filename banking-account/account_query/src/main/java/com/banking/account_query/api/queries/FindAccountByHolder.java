package com.banking.account_query.api.queries;

import com.banking.cqrs_core.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAccountByHolder extends BaseQuery {

    private String accountHolder;
}
