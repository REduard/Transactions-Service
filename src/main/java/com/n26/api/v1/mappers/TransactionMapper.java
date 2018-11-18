package com.n26.api.v1.mappers;

import com.n26.api.v1.model.TransactionDTO;
import com.n26.domain.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface used by Mapstruct to generate mapping Class.
 * The generated class, TransactionMapperImpl, is used to map the
 * TransactionDTO to the transaction domain object
 */
@Mapper
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    Transaction transactionDTOtoTransaction(TransactionDTO transactionDTO);
}
