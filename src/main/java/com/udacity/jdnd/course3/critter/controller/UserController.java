package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = userService.saveCustomer(customerDTO);
        return DTOUtils.convertCustomerToDTO(customer);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customers = userService.getAllCustomers();
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        for(Customer customer : customers) {
            customerDTOS.add(DTOUtils.convertCustomerToDTO(customer));
        }

        return customerDTOS;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Optional<Customer> optCustomer = userService.getOwnerByPet(petId);
        if (optCustomer.isPresent()) {
            return DTOUtils.convertCustomerToDTO(optCustomer.get());
        }
        return null;
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = userService.saveEmployee(employeeDTO);
        return DTOUtils.convertEmployeeToDTO(employee);
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Optional<Employee> employeeById = userService.getEmployeeById(employeeId);
        if(employeeById.isPresent()) {
            return DTOUtils.convertEmployeeToDTO(employeeById.get());
        }
        return null;
    }

    @PutMapping("/employee/{employeeId}")
    public EmployeeDTO setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        Optional<Employee> optionalEmployee = userService.getEmployeeById(employeeId);
        if(optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            employee.setDaysAvailable(daysAvailable);
            Employee emp = userService.saveEmployee(DTOUtils.convertEmployeeToDTO(employee));
            return DTOUtils.convertEmployeeToDTO(emp);
        }
        return null;
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> employees = userService.getEmployeeBySkillsAndAvailability(employeeDTO);
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        for(Employee employee : employees) {
            employeeDTOS.add(DTOUtils.convertEmployeeToDTO(employee));
        }
        return employeeDTOS;
    }

}
