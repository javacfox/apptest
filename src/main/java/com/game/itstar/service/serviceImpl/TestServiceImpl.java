package com.game.itstar.service.serviceImpl;

import com.game.itstar.entity.Test;
import com.game.itstar.repository.TestRepository;
import com.game.itstar.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestRepository repository;

    @Override
    public Test getTestById(Integer id) {
        return repository.getOne(id);
    }

    @Override
    public void saveTest(Test test) {
        repository.save(test);
    }
}
