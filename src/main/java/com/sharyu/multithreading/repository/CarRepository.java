package com.sharyu.multithreading.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sharyu.multithreading.domain.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

}