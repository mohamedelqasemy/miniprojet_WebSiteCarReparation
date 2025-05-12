package com.ensas.domicileservice.web;

import com.ensas.domicileservice.dtos.RequestHomeDto;
import com.ensas.domicileservice.dtos.RequestHomeRequest;
import com.ensas.domicileservice.dtos.RequestHomeResponse;
import com.ensas.domicileservice.entities.RequestHome;
import com.ensas.domicileservice.feign.UserRestClient;
import com.ensas.domicileservice.mappers.UserMapper;
import com.ensas.domicileservice.models.User;
import com.ensas.domicileservice.models.UserDto;
import com.ensas.domicileservice.services.RequestHomeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<RequestHome> createRequestHome(@RequestBody RequestHomeRequest requestHomeRequest) {
        RequestHome request=requestHomeService.createRequestHome(requestHomeRequest);
        return ResponseEntity.ok(request);
    }

    //get all cars
    @GetMapping
    public ResponseEntity<List<RequestHomeDto>> getAllRequestsHome() {
        List<RequestHomeDto> requestHomeDtoList = requestHomeService.getAllRequestHome();
        requestHomeDtoList.forEach( home-> {
            User user = userRestClient.getUserById(home.getUserId());
            home.setUser(UserMapper.toDTO(user));

        });
        return ResponseEntity.ok(requestHomeDtoList);
    }

    //get a specific car
    @GetMapping("/{id}")
    public ResponseEntity<RequestHomeDto> getRequestHomeById(@PathVariable("id") Long id) {
        RequestHomeDto requestHomeDto = requestHomeService.getRequestHomeById(id);
        User user = userRestClient.getUserById(requestHomeDto.getUserId());
        requestHomeDto.setUser(UserMapper.toDTO(user));

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


    @GetMapping("/paginated")
    public ResponseEntity<Page<RequestHomeResponse>> getAllRequestPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String search
    ) {
        Page<RequestHomeResponse> requests = requestHomeService.getAllRequestPaginated(page, size,search);
        return ResponseEntity.ok(requests);
    }

    //blocked days
    @GetMapping("/blocked-dates")
    public List<String> getBlockedDatesFromTomorrow(
            @RequestParam(defaultValue = "8") int maxPerDay
    ) {
        return requestHomeService.getBlockedDatesFromTomorrow(maxPerDay);
    }
}
