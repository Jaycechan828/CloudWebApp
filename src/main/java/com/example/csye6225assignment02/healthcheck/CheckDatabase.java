package com.example.csye6225assignment02.healthcheck;

import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Service
public class CheckDatabase {

    boolean isConnected = false;



    @Autowired
    private DataSource dataSource;

    public void checkConnect(){

        try {
            Connection connection = dataSource.getConnection();
            if ( connection.isValid(1)){
                isConnected = true;
            }
            connection.close();

        } catch (SQLException e) {
            isConnected = false;
        }
    }
    public boolean connectionStatus(){
        return isConnected;
    }



}
