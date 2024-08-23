package se.systementor.javasecstart.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DogRepository  extends JpaRepository<Dog, Long> {

    List<Dog> findAllBySoldToIsNull();


    Page<Dog> findByNameContainingIgnoreCaseOrBreedContainingIgnoreCaseOrSizeContainingIgnoreCaseOrAgeContainingIgnoreCaseOrPrice(
            String name, String breed, String age, String size, int price, Pageable pageable);





}