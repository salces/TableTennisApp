package pl.edu.wat.web.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api")
public class NationalitiesResource {

    @RequestMapping(value = "/nationalities",method = GET)
    public List<String> getAllNationalities(){
        List<String> nationalities = new ArrayList<>();
        String[] nationalitiesCodes = Locale.getISOCountries();

        for (String code : nationalitiesCodes) {
            Locale l = new Locale("", code);
            nationalities.add(l.getDisplayCountry(l));
        }
        Collections.sort(nationalities);
        return nationalities;
    }
}
