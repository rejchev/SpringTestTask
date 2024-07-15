package pw.rejchev.springtesttask.entities.converters;

import org.springframework.core.convert.converter.Converter;
import pw.rejchev.springtesttask.entities.Bank;
import pw.rejchev.springtesttask.entities.dto.BankDto;

public class BankToDtoConverter implements Converter<Bank, BankDto> {

    @Override
    public BankDto convert(Bank source) {
        return BankDto.builder()
                .bic(source.getBic())
                .name(source.getName())
                .build();
    }
}
