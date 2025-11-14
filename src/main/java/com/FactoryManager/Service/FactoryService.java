package com.FactoryManager.Service;

import com.FactoryManager.Constatnts.Role;
import com.FactoryManager.DTO.*;
import com.FactoryManager.Entity.*;
import com.FactoryManager.Repository.FactoryProductRepository;
import com.FactoryManager.Repository.FactoryRepository;
import com.FactoryManager.Repository.ProductRepository;
import com.FactoryManager.Repository.UserRepository;
import com.FactoryManager.exceptionHandling.ElementNotFoundException;
import com.FactoryManager.exceptionHandling.FactoryAlreadyExist;
import com.FactoryManager.exceptionHandling.IllegalMoveException;
import com.cloudinary.api.exceptions.AlreadyExists;
import com.cloudinary.api.exceptions.BadRequest;
import com.cloudinary.api.exceptions.NotFound;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FactoryService {
    @Autowired
    private FactoryRepository factoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    FactoryProductRepository factoryProductRepository;

    public FactoryResponseDto createFactory(FactoryRequestDto factoryRequestDto) {
        if (factoryRepository.findByName(factoryRequestDto.getName()).isPresent()) {
            throw new FactoryAlreadyExist("Factory with this name already exists! " + factoryRequestDto.getName());
        }

        // 1️⃣ Create factory
        Factory factory = new Factory();
        factory.setName(factoryRequestDto.getName());
        factory.setLocation(factoryRequestDto.getLocation());
        factory = factoryRepository.save(factory);


        List<Product> products = productRepository.findAll();


        List<FactoryProduct> factoryProducts = new ArrayList<>();

        for (Product product : products) {
            FactoryProduct fp = new FactoryProduct();
            fp.setFactory(factory);
            fp.setProduct(product);
            fp.setQuantity(100);


            factoryProducts.add(fp);
        }

        factoryProductRepository.saveAll(factoryProducts);



        FactoryResponseDto factoryResponseDto = new FactoryResponseDto();
        factoryResponseDto.setMessage("factory created successfully!");
        return factoryResponseDto;
    }

    public FactoryResponseDto updateFactory(Long id, FactoryRequestDto factoryUpdateRequestDto) {
        Factory factory = factoryRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Factory not found! "));

        User plantHead = userRepository.findById(factoryUpdateRequestDto.getPlantHead_id())
                .orElseThrow(() -> new UsernameNotFoundException("Plant head not found"));


        if (plantHead.getFactory() != null && !plantHead.getFactory().getId().equals(factory.getId())) {
            throw new IllegalMoveException("This plant head is already assigned to another factory!");
        }
        if (factoryUpdateRequestDto.getName() != null && !factoryUpdateRequestDto.getName().isBlank()) {
            factory.setName(factoryUpdateRequestDto.getName());

        }

        if (factoryUpdateRequestDto.getLocation() != null && !factoryUpdateRequestDto.getLocation().isBlank()) {
            factory.setLocation(factoryUpdateRequestDto.getLocation());

        }
        if (factoryUpdateRequestDto.getPlantHead_id() != null) {
            plantHead.setFactory(factory);


        }

        factoryRepository.save(factory);

        FactoryResponseDto response = new FactoryResponseDto();
        response.setMessage("Factory updated successfully!");
        return response;
    }

    public String getPlantHeadNameByFactoryId(Long factoryId) {
        return userRepository.findByFactoryIdAndRole(factoryId, Role.PLANT_HEAD)
                .map(User::getUsername)
                .orElse("N/A");
    }

    public Page<AllFactoryResponseDto> getAllFactories(Pageable pageable) {
        Page<Factory> factoryPage = factoryRepository.findAll(pageable);

        return factoryPage.map(factory -> new AllFactoryResponseDto(
                factory.getId(),
                factory.getName(),
                factory.getLocation(),
                getPlantHeadNameByFactoryId(factory.getId())
        ));
    }

    public FactoryResponseDto deleteFactory(Long id) {
        Factory factory = factoryRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Factory not found! "));
        factoryRepository.deleteById(factory.getId());

        FactoryResponseDto response = new FactoryResponseDto();
        response.setMessage("Factory deleted successfully!");
        return response;
    }


    public List<LocationFactoryCountResponseDto> getLocationWiseFactoryCount() {

        List<LocationFactoryCountResponseDto> list = factoryRepository.getLocationWiseFactoryCount();

        if (list.isEmpty()) return list;

        // Step 1: take unique top 3 counts
        Set<Long> uniqueCounts = new LinkedHashSet<>();

        for (LocationFactoryCountResponseDto dto : list) {
            uniqueCounts.add(dto.getFactoryCount());
            if (uniqueCounts.size() == 3) break;  // top 3 distinct counts mil gaye
        }

        // Step 2: include all items whose count is in that TOP-3 set
        return list.stream()
                .filter(dto -> uniqueCounts.contains(dto.getFactoryCount()))
                .collect(Collectors.toList());
    }



    public Map<String, Object> getFactories(String type) {

        List<Factory> factories = factoryRepository.findAll();

        List<FactorySimpleDto> result;

        if (type == null || type.equalsIgnoreCase("all")) {
            // All factories
            result = factories.stream()
                    .map(f -> new FactorySimpleDto(f.getId(), f.getName(), f.getLocation()))
                    .collect(Collectors.toList());
        }
        else if (type.equalsIgnoreCase("unassigned")) {
            // Only factories where no user has role = PLANT_HEAD
            result = factories.stream()
                    .filter(f -> f.getUsers() == null ||
                            f.getUsers().stream()
                                    .noneMatch(u -> u.getRole() == Role.PLANT_HEAD))
                    .map(f -> new FactorySimpleDto(f.getId(), f.getName(), f.getLocation()))
                    .collect(Collectors.toList());
        }
        else {
            throw new IllegalArgumentException("Invalid type! Use 'all' or 'unassigned'.");
        }

        // ✅ Step 3: Prepare clean response
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("filterType", type);
        response.put("count", result.size());
        response.put("data", result);

        return response;
    }

}

