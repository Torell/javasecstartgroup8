package se.systementor.javasecstart.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Page<Dog> searchDogs(String name, String breed, String size, String age, int price, Pageable pageable){
        return dogRepository.findByNameContainingIgnoreCaseOrBreedContainingIgnoreCaseOrSizeContainingIgnoreCaseOrAgeContainingIgnoreCaseOrPrice(
                name, breed, size, age, price, pageable);
    }





}
