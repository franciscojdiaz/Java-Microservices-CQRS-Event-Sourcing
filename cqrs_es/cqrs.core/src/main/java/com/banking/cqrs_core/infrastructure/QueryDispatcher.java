package com.banking.cqrs_core.infrastructure;

import com.banking.cqrs_core.domain.BaseEntity;
import com.banking.cqrs_core.queries.BaseQuery;
import com.banking.cqrs_core.queries.QueryHandlerMethod;

import java.util.List;

public interface QueryDispatcher {

    <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler);
    <U extends BaseEntity> List<U> send(BaseQuery query);

}
