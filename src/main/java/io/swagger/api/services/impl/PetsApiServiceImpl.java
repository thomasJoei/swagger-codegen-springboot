package io.swagger.api.services.impl;

import io.swagger.api.services.PetsApiService;
import io.swagger.model.NewPet;
import io.swagger.model.Pet;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class PetsApiServiceImpl implements PetsApiService {

  public Pet addPet(NewPet pet) {
    // do some magic!
    return new Pet();
  }

  public Pet findPetById(Long id) {
    // do some magic!
    return new Pet();
  }

  public List<Pet> findPets(List<String> tags, Integer limit) {
    // dummy code
    List list = new ArrayList();
    Pet test = new Pet();
    test.setId(new Long(0));
    test.setName("Swagger pet");
    test.setTag("Swagger");

    list.add(test);

    return list;
  }
}
