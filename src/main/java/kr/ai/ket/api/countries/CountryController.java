package kr.ai.ket.api.countries;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CountryController {

    @GetMapping("/countries")
    public ResponseEntity<CountryDto> get() {
        return CountryDto;
    }

}
