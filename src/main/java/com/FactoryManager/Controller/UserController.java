package com.FactoryManager.Controller;

import com.FactoryManager.DTO.*;
import com.FactoryManager.Service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private  UserService userService;

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping(value = "/ph-supervisor" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserResponseDto> addPHOrChief(@Valid @ModelAttribute UserRequestDto dto) {
        UserResponseDto savedUser = userService.addPHOrChief(dto);
        return ResponseEntity.ok(savedUser);
    }

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping(value = "/centralofficer" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CentralOfficerResDto> addcentralofficer(@Valid @ModelAttribute CentralOfficerReqDto centralOfficerReqDto) {
        CentralOfficerResDto savedUser = userService.addCO(centralOfficerReqDto);
        return ResponseEntity.ok(savedUser);
    }

    @PreAuthorize("hasAnyRole('OWNER','CHIEF_SUPERVISOR','PLANT_HEAD')")
    @PatchMapping(value = "/update-emp", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UpdateUserResponseDto> updateUser(@Valid @ModelAttribute UpdateUserRequestDto updateUserRequestDto) {
        UpdateUserResponseDto updateUserResponseDto=userService.updateUser(updateUserRequestDto);
        return ResponseEntity.ok(updateUserResponseDto);
    }

    @PreAuthorize("hasAnyRole('OWNER','CHIEF_SUPERVISOR','PLANT_HEAD')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        String message = userService.deleteUser(id);
        return ResponseEntity.ok(message);
    }

    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/count")
//    @PreAuthorize()
    public ResponseEntity<Map<String, Object>> getRoleCounts() {
        List<RoleCountResponseDto> data = userService.getRoleCounts();

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", data);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('OWNER','CHIEF_SUPERVISOR','PLANT_HEAD')")
    @GetMapping("/role/{role}")
    public ResponseEntity<Page<EmpByRoleAndFactoryResDto>> getUsersByRole(
            @PathVariable String role,
            @RequestParam(required = false) Long factoryId,
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "0") @Min(value = 0, message = "Page number cannot be negative") int page,
            @RequestParam(defaultValue = "5") @Min(value = 1, message = "Size must be at least 1") int size) {

        Page<EmpByRoleAndFactoryResDto> response =
                userService.getEmpByRoleAndFactory(role, factoryId, search, page, size);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/unassigned-planthead")
    public ResponseEntity<List<UnassignedPlantHeadResponseDto>> getUnassignedPlantHeads() {
        List<UnassignedPlantHeadResponseDto> data = userService.getUnassignedPlantHeads();

        return ResponseEntity.ok(data);
    }
    @PreAuthorize("hasAnyRole('OWNER','PLANT_HEAD')")
    @GetMapping("/unassigned-chief-supervisor")
    public ResponseEntity<List<UnassignedPlantHeadResponseDto>> getUnassignedchiefsupervisor() {
        List<UnassignedPlantHeadResponseDto> data = userService.getUnassignedchiefsupervisor();

        return ResponseEntity.ok(data);
    }
    @PreAuthorize("hasAnyRole('OWNER','CHIEF_SUPERVISOR','PLANT_HEAD','WORKER','CENTRAL_OFFICER','DISTRIBUTOR','CUSTOMER')")
    @GetMapping("/profile")
    public ResponseEntity<UserProfileResDto> getProfile(@RequestParam Long id) {
        return ResponseEntity.ok(userService.getProfile(id));
    }

    @PreAuthorize("hasAnyRole('OWNER','CENTRAL_OFFICER')")
    @GetMapping("/distributors")
    public ResponseEntity<Page<DistributorResDto>> getAllDistributors(
            @RequestParam(defaultValue = "0") @Min(value = 0, message = "Page number cannot be negative")int page,
            @RequestParam(defaultValue = "10")  @Min(value = 1, message = "Size must be at least 1") int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<DistributorResDto> data = userService.getAllDistributors(pageable);


        return ResponseEntity.ok(data);
    }

    @PreAuthorize("hasAnyRole('OWNER','CHIEF_SUPERVISOR','PLANT_HEAD','WORKER','CENTRAL_OFFICER','DISTRIBUTOR','CUSTOMER')")
    @PutMapping(value  = "/profile/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserProfileResDto> updateProfile(
            @PathVariable Long id,
            @ModelAttribute UpdateProfileReqDto req) {

        return ResponseEntity.ok(userService.updateProfile(id, req));
    }












}
