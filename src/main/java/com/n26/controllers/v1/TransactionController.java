package com.n26.controllers.v1;

import com.n26.api.v1.model.TransactionDTO;
import com.n26.services.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.n26.util.LoggingUtil.getEnteringMethodMessage;

@Slf4j
@Api("Controller to manage transaction operations")
@RestController
@RequestMapping(TransactionController.BASE_URL)
public class TransactionController {
    static final String BASE_URL = "/transactions";

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @ApiOperation(value = "Add a transaction")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
        log.debug(getEnteringMethodMessage("TransactionController.addTransaction", transactionDTO));
        transactionService.addTransaction(transactionDTO);
    }

    @ApiOperation(value = "Delete all transactions")
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllTransactions() {
        log.debug(getEnteringMethodMessage("TransactionController.deleteAllTransactions"));
        transactionService.deleteAllTransactions();
    }

}
