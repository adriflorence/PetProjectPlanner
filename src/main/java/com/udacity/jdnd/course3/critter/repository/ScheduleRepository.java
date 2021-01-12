package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByEmployeeId(Long employeeId);

    @Query("SELECT s FROM Schedule s JOIN s.pets p ON p.customer = :customer")
    List<Schedule> findByCustomer(Customer customer);

    @Query("SELECT s FROM Schedule s WHERE :pet MEMBER OF s.pets")
    List<Schedule> findByPet(Pet pet);
}