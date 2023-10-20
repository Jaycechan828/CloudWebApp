package com.example.csye6225assignment02.controller;

import com.example.csye6225assignment02.entity.Assignment;
import com.example.csye6225assignment02.entity.User;
import com.example.csye6225assignment02.repository.AssignmentRepository;
import com.example.csye6225assignment02.repository.UserRepository;
import com.example.csye6225assignment02.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
import java.net.http.HttpResponse;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
public class MainController {

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/v1/assignments")
    public ResponseEntity<List<Assignment>> getAssignments(){

        return ResponseEntity.ok(assignmentService.getAllAssignment());
    }

    @PostMapping("/v1/assignments")
    public ResponseEntity<Assignment> createAssignment(@RequestBody Assignment assignment, Principal principal) {

//        Optional<User> user = userRepository.findByEmail(principal.getName());
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

            Assignment savedAssignment = assignmentService.addAssignment(assignment, user);
            if (savedAssignment != null) {
                return new ResponseEntity<>(savedAssignment, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

    @GetMapping("/v1/assignments/{id}")
    public  Assignment getAssignmentsDetails(@PathVariable String id){

        return assignmentService.getAssignmentDetails(id);
}


    @DeleteMapping("/v1/assignments/{id}")
    public ResponseEntity<HttpResponse> deleteAssignments(@PathVariable String id, Principal principal){
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Assignment assignmentDetails = assignmentService.getAssignmentDetails(id);
        if (assignmentDetails == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Assignment not found");
        }
        if (!user.getId().equals(assignmentDetails.getUser().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        assignmentService.deleteAssignment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/v1/assignments/{id}", method = RequestMethod.PUT)
    public ResponseEntity<HttpResponse> updateAssignment(@PathVariable String id,
                                                         Principal principal,
                                                         @RequestBody Assignment assignment){
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Assignment assignmentDetails = assignmentService.getAssignmentDetails(id);
        if (assignmentDetails == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Assignment not found");
        }
        if (!user.getId().equals(assignmentDetails.getUser().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        assignmentService.updateAssignment(assignment, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
