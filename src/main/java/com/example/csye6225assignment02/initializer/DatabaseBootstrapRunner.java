package com.example.csye6225assignment02.initializer;


import com.example.csye6225assignment02.entity.User;
import com.example.csye6225assignment02.repository.UserRepository;
import com.example.csye6225assignment02.service.UserService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Component
public class DatabaseBootstrapRunner implements CommandLineRunner{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Value("${csv.file.path}")
    private String csvPath;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {  // Check if the database is empty
            Resource resource = new DefaultResourceLoader().getResource(csvPath);
            try (Reader reader = new InputStreamReader(resource.getInputStream())) {
                CsvToBean<User> csvToBean = new CsvToBeanBuilder<User>(reader)
                        .withType(User.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();
                List<User> users = csvToBean.parse();

                for (User user : users) {  // Loop through each user
                    userService.saveUser(user);  // Save user using UserService which will hash the password
                }
            }
        }
    }
}