package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import com.udacity.jdnd.course3.critter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private UserService userService;

    @Autowired
    private PetService petService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule(scheduleDTO.getDate(), scheduleDTO.getActivities());

        // set employees
        List<Employee> employees = new ArrayList<>();
        for(Long id : scheduleDTO.getEmployeeIds()) {
            Optional<Employee> employee = userService.getEmployeeById(id);
            if(employee.isPresent()) {
                employees.add(employee.get());
            }
        }
        schedule.setEmployee(employees);

        // set pets
        List<Pet> pets = new ArrayList<>();
        for(Long id : scheduleDTO.getPetIds()){
            Optional<Pet> pet = petService.getPetById(id);
            if(pet.isPresent()) {
                pets.add(pet.get());
            }
        }
        schedule.setPets(pets);

        Schedule updatedSchedule = scheduleService.saveSchedule(schedule);
        return DTOUtils.convertScheduleToDTO(updatedSchedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAllSchedules();
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for(Schedule schedule : schedules) {
            scheduleDTOS.add(DTOUtils.convertScheduleToDTO(schedule));
        }
        return scheduleDTOS;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> schedulesForPet = scheduleService.getSchedulesForPet(petId);
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for (Schedule schedule : schedulesForPet) {
            scheduleDTOS.add(DTOUtils.convertScheduleToDTO(schedule));
        }
        return scheduleDTOS;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getSchedulesForEmployee(@PathVariable long employeeId) {
        List<Schedule> schedules = scheduleService.getSchedulesForEmployee(employeeId);
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for(Schedule schedule : schedules) {
            scheduleDTOS.add(DTOUtils.convertScheduleToDTO(schedule));
        }
        return scheduleDTOS;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getSchedulesForCustomer(@PathVariable long customerId) {
       List<Schedule> schedules = scheduleService.getSchedulesForCustomer(customerId);
       List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
       for(Schedule schedule : schedules) {
           scheduleDTOS.add(DTOUtils.convertScheduleToDTO(schedule));
       }
       return scheduleDTOS;
    }
}
