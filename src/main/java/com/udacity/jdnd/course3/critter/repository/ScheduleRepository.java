package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByEmployeeId(Long employeeId);

    @Query("SELECT s FROM Schedule s WHERE :customerId IN (SELECT p.customer FROM Pet p)")
    List<Schedule> findByCustomerId(Long customerId);

    @Query("SELECT s FROM Schedule s WHERE :petId IN (s.pets)")
    List<Schedule> findByPetId(Long petId);
}