package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DTOUtils {

    public static CustomerDTO convertCustomerToDTO(Customer customer) {
        List<Pet> pets = customer.getPets();
        List<Long> petIds = getPetIds(pets);

        return new CustomerDTO(customer.getId(), customer.getName(), customer.getPhoneNumber(), customer.getNotes(), petIds);
    }

    public static EmployeeDTO convertEmployeeToDTO(Employee employee) {
        return new EmployeeDTO(employee.getId(),employee.getName(),employee.getSkills(),employee.getDaysAvailable());
    }

    public static PetDTO convertPetToDTO(Pet pet) {
        return new PetDTO(pet.getId(), pet.getType(), pet.getName(), pet.getCustomer().getId(), pet.getBirthDate(), pet.getNotes());
    }

    public static ScheduleDTO convertScheduleToDTO(Schedule schedule) {
//        return new ScheduleDTO(schedule.getId(), getPetIds(schedule.getPets()));
        return null;
    }

    private static List<Long> getPetIds(List<Pet> pets) {
        List<Long> petIds = new ArrayList<>();
        if(pets != null) {
            petIds = pets.stream().map(Pet::getId).collect(Collectors.toList());
        }
        return petIds;
    }
}
