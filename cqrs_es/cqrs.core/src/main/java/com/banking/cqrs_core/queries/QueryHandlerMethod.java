package com.banking.cqrs_core.queries;

import com.banking.cqrs_core.domain.BaseEntity;

import java.util.List;

// es generica pero de aquellas clases que extieda del BaseQuer
@FunctionalInterface
public interface QueryHandlerMethod <T extends BaseQuery>{

    List<BaseEntity> handle(T query);
}
