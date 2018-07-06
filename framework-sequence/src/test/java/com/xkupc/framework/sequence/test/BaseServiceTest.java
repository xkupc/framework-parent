package com.xkupc.framework.sequence.test;

import com.xkupc.framework.sequence.factory.IdServiceFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xk
 * @create 2018-07-06 15:13
 * @description
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class BaseServiceTest {
    @Autowired
    IdServiceFactory idServiceFactory;

    @Test
    public void getId() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        while (true) {
            executorService.execute(new Runnable() {

                public void run() {
                    System.err.println(idServiceFactory.getId("order"));
                }
            });
        }
    }
}
