package com.example.csye6225assignment02.healthcheck;

import ch.qos.logback.core.net.server.Client;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.SQLException;

@RestController
public class HealthCheckController {

    @Autowired
    private CheckDatabase checkDatabase;

    public HttpHeaders Headers(){
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache()
                .mustRevalidate().getHeaderValue());
        headers.setPragma("no-cache");
        headers.set("X-Content-Type-Options", "nosniff");

        return headers;
    }

    @RequestMapping(value = "healthz",
            method = RequestMethod.GET)
    public ResponseEntity<String> getRequest() {

        checkDatabase.checkConnect();

        HttpHeaders headers = Headers();

        if (checkDatabase.connectionStatus()){
            return new ResponseEntity<>("",headers,
                    HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("", headers,
                    HttpStatus.SERVICE_UNAVAILABLE);
        }

    }

    @RequestMapping(value = "/healthz",
            method = {RequestMethod.PUT, RequestMethod.POST,
                    RequestMethod.DELETE, RequestMethod.PATCH})
    public ResponseEntity<String> putRequest() {

        HttpHeaders headers = Headers();

        return new ResponseEntity<>("", headers,
                HttpStatus.METHOD_NOT_ALLOWED);
    }

}




