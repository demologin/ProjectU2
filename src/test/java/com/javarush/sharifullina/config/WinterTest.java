package com.javarush.sharifullina.config;

import com.javarush.sharifullina.repository.UserRepository;
import com.javarush.sharifullina.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WinterTest {

    @BeforeEach
    void setUp() {
        Winter.beans.clear();
    }

    @Test
    void getBeanShouldCreateInstanceWithDependenciesAndCacheItAsSingleton() {
        UserService firstCall = Winter.getBean(UserService.class);
        UserService secondCall = Winter.getBean(UserService.class);
        UserRepository repoBean = Winter.getBean(UserRepository.class);

        assertNotNull(firstCall);
        assertSame(firstCall, secondCall, "Winter должен возвращать один и тот же экземпляр бина");
        assertNotNull(repoBean);
    }
}
