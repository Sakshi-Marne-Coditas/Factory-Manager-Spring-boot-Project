package com.FactoryManager.Controller;

import com.FactoryManager.DTO.AddBayReqDto;
import com.FactoryManager.DTO.AddBayResDto;
import com.FactoryManager.Service.BayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bay")
public class BayController {
    @Autowired
    BayService bayService;

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/newbay")
    public ResponseEntity<AddBayResDto> addBay(@RequestBody AddBayReqDto addBayReqDto){
        AddBayResDto addBayResDto= bayService.addBay(addBayReqDto);
        return ResponseEntity.ok(addBayResDto);
    }
}
