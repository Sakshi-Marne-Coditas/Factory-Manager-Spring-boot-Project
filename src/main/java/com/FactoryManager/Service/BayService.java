package com.FactoryManager.Service;

import com.FactoryManager.DTO.AddBayReqDto;
import com.FactoryManager.DTO.AddBayResDto;
import com.FactoryManager.Entity.Bay;
import com.FactoryManager.Entity.Factory;
import com.FactoryManager.Repository.BayRepository;
import com.FactoryManager.Repository.FactoryRepository;
import com.FactoryManager.exceptionHandling.ElementNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BayService {

    private final BayRepository bayRepository;


    private final FactoryRepository factoryRepository;

    @Transactional
    public AddBayResDto addBay(AddBayReqDto addBayReqDto) {

        Long factoryId = addBayReqDto.getFactory_id();

        if (!factoryRepository.existsById(factoryId)) {
            throw new ElementNotFoundException("Factory not found with id: " + factoryId);
        }

        Factory factory = factoryRepository.findById(factoryId).get();

        Bay bay = new Bay();
        bay.setBay_name(addBayReqDto.getBay_Name());
        bay.setFactory(factory);

        bayRepository.save(bay);

        AddBayResDto res = new AddBayResDto();
        res.setBay_id(bay.getBay_id());
        res.setBay_Name(bay.getBay_name());
        res.setFactory_name(factory.getName());

        return res;
    }
}
