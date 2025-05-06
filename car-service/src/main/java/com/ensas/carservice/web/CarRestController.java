package com.ensas.carservice.web;

import com.ensas.carservice.clients.UserRestClient;
import com.ensas.carservice.dtos.CarDto;
import com.ensas.carservice.models.User;
import com.ensas.carservice.services.CarService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
@AllArgsConstructor()
public class CarRestController {
    private final CarService carService;
    private final UserRestClient userRestClient;


    //create a car
    @PostMapping
    public ResponseEntity<CarDto> createCar(@RequestBody CarDto carDto) {
        CarDto car=carService.createCar(carDto);
        return ResponseEntity.ok(car);
    }

    //get all cars
    @GetMapping
    public ResponseEntity<List<CarDto>> getAllCars() {
        List<CarDto> carDtoList = carService.getAllCars();
        return ResponseEntity.ok(carDtoList);
    }
    @GetMapping("/paginated")
    public ResponseEntity<Page<CarDto>> getAllCars(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<CarDto> cars = carService.getAllCarsPaginated(page, size);
        return ResponseEntity.ok(cars);
    }

    //get a specific car
    @GetMapping("/{id}")
    public ResponseEntity<CarDto> getCarById(@PathVariable("id") Long id) {
        CarDto carDto = carService.getCarById(id);
        User user = userRestClient.findUserById(carDto.getUserId());
        carDto.setUser(user);
        return ResponseEntity.ok(carDto);
    }

    //update a car that exists .
    @PutMapping("/{id}")
    public ResponseEntity<CarDto> updateCar(@PathVariable("id") Long id,@RequestBody CarDto carDto) {
        CarDto car = carService.updateCar(id,carDto);
        return ResponseEntity.ok(car);
    }

    //delete a car that exists .
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable("id") Long id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }
}
