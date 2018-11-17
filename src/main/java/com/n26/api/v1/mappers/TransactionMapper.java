package com.n26.api.v1.mappers;

import com.n26.api.v1.model.TransactionDTO;
import com.n26.domain.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    Transaction transactionDTOtoTransaction(TransactionDTO transactionDTO);
}
