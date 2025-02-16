package com.ensas.domicileservice.web;

import com.ensas.domicileservice.clients.UserRestClient;
import com.ensas.domicileservice.dtos.RequestHomeDto;
import com.ensas.domicileservice.models.User;
import com.ensas.domicileservice.services.RequestHomeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/requestHomes")
@AllArgsConstructor
public class RequestHomeRestController {
    private final RequestHomeService requestHomeService;
    private final UserRestClient userRestClient;


    //create a car
    @PostMapping
    public ResponseEntity<RequestHomeDto> createRequestHome(@RequestBody RequestHomeDto requestHomeDto) {
        RequestHomeDto request=requestHomeService.createRequestHome(requestHomeDto);
        return ResponseEntity.ok(request);
    }

    //get all cars
    @GetMapping
    public ResponseEntity<List<RequestHomeDto>> getAllRequestsHome() {
        List<RequestHomeDto> requestHomeDtoList = requestHomeService.getAllRequestHome();
        return ResponseEntity.ok(requestHomeDtoList);
    }

    //get a specific car
    @GetMapping("/{id}")
    public ResponseEntity<RequestHomeDto> getRequestHomeById(@PathVariable("id") Long id) {
        RequestHomeDto requestHomeDto = requestHomeService.getRequestHomeById(id);
        User user = userRestClient.findUserById(requestHomeDto.getUserId());
        requestHomeDto.setUser(user);
        return ResponseEntity.ok(requestHomeDto);
    }

    //update a car that exists .
    @PutMapping("/{id}")
    public ResponseEntity<RequestHomeDto> updateRequestHome(@PathVariable("id") Long id,@RequestBody RequestHomeDto requestHomeDto) {
        RequestHomeDto request = requestHomeService.updateRequestHome(id,requestHomeDto);
        return ResponseEntity.ok(request);
    }

    //delete a car that exists .
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRequestHome(@PathVariable("id") Long id) {
        requestHomeService.deleteRequestsHome(id);
        return ResponseEntity.noContent().build();
    }
}
