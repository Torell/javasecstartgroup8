package se.systementor.javasecstart.utils;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.systementor.javasecstart.model.Dog;
import se.systementor.javasecstart.model.DogRepository;

@Component
public class DataSeeder {

    @Autowired
    DogRepository dogRepository;

    private final Faker faker = new Faker();
    private final RandomSelector randomSelector = new RandomSelector("static/images/dogs");

    public void Seed() {
        if(dogRepository.count() > 0 ){
            return;
        }
        for(int i =0; i < 100; i++) {
            dogRepository.save(RandomDog());
        }
    }

    private Dog RandomDog() {
        Dog dog = new Dog();
        dog.setAge(faker.dog().age());
        dog.setName(faker.dog().name());
        dog.setBreed(faker.dog().breed());
        dog.setGender(faker.dog().gender());
        dog.setPrice((faker.random().nextInt(4, 20) * 1000));
        dog.setSize(faker.dog().size());
        dog.setImage("/images/dogs/" + randomSelector.getRandomImage());
        return dog;
    }
}