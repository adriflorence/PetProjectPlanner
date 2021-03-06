package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = petService.savePet(petDTO);
        return DTOUtils.convertPetToDTO(pet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Optional<Pet> pet = petService.getPetById(petId);
        if(pet.isPresent()) {
            return DTOUtils.convertPetToDTO(pet.get());
        } else {
            return null;
        }
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = petService.getPets();
        List<PetDTO> petDTOS = new ArrayList<>();
        for(Pet pet : pets) {
            petDTOS.add(DTOUtils.convertPetToDTO(pet));
        }
        return petDTOS;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> pets = petService.getPetsByCustomerId(ownerId);
        List<PetDTO> petDTOS = new ArrayList<>();
        for(Pet pet : pets) {
            petDTOS.add(DTOUtils.convertPetToDTO(pet));
        }
        return petDTOS;
    }
}
