package pw.rejchev.springtesttask.entities.converters;

import org.springframework.core.convert.converter.Converter;
import pw.rejchev.springtesttask.entities.Deposit;
import pw.rejchev.springtesttask.entities.dto.DepositDto;

public class DepositToDtoConverter implements Converter<Deposit, DepositDto> {
    @Override
    public DepositDto convert(Deposit source) {
        return DepositDto.builder()
                .id(source.getId())
                .bankId(source.getBank().getBic().toString())
                .clientId(source.getClient().getId())
                .createdAt(source.getCreatedAt())
                .rate(source.getRate())
                .durationInMonth(source.getDurationInMonth())
                .build();
    }
}
