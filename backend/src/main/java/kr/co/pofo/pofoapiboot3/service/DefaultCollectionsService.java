package kr.co.pofo.pofoapiboot3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.pofo.pofoapiboot3.entity.Portfolio;
import kr.co.pofo.pofoapiboot3.repository.CollectionsRepository;

@Service
public class DefaultCollectionsService implements CollectionsService{
    
    @Autowired
    private CollectionsRepository repository;

    @Override
    public List<Portfolio> getByUserId(int id) {
        return repository.findByUserId(id);
    }
}
