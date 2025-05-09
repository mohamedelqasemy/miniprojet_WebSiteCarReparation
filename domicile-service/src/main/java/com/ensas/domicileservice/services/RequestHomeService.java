package com.ensas.domicileservice.services;

import com.ensas.domicileservice.clients.UserRestClient;
import com.ensas.domicileservice.dtos.RequestHomeDto;
import com.ensas.domicileservice.dtos.UserDTO;
import com.ensas.domicileservice.entities.RequestHome;
import com.ensas.domicileservice.mappers.RequestHomeMapper;
import com.ensas.domicileservice.repositories.RequestHomeRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RequestHomeService {
    private final RequestHomeRepository requestHomeRepository;
    private final UserRestClient userRestClient;

    public RequestHomeService(RequestHomeRepository requestHomeRepository, UserRestClient userRestClient) {
        this.requestHomeRepository = requestHomeRepository;
        this.userRestClient = userRestClient;
    }
    
    public List<RequestHomeDto> getAllRequestHome(){
        List<RequestHome> requestHomes = requestHomeRepository.findAll();
        return RequestHomeMapper.toRequestHomeDtoList(requestHomes);
    }
    
    public RequestHomeDto getRequestHomeById(Long id){
        return requestHomeRepository.findById(id)
                .map(RequestHomeMapper::toRequestHomeDto)
                .orElseThrow(() -> new RuntimeException("La demande domicile non trouvé"));
    }

    public RequestHomeDto createRequestHome(RequestHomeDto requestHomeDto){
        RequestHome requestHome = RequestHomeMapper.toRequestHome(requestHomeDto);
        requestHome = requestHomeRepository.save(requestHome);
        return RequestHomeMapper.toRequestHomeDto(requestHome);
    }

    @Transactional
    public RequestHomeDto updateRequestHome(Long id,RequestHomeDto requestHomeDto) {
        if (requestHomeDto == null || id == null) {
            throw new IllegalArgumentException("Les données de la demande domicile ne peuvent pas être nulles");
        }

        RequestHome existingRequest = requestHomeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La demande domicile non trouvé"));
        
        if (requestHomeDto.getAddress() != null) {
            existingRequest.setAddress(requestHomeDto.getAddress());
        }
        if (requestHomeDto.getUserId() != null) {
            existingRequest.setUserId(requestHomeDto.getUserId());
        }
        if (requestHomeDto.getStatus() != null) {
            existingRequest.setStatus(requestHomeDto.getStatus());
        }
        if (requestHomeDto.getDateDemand() != null) {
            existingRequest.setDateDemand(requestHomeDto.getDateDemand());
        }

        return RequestHomeMapper.toRequestHomeDto(existingRequest);
    }

    public void deleteRequestsHome(Long id) {
        if (!requestHomeRepository.existsById(id)) {
            throw new RuntimeException("Voiture non trouvé");
        }
        requestHomeRepository.deleteById(id);
    }
    
    public RequestHomeDto defaultRequestHomeDto(){
        return RequestHomeDto.builder()
                .id(0L)
                .userId(0L)
                .user(new UserDTO())
                .address("Not Available")
                .dateDemand(new Date())
                .status("Not Available")
                .build();
    }

    public Page<RequestHomeDto> getAllRequestPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<RequestHome> reservationPage = requestHomeRepository.findAll(pageable);

        return reservationPage.map(request -> {
            UserDTO user = userRestClient.findUserById(request.getUserId());
            RequestHomeDto requestHomeDto = RequestHomeMapper.toRequestHomeDto(request);
            requestHomeDto.setUser(user);
            return requestHomeDto;
        });
    }
    
}
