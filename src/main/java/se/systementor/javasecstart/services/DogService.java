package se.systementor.javasecstart.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import se.systementor.javasecstart.model.Dog;
import se.systementor.javasecstart.model.DogRepository;

import java.util.List;

@Service
public class DogService {

    @Autowired
    DogRepository dogRepository;

    public List<Dog> getPublicDogs(){
        return dogRepository.findAllBySoldToIsNull();
    }

    public Page<Dog>findAllDogsWithSearch(String search, Pageable pageable) {
        return (search.isBlank() || !search.matches(".*\\d.*"))
                ? findAllDogsWithSearchExcludingPrice(search, pageable)
                : dogRepository.findByNameContainingIgnoreCaseOrBreedContainingIgnoreCaseOrSizeContainingIgnoreCaseOrAgeContainingIgnoreCaseOrPrice
                    (search, search, search, search, onlyDigits(search), pageable);
    }

    private Page<Dog>findAllDogsWithSearchExcludingPrice(String search, Pageable pageable) {
        return dogRepository.findByNameContainingIgnoreCaseOrBreedContainingIgnoreCaseOrSizeContainingIgnoreCaseOrAgeContainingIgnoreCase(
                search, search, search, search, pageable);
    }

    private int onlyDigits(String search) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < search.length(); i++) {
            builder.append(Character.isDigit(search.charAt(i)) ? search.charAt(i) : "");
        }

        return Integer.parseInt(builder.toString());
    }
}
