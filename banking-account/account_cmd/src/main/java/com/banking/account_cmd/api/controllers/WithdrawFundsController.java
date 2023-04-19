package com.banking.account_cmd.api.controllers;


import com.banking.account_cmd.api.command.DepositFundsCommand;
import com.banking.account_cmd.api.command.WithdrawFunsCommand;
import com.banking.account_common.dto.BaseResponse;
import com.banking.cqrs_core.excepcions.AggregateNotFoundException;
import com.banking.cqrs_core.infrastructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/withdrawFunds")
public class WithdrawFundsController {

    private final Logger logger = Logger.getLogger(WithdrawFundsController.class.getName());

    @Autowired
    private CommandDispatcher commandDispatcher;

    @PutMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> withDrawFunds(@PathVariable(value = "id") String id, @RequestBody WithdrawFunsCommand command){
        // para generar codigo unico

        try{
            command.setId(id);
            commandDispatcher.send(command);
            return new ResponseEntity<>(new BaseResponse("El retiro fue exitoso"), HttpStatus.OK);
        }catch (IllegalStateException | AggregateNotFoundException e){
            logger.log(Level.WARNING, MessageFormat.format("El cliente envio un request con errores", e.toString()));
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            var safeErrorMessage = MessageFormat.format("Errores mientras se generaba el request - {id}", id);
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        }

    }

}
