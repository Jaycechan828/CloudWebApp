package com.example.csye6225assignment02.service;


import com.example.csye6225assignment02.entity.Assignment;
import com.example.csye6225assignment02.entity.User;
import com.example.csye6225assignment02.repository.AssignmentRepository;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.expression.spel.ast.Assign;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    public Assignment addAssignment(Assignment assignment, User user) {
        int points = assignment.getPoints();
        assignment.setUser(user);
        if (points >= 1 && points <= 10) {
            return assignmentRepository.save(assignment);
        } else {
            return null;
        }
    }

    public List<Assignment> getAllAssignment() {

        return assignmentRepository.findAll();
    }

    public Assignment getAssignmentDetails(String id) {
//        Assignment res = assignmentRepository.findById(id);

        return assignmentRepository.findById(id).orElse(null);
    }

    public void deleteAssignment(String id) {
        assignmentRepository.deleteById(id);
    }


    public void updateAssignment(Assignment assignment, String id) {
        Assignment foundAssignment = assignmentRepository.findById(id).orElse(null);

        foundAssignment.setName(assignment.getName());
        foundAssignment.setPoints(assignment.getPoints());
        foundAssignment.setNum_of_attemps(assignment.getNum_of_attemps());
        foundAssignment.setDeadline(assignment.getDeadline());
        foundAssignment.setAssignment_updated(LocalDateTime.now().toString());
        assignmentRepository.save(foundAssignment);

    }
}
