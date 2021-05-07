package com.ev.trading;

import com.ev.trading.entity.User;
import com.ev.trading.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class TradingApplicationTests {

    @Autowired
    UserService userService;

    @Test
    void contextLoads() {
        List<User> list = userService.list();
        for (User user : list) {
            System.out.println(user);
        }
    }

}
