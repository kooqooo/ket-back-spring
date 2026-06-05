package kr.ai.ket.api.countries;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CountryDto {
    String name;
    String code;
    String code3;
    String flagEmoji;
}
