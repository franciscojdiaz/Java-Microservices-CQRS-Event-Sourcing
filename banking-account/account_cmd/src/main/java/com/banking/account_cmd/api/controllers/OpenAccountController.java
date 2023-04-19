package com.banking.account_cmd.api.controllers;


import com.banking.account_cmd.api.command.OpenAccountCommand;
import com.banking.account_cmd.api.dto.OpenAccountResponse;
import com.banking.account_common.dto.BaseResponse;
import com.banking.cqrs_core.infrastructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/openBankAccount")
public class OpenAccountController {

    private final Logger logger = Logger.getLogger(OpenAccountController.class.getName());

    @Autowired
    private CommandDispatcher commandDispatcher;

    //el cliente es el que crea el objeto command
    @PostMapping
    public ResponseEntity<BaseResponse> openAccount(@RequestBody OpenAccountCommand command){
        // para generar codigo unico
        var id = UUID.randomUUID().toString();

        command.setId(id);

        try {
            commandDispatcher.send(command);
            return new ResponseEntity<>(new OpenAccountResponse("La cuenta fue creada exitosamente",id), HttpStatus.CREATED);
        }catch (IllegalStateException es){
            logger.log(Level.WARNING, MessageFormat.format("No se pudo generar la cuenta - {0}", es.toString()));
            return new ResponseEntity<>(new BaseResponse(es.toString()), HttpStatus.BAD_REQUEST);
        }catch (Exception ex ){
            var safeErrorMessage = MessageFormat.format("Errores mientras se generaba el request - {0}", id);
            logger.log(Level.SEVERE, safeErrorMessage);
            return new ResponseEntity<>(new OpenAccountResponse(safeErrorMessage,id), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
