package se.systementor.javasecstart.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
//ev ändra till JPA
public interface DogRepository  extends JpaRepository<Dog, Long> {

    List<Dog> findAllBySoldToIsNull();

    //Skapa sök sort metod, finns redan
    Page<Dog> findByNameContainingIgnoreCaseOrBreedContainingIgnoreCase(String name, String breed, Pageable pageable);

}