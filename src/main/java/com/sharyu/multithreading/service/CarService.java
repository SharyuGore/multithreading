package com.sharyu.multithreading.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sharyu.multithreading.domain.Car;
import com.sharyu.multithreading.repository.CarRepository;

@Service
public class CarService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CarService.class);

	@Autowired
	private CarRepository carRepository;

	@Async
	public CompletableFuture<List<Car>> saveCars() throws Exception {
		final long start = System.currentTimeMillis();

		List<Car> cars = parseCSVFile();

		System.out.println("Saving a list of cars of size {} records" + cars.size());
		LOGGER.info("Saving a list of cars of size {} records", cars.size());

		cars = carRepository.saveAll(cars);

		LOGGER.info("Elapsed time: {}", (System.currentTimeMillis() - start));
		return CompletableFuture.completedFuture(cars);
	}

	private List<Car> parseCSVFile() throws Exception {
		final List<Car> cars = new ArrayList<>();
		try {
			
			try (final BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\S7G\\Desktop\\Book1.csv"))) {
				String line;
				while ((line = br.readLine()) != null) {
					final String[] data = line.split(";");
					final Car car = new Car();
					car.setManufacturer(data[0]);
					car.setModel(data[1]);
					car.setType(data[2]);
					cars.add(car);
				}
				return cars;
			}
		} catch (final IOException e) {
			LOGGER.error("Failed to parse CSV file {}", e);
			throw new Exception("Failed to parse CSV file {}", e);
		}
	}

	@Async
	public CompletableFuture<List<Car>> getAllCars() {

		LOGGER.info("Request to get a list of cars");

		final List<Car> cars = carRepository.findAll();
		return CompletableFuture.completedFuture(cars);
	}
	

}
