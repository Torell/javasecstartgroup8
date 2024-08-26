package se.systementor.javasecstart.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DogRepository  extends JpaRepository<Dog, Long> {

    List<Dog> findAllBySoldToIsNull();

    Page<Dog> findByNameContainingIgnoreCaseOrBreedContainingIgnoreCaseOrSizeContainingIgnoreCaseOrAgeContainingIgnoreCase(
            String name, String breed, String age, String size, Pageable pageable);

    Page<Dog> findByNameContainingIgnoreCaseOrBreedContainingIgnoreCaseOrSizeContainingIgnoreCaseOrAgeContainingIgnoreCaseOrPrice(
            String name, String breed, String age, String size, int price, Pageable pageable);

     Optional<Dog> findById(Integer id);
}