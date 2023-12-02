package com.example.csye6225assignment02.controller;

import com.example.csye6225assignment02.entity.Assignment;
import com.example.csye6225assignment02.entity.Submission;
import com.example.csye6225assignment02.entity.SubmissionDTO;
import com.example.csye6225assignment02.entity.User;
import com.example.csye6225assignment02.repository.AssignmentRepository;
import com.example.csye6225assignment02.repository.SubmissionRepository;
import com.example.csye6225assignment02.repository.UserRepository;
import com.example.csye6225assignment02.response.Response;
import com.example.csye6225assignment02.service.AssignmentService;
import com.example.csye6225assignment02.service.SnsService;
import com.example.csye6225assignment02.service.SubmissionService;
import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
import java.net.http.HttpResponse;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


@RestController
public class MainController {


    private static final StatsDClient statsd = new NonBlockingStatsDClient("my.prefix", "localhost", 8125);


    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubmissionService submissionService;
    @Autowired
    private SubmissionRepository submissionRepository;
    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private SnsService snsService;


    @GetMapping("/v1/assignments")
    public ResponseEntity<List<Assignment>> getAssignments() {

        long startTime = System.currentTimeMillis();

        ResponseEntity<List<Assignment>> response = ResponseEntity.ok(assignmentService.getAllAssignment());

        long duration = System.currentTimeMillis() - startTime;
        statsd.recordExecutionTime("assignments.get.duration", duration);
        statsd.incrementCounter("assignments.get.count");


        return response;
    }

    @PostMapping("/v1/assignments")
    public ResponseEntity<Assignment> createAssignment(@RequestBody Assignment assignment, Principal principal) {
        long startTime = System.currentTimeMillis();

//        Optional<User> user = userRepository.findByEmail(principal.getName());
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Assignment savedAssignment = assignmentService.addAssignment(assignment, user);
        if (savedAssignment != null) {

            long duration = System.currentTimeMillis() - startTime;
            statsd.recordExecutionTime("assignments.create.duration", duration);
            statsd.incrementCounter("assignments.create.count");

            return new ResponseEntity<>(savedAssignment, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/v1/assignments/{id}")
    public Assignment getAssignmentsDetails(@PathVariable String id) {

        long startTime = System.currentTimeMillis();
        long duration = System.currentTimeMillis() - startTime;
        statsd.recordExecutionTime("assignments.get.duration", duration);
        statsd.incrementCounter("assignments.get.count");

        return assignmentService.getAssignmentDetails(id);
    }


    @DeleteMapping("/v1/assignments/{id}")
    public ResponseEntity<HttpResponse> deleteAssignments(@PathVariable String id, Principal principal) {
        long startTime = System.currentTimeMillis();

        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Assignment assignmentDetails = assignmentService.getAssignmentDetails(id);
        if (assignmentDetails == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Assignment not found");
        }
        if (!user.getId().equals(assignmentDetails.getUser().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        assignmentService.deleteAssignment(id);

        long duration = System.currentTimeMillis() - startTime;
        statsd.recordExecutionTime("assignments.delete.duration", duration);
        statsd.incrementCounter("assignments.delete.count");

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/v1/assignments/{id}", method = RequestMethod.PUT)
    public ResponseEntity<HttpResponse> updateAssignment(@PathVariable String id,
                                                         Principal principal,
                                                         @RequestBody Assignment assignment) {

        long startTime = System.currentTimeMillis();

        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Assignment assignmentDetails = assignmentService.getAssignmentDetails(id);
        if (assignmentDetails == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Assignment not found");
        }
        if (!user.getId().equals(assignmentDetails.getUser().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        assignmentService.updateAssignment(assignment, id);
        long duration = System.currentTimeMillis() - startTime;
        statsd.recordExecutionTime("assignments.update.duration", duration);
        statsd.incrementCounter("assignments.update.count");

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/v1/assignments/{id}/submission", method = RequestMethod.POST)
    public Response<Submission> createSubmission(@PathVariable String id,

                                                 @RequestBody SubmissionDTO submissionDTO,
                                                 Principal principal) {
        Assignment assignment = assignmentRepository.findById(id).orElse(null);

        if (assignment == null) {
            return Response.notFound("Assignment not found");
        }

        LocalDateTime deadline = LocalDateTime.parse(assignment.getDeadline(), DateTimeFormatter.ISO_DATE_TIME);
        if (LocalDateTime.now().isAfter(deadline)) {
            return Response.forbidden("The deadline has passed");
        }

        User submitEmail = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        String email = submitEmail.getEmail();

        int countSubmission = submissionRepository.countSubmissionsByIdAndEmail(id, email);
        if (countSubmission >= assignment.getNum_of_attemps()) {
            return Response.forbidden("You have exceeded the number of attempts");
        }

        Submission submission = submissionService.createSubmission(id, submissionDTO, assignment, submitEmail);

        snsService.publishToSns(submissionDTO.getSubmission_url(), principal.getName());

        return Response.success(submission);


    }
}
