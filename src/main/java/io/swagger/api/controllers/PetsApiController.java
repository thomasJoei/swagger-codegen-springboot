package io.swagger.api.controllers;

import io.swagger.annotations.ApiParam;
import io.swagger.api.PetsApi;
import io.swagger.api.services.PetsApiService;
import io.swagger.model.NewPet;
import io.swagger.model.Pet;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PetsApiController implements PetsApi {

    @Autowired
    protected PetsApiService petsApiService;

    @Override
    public ResponseEntity<Pet> addPet(@ApiParam(value = "Pet to add to the store" ,required=true )  @Valid @RequestBody NewPet pet) {
        Pet newPet = petsApiService.addPet(pet);

        return new ResponseEntity(newPet, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Pet> findPetById(@ApiParam(value = "ID of pet to fetch",required=true ) @PathVariable("id") Long id) {
        Pet pet = petsApiService.findPetById(id);

        return new ResponseEntity<Pet>(pet,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Pet>> findPets(@ApiParam(value = "tags to filter by") @RequestParam(value = "tags", required = false) List<String> tags,
        @ApiParam(value = "maximum number of results to return") @RequestParam(value = "limit", required = false) Integer limit) {
        // dummy code
        List result = new ArrayList();
        result.addAll(petsApiService.findPets(tags, limit));

        return new ResponseEntity<List<Pet>>(result, HttpStatus.OK);
    }

}
