package io.swagger.api.services;

import io.swagger.model.NewPet;
import io.swagger.model.Pet;
import java.util.List;


public interface PetsApiService {

  public Pet addPet(NewPet pet);

  public Pet findPetById(Long id);

  public List<Pet> findPets(List<String> tags, Integer limit);
}
