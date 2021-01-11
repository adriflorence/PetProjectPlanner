package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Pet savePet(PetDTO petDTO) {
        Pet pet = new Pet(petDTO.getType(), petDTO.getName(), petDTO.getBirthDate(), petDTO.getNotes());

        Optional<Customer> optionalCustomer = customerRepository.findById(petDTO.getOwnerId());
        if(optionalCustomer.isPresent()){
            Customer customer = optionalCustomer.get();
            pet.setCustomer(customer);
            List<Pet> existingPets = customer.getPets();
            if(existingPets != null) {
                existingPets.add(pet);
            } else {
                List<Pet> pets = new ArrayList<>();
                pets.add(pet);
                customer.setPets(pets);
            }
            pet = petRepository.save(pet);
            customerRepository.save(customer);
        } else {
            throw new IllegalStateException("Customer not found");
        }
        return pet;
    }

    public List<Pet> getPetsByCustomerId(long customerId) {
        List<Pet> pets = petRepository.findByCustomerId(customerId);
        return pets;
    }

    public Optional<Pet> getPetById(long petId) {
        return petRepository.findById(petId);
    }
}
