package io.swagger.api.controllers;

import io.swagger.annotations.ApiParam;
import io.swagger.api.PetsApi;
import io.swagger.model.NewPet;
import io.swagger.model.Pet;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PetsApiController implements PetsApi {



    public ResponseEntity<Pet> addPet(@ApiParam(value = "Pet to add to the store" ,required=true )  @Valid @RequestBody NewPet pet) {
        // do some magic!
        return new ResponseEntity<Pet>(HttpStatus.OK);
    }

    public ResponseEntity<Pet> findPetById(@ApiParam(value = "ID of pet to fetch",required=true ) @PathVariable("id") Long id) {
        // do some magic!
        return new ResponseEntity<Pet>(HttpStatus.OK);
    }

    public ResponseEntity<List<Pet>> findPets(@ApiParam(value = "tags to filter by") @RequestParam(value = "tags", required = false) List<String> tags,
        @ApiParam(value = "maximum number of results to return") @RequestParam(value = "limit", required = false) Integer limit) {
        // dummy code
        List list = new ArrayList();
        Pet test = new Pet();
        test.setId(new Long(0));
        test.setName("Swagger pet");
        test.setTag("Swagger");

        list.add(test);

        ResponseEntity<List<Pet>> res = new ResponseEntity<List<Pet>>(list, HttpStatus.OK);

        return res;
    }

}
