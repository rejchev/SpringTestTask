package pw.rejchev.springtesttask.entities.converters;

import org.springframework.core.convert.converter.Converter;
import pw.rejchev.springtesttask.entities.Bank;
import pw.rejchev.springtesttask.entities.dto.BankDto;

public class DtoToBankConverter implements Converter<BankDto, Bank> {
    @Override
    public Bank convert(BankDto source) {
        return Bank.builder()
                .bic(source.getBic())
                .name(source.getName())
                .build();
    }
}
