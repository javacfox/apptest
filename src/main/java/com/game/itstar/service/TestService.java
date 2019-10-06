package com.game.itstar.service;

import com.game.itstar.entity.Test;

public interface TestService {
    Test getTestById(Integer id);
    void saveTest(Test test);
}
