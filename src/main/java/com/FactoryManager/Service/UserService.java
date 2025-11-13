package com.FactoryManager.Service;

import com.FactoryManager.DTO.*;
import com.FactoryManager.Entity.Factory;
import com.FactoryManager.Repository.FactoryRepository;
import com.FactoryManager.util.JwtUtil;
import com.FactoryManager.Constatnts.Role;
import com.FactoryManager.Entity.DistributorDetails;
import com.FactoryManager.Entity.User;
import com.FactoryManager.Repository.UserRepository;
import com.FactoryManager.Security.CustomUserDetails;
import com.FactoryManager.exceptionHandling.EmailAlreadyExistException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    FactoryRepository factoryRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private CloudinaryService cloudinaryService;

    public SignUpResponseDto signup(@Valid SignUpRequestDto signUpRequestDto) {
        if (userRepository.findByEmail(signUpRequestDto.getEmail()).isPresent()) {
            throw new EmailAlreadyExistException("Email already exists!");
        }


        String imageUrl = null;
        if (signUpRequestDto.getPhoto() != null && !signUpRequestDto.getPhoto().isEmpty()) {
            try {
                imageUrl = cloudinaryService.uploadFile(signUpRequestDto.getPhoto());
            } catch (IOException e) {
                throw new RuntimeException("Error uploading image to Cloudinary", e);
            }
        }
        if (signUpRequestDto.getPassword() == null || signUpRequestDto.getPassword().trim().isEmpty()) {
            throw new RuntimeException("Password field is missing in the request!");
        }
        User distributor = User.builder()
                .username(signUpRequestDto.getUsername())
                .email(signUpRequestDto.getEmail())
                .phoneNo(signUpRequestDto.getPhoneNo())
                .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                .photo(imageUrl)
                .role(Role.DISTRIBUTOR)
                .build();

        DistributorDetails distributorDetails = DistributorDetails.builder()
                .city(signUpRequestDto.getCity())
                .state(signUpRequestDto.getState())
                .pinCode(signUpRequestDto.getPinCode())
                .companyName(signUpRequestDto.getCompanyName())
                .companyAddress(signUpRequestDto.getCompanyAddress())
                .gstId(signUpRequestDto.getGstId())
                .build();

        distributor.setDistributorDetails(distributorDetails);
        userRepository.save(distributor);

        SignUpResponseDto response = new SignUpResponseDto();
        response.setId(distributor.getId());
        response.setUsername(distributor.getUsername());
        response.setEmail(distributor.getEmail());

        return response;
    }


    public AuthResponseDto login(@Valid AuthRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(request.getEmail());
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        String token = jwtUtil.generateToken(userDetails.getUsername(), userDetails.getAuthorities().iterator().next().getAuthority());

        return new AuthResponseDto("Login successful",
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                token);
    }


    public UserResponseDto addPHOrChief(UserRequestDto dto) {

        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new EmailAlreadyExistException("Email already exists!");
        }

        String imageUrl = null;
        if (dto.getPhoto() != null && !dto.getPhoto().isEmpty()) {
            try {
                imageUrl = cloudinaryService.uploadFile(dto.getPhoto());
            } catch (IOException e) {
                throw new RuntimeException("Error uploading image to Cloudinary", e);
            }
        }

        User user = new User();
        user.setUsername(dto.getUsername());
                user.setEmail(dto.getEmail());
                user.setPhoto(imageUrl);
                user.setRole(dto.getRole());

        if (dto.getFcatory_id() != null) {
            Factory factory = factoryRepository.findById(dto.getFcatory_id())
                    .orElseThrow(() -> new RuntimeException("Factory not found with id: " + dto.getFcatory_id()));
            user.setFactory(factory);
        }

        userRepository.save(user);

        UserResponseDto response = new UserResponseDto();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());

        return response;

    }

    public CentralOfficerResDto addCO(CentralOfficerReqDto dto) {

        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new EmailAlreadyExistException("Email already exists!");
        }


        String imageUrl = null;
        if (dto.getPhoto() != null && !dto.getPhoto().isEmpty()) {
            try {
                imageUrl = cloudinaryService.uploadFile(dto.getPhoto());
            } catch (IOException e) {
                throw new RuntimeException("Error uploading image to Cloudinary", e);
            }
        }

        User user = User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .photo(imageUrl)
                .role(Role.CENTRAL_OFFICER)
                .build();


        userRepository.save(user);

        CentralOfficerResDto centralOfficerResDto = new CentralOfficerResDto();
        centralOfficerResDto.setId(user.getId());
        centralOfficerResDto.setUsername(user.getUsername());
        centralOfficerResDto.setEmail(user.getEmail());
        centralOfficerResDto.setRole(user.getRole());

        return centralOfficerResDto;


    }

    public UpdateUserResponseDto updateUser(UpdateUserRequestDto updateUserRequestDto) {

        User user = userRepository.findById(updateUserRequestDto.getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + updateUserRequestDto.getId()));

        if (updateUserRequestDto.getUsername() != null && !updateUserRequestDto.getUsername().isBlank()) {
            user.setUsername(updateUserRequestDto.getUsername());
        }

        if (updateUserRequestDto.getEmail() != null && !updateUserRequestDto.getEmail().isBlank()) {
            user.setEmail(updateUserRequestDto.getEmail());
        }

        if (updateUserRequestDto.getPhoto() != null && !updateUserRequestDto.getPhoto().isEmpty()) {
            try {
                String imageUrl = cloudinaryService.uploadFile(updateUserRequestDto.getPhoto());
                user.setPhoto(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Error uploading image to Cloudinary", e);
            }
        }

        userRepository.save(user);

        UpdateUserResponseDto updateUserResponseDto = new UpdateUserResponseDto();
        updateUserResponseDto.setId(user.getId());
        updateUserResponseDto.setUserName(user.getUsername());
        updateUserResponseDto.setEmail(user.getEmail());
        updateUserResponseDto.setPhoto(user.getPhoto());
        return updateUserResponseDto;
    }

    public String deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));

        userRepository.delete(user);

        return "User deleted successfully with id: " + id;
    }

    public List<RoleCountResponseDto> getRoleCounts() {

        List<Role> allowedRoles = List.of(Role.CHIEF_SUPERVISOR, Role.PLANT_HEAD, Role.CENTRAL_OFFICER, Role.WORKER);

        List<RoleCountResponseDto> counts = userRepository.getRoleCountsForSpecificRoles(allowedRoles);

        return counts.stream()
                .map(c -> new RoleCountResponseDto(
                        formatRoleName(c.getRole()),
                        c.getCount()
                ))
                .collect(Collectors.toList());
    }



    private String formatRoleName(String roleName) {
        if (roleName == null) return "";
        String formatted = roleName.substring(0, 1).toUpperCase() + roleName.substring(1).toLowerCase();
        if (!formatted.endsWith("s")) {
            formatted += "s";
        }
        return formatted;
    }

    public List<UnassignedPlantHeadResponseDto> getUnassignedPlantHeads() {
        List<User> users = userRepository.findByRoleAndFactoryIsNull(Role.PLANT_HEAD);

        return users.stream()
                .map(u -> new UnassignedPlantHeadResponseDto(
                        u.getId(),
                        u.getUsername(),
                        u.getEmail(),
                        u.getRole()
                ))
                .collect(Collectors.toList());
    }

    public List<UnassignedPlantHeadResponseDto> getUnassignedchiefsupervisor() {
        List<User> users = userRepository.findByRoleAndFactoryIsNull(Role.CHIEF_SUPERVISOR);

        return users.stream()
                .map(u -> new UnassignedPlantHeadResponseDto(
                        u.getId(),
                        u.getUsername(),
                        u.getEmail(),
                        u.getRole()
                ))
                .collect(Collectors.toList());
    }
    public Page<EmpByRoleAndFactoryResDto> getEmpByRoleAndFactory(String roleName, Long factoryId, String search, int page, int size) {
        Role role = Role.fromValue(roleName);

        Pageable pageable = PageRequest.of(page, size);

        // ✅ 1. Fetch paginated data from DB
        Page<User> userPage;
        if (factoryId != null) {
            userPage = userRepository.findByRoleAndFactory_Id(role, factoryId, pageable);
        } else {
            userPage = userRepository.findByRole(role, pageable);
        }

        // ✅ 2. Apply search (in-memory filter)
        List<User> filteredUsers = userPage.getContent().stream()
                .filter(user -> {
                    if (search == null || search.trim().isEmpty()) return true;
                    String keyword = search.toLowerCase(Locale.ROOT);
                    return user.getUsername().toLowerCase(Locale.ROOT).contains(keyword)
                            || user.getEmail().toLowerCase(Locale.ROOT).contains(keyword);
                })
                .collect(Collectors.toList());

        // ✅ 3. Convert to DTO
        List<EmpByRoleAndFactoryResDto> dtoList = filteredUsers.stream()
                .map(user -> new EmpByRoleAndFactoryResDto(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getRole(),
                        user.getPhoto(),
                        user.getFactory() != null ? user.getFactory().getName() : "N/A"
                ))
                .collect(Collectors.toList());

        // ✅ 4. Return paginated response
        return new PageImpl<>(dtoList, pageable, userPage.getTotalElements());
    }

}
