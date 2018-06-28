package com.ipiecoles.java.mdd050.service;

import com.ipiecoles.java.mdd050.exception.EmployeException;
import com.ipiecoles.java.mdd050.model.Commercial;
import com.ipiecoles.java.mdd050.repository.CommercialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CommercialService {

    @Autowired
    private CommercialRepository commercialRepository;

    public Commercial findById(Long id){
        return commercialRepository.findOne(id);
    }

    public Commercial creerEmploye(Commercial e) {
        return commercialRepository.save(e);
    }

    public Commercial updateEmploye(Long id, Commercial employe) throws EmployeException {
        return commercialRepository.save(employe);
    }


}
