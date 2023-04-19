package com.banking.account_query.infraestructura.handlers;

import com.banking.account_common.events.AccountClosedEvent;
import com.banking.account_common.events.AccountOpenedEvent;
import com.banking.account_common.events.FundsDepositedEvent;
import com.banking.account_common.events.FundsWhithdrawEvent;

public interface EventHandler {

    void on(AccountOpenedEvent event);
    void on(FundsDepositedEvent event);
    void on(FundsWhithdrawEvent event);
    void on(AccountClosedEvent event);

}
