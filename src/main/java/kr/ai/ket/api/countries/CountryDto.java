package kr.ai.ket.api.countries;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CountryDto {
    private final String name;
    private final String code;
    private final String code3;
    private final String flagEmoji;
}
