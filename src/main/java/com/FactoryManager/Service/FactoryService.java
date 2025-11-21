package com.FactoryManager.Service;

import com.FactoryManager.Constants.Role;
import com.FactoryManager.DTO.*;
import com.FactoryManager.Entity.*;
import com.FactoryManager.Repository.FactoryProductRepository;
import com.FactoryManager.Repository.FactoryRepository;
import com.FactoryManager.Repository.ProductRepository;
import com.FactoryManager.Repository.UserRepository;
import com.FactoryManager.exceptionHandling.ElementNotFoundException;
import com.FactoryManager.exceptionHandling.FactoryAlreadyExist;
import com.FactoryManager.exceptionHandling.IllegalMoveException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

    @Transactional
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
    @Transactional
    public String getPlantHeadNameByFactoryId(Long factoryId) {
        return userRepository.findByFactoryIdAndRole(factoryId, Role.PLANT_HEAD)
                .map(User::getUsername)
                .orElse("N/A");
    }

    @Transactional
    public Page<AllFactoryResponseDto> getAllFactories(
            String search, String location, int page, int size) {

        List<Factory> allFactories = factoryRepository.findAll();

        List<Factory> filtered = allFactories.stream()
                .filter(f -> {

                    if (search != null && !search.isBlank()) {
                        String s = search.toLowerCase();
                        boolean match =
                                f.getName().toLowerCase().contains(s) ||
                                        f.getLocation().toLowerCase().contains(s);
                        if (!match) return false;
                    }

                    if (location != null && !location.isBlank()) {
                        if (!f.getLocation().equalsIgnoreCase(location.trim())) {
                            return false;
                        }
                    }

                    return true;
                })
                .collect(Collectors.toList());

        filtered.sort(Comparator.comparing(Factory::getCreatedAt).reversed());

        int start = Math.min(page * size, filtered.size());
        int end = Math.min(start + size, filtered.size());
        List<Factory> paginatedList = filtered.subList(start, end);

        List<AllFactoryResponseDto> dtoList = paginatedList.stream()
                .map(factory -> new AllFactoryResponseDto(
                        factory.getId(),
                        factory.getName(),
                        factory.getLocation(),
                        getPlantHeadNameByFactoryId(factory.getId())
                )).toList();

        return new PageImpl<>(dtoList, PageRequest.of(page, size), filtered.size());
    }


    @Transactional
    public FactoryResponseDto deleteFactory(Long id) {

        Factory factory = factoryRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Factory not found!"));

        // STEP 1: Unlink all users before deleting factory
        List<User> users = factory.getUsers();
        if (users != null && !users.isEmpty()) {
            for (User user : users) {
                user.setFactory(null); // remove foreign key reference
            }
            userRepository.saveAll(users);
        }

        factoryRepository.delete(factory);

        FactoryResponseDto response = new FactoryResponseDto();
        response.setMessage("Factory deleted successfully!");

        return response;
    }


    @Transactional
    public List<LocationFactoryCountResponseDto> getLocationWiseFactoryCount() {

        List<LocationFactoryCountResponseDto> list = factoryRepository.getLocationWiseFactoryCount();

        if (list.isEmpty()) return list;

        // take unique top 3 counts
        Set<Long> uniqueCounts = new LinkedHashSet<>();

        for (LocationFactoryCountResponseDto dto : list) {
            uniqueCounts.add(dto.getFactoryCount());
            if (uniqueCounts.size() == 3) break;  // top 3 distinct counts mil gaye
        }

        // include all items whose count is in that TOP-3 set
        return list.stream()
                .filter(dto -> uniqueCounts.contains(dto.getFactoryCount()))
                .collect(Collectors.toList());
    }


    @Transactional
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


        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("filterType", type);
        response.put("count", result.size());
        response.put("data", result);

        return response;
    }

}

