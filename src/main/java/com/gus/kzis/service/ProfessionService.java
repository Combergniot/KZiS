package com.gus.kzis.service;

import com.gus.kzis.model.Profession;
import org.springframework.stereotype.Service;
import com.gus.kzis.repository.ProfessionRepository;

import java.util.List;

@Service
public class ProfessionService {

    private final ProfessionRepository professionRepository;

    public ProfessionService(ProfessionRepository professionRepository) {
        this.professionRepository = professionRepository;
    }

    public Iterable<Profession> list() {
        return professionRepository.findAll();
    }

    public Profession save(Profession profession) {
        return professionRepository.save(profession);
    }

    public void save(List<Profession> professions) {
        professionRepository.save(professions);
    }
}
