package com.FactoryManager.Service;

import com.FactoryManager.DTO.AddBayReqDto;
import com.FactoryManager.DTO.AddBayResDto;
import com.FactoryManager.Entity.Bay;
import com.FactoryManager.Entity.Factory;
import com.FactoryManager.Repository.BayRepository;
import com.FactoryManager.Repository.FactoryRepository;
import com.FactoryManager.exceptionHandling.ElementNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BayService {
    @Autowired
    BayRepository bayRepository;

    @Autowired
    FactoryRepository factoryRepository;

    public AddBayResDto addBay(AddBayReqDto addBayReqDto) {
        Factory factory= factoryRepository.findById(addBayReqDto.getFactory_id()).orElseThrow(() -> new ElementNotFoundException("Factory not found!"));

        Bay bay = new Bay();
        bay.setBay_name(addBayReqDto.getBay_Name());
        bay.setFactory(factory);

        AddBayResDto addBayResDto = new AddBayResDto();
        addBayResDto.setBay_id(bay.getBay_id());
        addBayResDto.setBay_Name(bay.getBay_name());
        addBayResDto.setFactory_name(bay.getFactory().getName());

        bayRepository.save(bay);
        return addBayResDto;
    }
}
