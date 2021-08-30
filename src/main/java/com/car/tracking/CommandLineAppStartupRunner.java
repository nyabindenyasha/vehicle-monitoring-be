package com.car.tracking;

import com.car.tracking.users.context.UserAccountCreateContext;
import com.car.tracking.users.context.UserAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author Nyabinde Nyasha
 * @created 12/27/2020
 * @project procurement-system
 */

@Slf4j
@Component
@AllArgsConstructor
public class CommandLineAppStartupRunner implements CommandLineRunner {

    private final UserAccountService userAccountService;

    @Override
    public void run(String... args) throws Exception {

        try {

            val adminUserExists = userAccountService.existsByUsername("admin");

            if (!adminUserExists) {

                val adminUserCreateContext = new UserAccountCreateContext();
                adminUserCreateContext.setFirstName("Admin");
                adminUserCreateContext.setLastName("Admin");
                adminUserCreateContext.setPassword("admin@123");
                adminUserCreateContext.setUsername("admin");
                adminUserCreateContext.setAdmin(true);
                adminUserCreateContext.setEmail("nyabindenyasha@gmail.com");
                userAccountService.create(adminUserCreateContext);
                log.info("User created");
            } else {
                log.info("User already exists");
            }

        } catch (Exception e) {
            log.info("Exception @ CommandLineRunner: " + e.getMessage());
        }
    }
}
