package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DTOUtils {

    public static CustomerDTO getDTOFromCustomer(Customer customer) {
        List<Pet> pets = customer.getPets();
        List<Long> petIds = new ArrayList<>();
        if(pets != null) {
            petIds = pets.stream().map(Pet::getId).collect(Collectors.toList());
        }

        return new CustomerDTO(customer.getId(), customer.getName(), customer.getPhoneNumber(), customer.getNotes(), petIds);
    }

}
