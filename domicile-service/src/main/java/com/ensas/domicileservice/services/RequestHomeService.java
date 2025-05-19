package com.ensas.domicileservice.services;

import com.ensas.domicileservice.dtos.RequestHomeDto;
import com.ensas.domicileservice.dtos.RequestHomeRequest;
import com.ensas.domicileservice.dtos.RequestHomeResponse;
import com.ensas.domicileservice.feign.HistoryRestClient;
import com.ensas.domicileservice.mappers.UserMapper;
import com.ensas.domicileservice.models.*;
import com.ensas.domicileservice.entities.RequestHome;
import com.ensas.domicileservice.enums.EnumStatus;
import com.ensas.domicileservice.feign.CarRestClient;
import com.ensas.domicileservice.feign.ServiceRestClient;
import com.ensas.domicileservice.feign.UserRestClient;
import com.ensas.domicileservice.mappers.RequestHomeMapper;
import com.ensas.domicileservice.repositories.RequestHomeRepository;
import com.ensas.domicileservice.util.RequestHomeSpecification;
import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RequestHomeService {
    private final RequestHomeRepository requestHomeRepository;
    private final UserRestClient userRestClient;
    private final CarRestClient carRestClient;
    private final ServiceRestClient serviceRestClient;
    private final HistoryRestClient historyRestClient;

    public RequestHomeService(RequestHomeRepository requestHomeRepository, UserRestClient userRestClient, CarRestClient carRestClient, ServiceRestClient serviceRestClient,HistoryRestClient historyRestClient) {
        this.requestHomeRepository = requestHomeRepository;
        this.userRestClient = userRestClient;
        this.carRestClient = carRestClient;
        this.serviceRestClient = serviceRestClient;
        this.historyRestClient = historyRestClient;
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

    public RequestHome createRequestHome(RequestHomeRequest reservation) {
        // Récupération sécurisée des entités liées
        User user = getUserSafe(reservation.getUserId());
        List<Reparation> services = new ArrayList<>();
        for (Long id : reservation.getServiceId()) {
            services.add(getServiceSafe(id));
        }


        RequestHome newReservation = new RequestHome();
        newReservation.setDateDemand(reservation.getDate());
        newReservation.setStatus(EnumStatus.Pending);
        newReservation.setUserId(reservation.getUserId());
        newReservation.setServiceId(reservation.getServiceId());
        newReservation.setAddress(reservation.getAddress());
        newReservation.setServiceNames(services.stream().map(Reparation::getName).collect(Collectors.toList()));

        CarDto carDto = CarDto.builder()
                .brand(reservation.getBrand())
                .model(reservation.getModel())
                .image("")
                .kilometers(reservation.getKilometers())
                .motorisation(reservation.getMotorisation())
                .licensePlate(reservation.getLicensePlate())
                .userId(user.getId())
                .build();

        Long myCarId = carRestClient.getCarIdByLicensePlate(reservation.getLicensePlate().trim());

        Long carId = myCarId;
        if (carId == 0) {
            CarDto newCar = carRestClient.createCar(carDto);
            carId = newCar.getId();
        }
        newReservation.setCarId(carId);

        BreakdownHistoryDto newHistory = BreakdownHistoryDto.builder()
                .carId(carId)
                .datePanne(reservation.getDate())
                .isRepaired(false)
                .nomPanne("fin njibo smiyat les pannes a drari")
                .description("o htta desc")
                .build();
        BreakdownHistoryDto createdHistory = historyRestClient.createBreakdownHistory(newHistory);
        return requestHomeRepository.save(newReservation);
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
                .user(new UserDto())
                .address("Not Available")
                .dateDemand(new Date())
                .status(EnumStatus.Pending)
                .build();
    }

    public Page<RequestHomeResponse> getAllRequestPaginated(int page, int size,String search) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
//        Page<RequestHome> reservationPage = requestHomeRepository.findAll(spec,pageable);
        Specification<RequestHome> spec = Specification.where(null);
        spec = spec.and(RequestHomeSpecification.hasName(search));
        Page<RequestHome> reservationPage = requestHomeRepository.findAll(spec,pageable);

        return reservationPage.map(request -> {
            User user = userRestClient.getUserById(request.getUserId());
            List<Reparation> services = request.getServiceId().stream()
                    .map(serviceRestClient::getServiceById)
                    .collect(Collectors.toList());
            return RequestHomeMapper.mapToDtoN(request, user, services);
        });
    }


    //for giving blocked days
    public List<String> getBlockedDatesFromTomorrow(int maxPerDay) {
        // Start from tomorrow
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = truncateTime(cal.getTime());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return requestHomeRepository.findAll().stream()
                .filter(res -> res.getStatus() != EnumStatus.Rejected)
                .map(res -> truncateTime(res.getDateDemand()))
                .filter(date -> !date.before(tomorrow)) // Only from tomorrow onward
                .collect(Collectors.groupingBy(date -> date, Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() >= maxPerDay)
                .map(entry -> sdf.format(entry.getKey())) // Format as string: yyyy-MM-dd
                .toList();
    }




    // ====================
    // Méthodes privées de sécurisation
    // ====================

    private User getUserSafe(Long userId) {
        try {
            User user = userRestClient.getUserById(userId);
            if (user == null) throw new Exception("Utilisateur nul");
            return user;
        } catch (FeignException.NotFound e) {
            log.warn("Utilisateur non trouvé (ID : {}). Utilisation d’un utilisateur par défaut. Erreur : {}", userId, e.getMessage());
            User defaultUser = new User();
            defaultUser.setFirstname("Default");
            defaultUser.setLastname("User");
            defaultUser.setEmail("anonyme@example.com");
            return defaultUser;
        } catch (Exception e) {
            log.warn("Erreur lors de la récupération de l'utilisateur (ID : {}). Utilisation d’un utilisateur par défaut. Erreur : {}", userId, e.getMessage());
            User defaultUser = new User();
            defaultUser.setFirstname("Default");
            defaultUser.setLastname("User");
            defaultUser.setEmail("anonyme@example.com");
            return defaultUser;
        }
    }

    private Reparation getServiceSafe(Long serviceId) {
        try {
            Reparation service = serviceRestClient.getServiceById(serviceId);
            if (service == null) throw new Exception("Service nul");
            return service;
        } catch (Exception e) {
            log.warn("Service non trouvé (ID : {}). Utilisation d’un service par défaut. Erreur : {}", serviceId, e.getMessage());
            Reparation defaultService = new Reparation();
            defaultService.setName("Service inconnu");
            defaultService.setServicePrice(0.0);
            defaultService.setImage("default.jpg");
            return defaultService;
        }
    }
    private Date truncateTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    
}
