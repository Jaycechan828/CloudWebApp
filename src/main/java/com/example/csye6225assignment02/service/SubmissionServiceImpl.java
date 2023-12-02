package com.example.csye6225assignment02.service;

import com.example.csye6225assignment02.entity.Assignment;
import com.example.csye6225assignment02.entity.Submission;
import com.example.csye6225assignment02.entity.SubmissionDTO;
import com.example.csye6225assignment02.entity.User;
import com.example.csye6225assignment02.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class SubmissionServiceImpl implements SubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;

    @Override
    public Submission createSubmission(String id, SubmissionDTO submissionDTO, Assignment assignment, User user) {
        String submissionUrl = submissionDTO.getSubmission_url();
        Submission submission = new Submission();
        submission.setId(UUID.randomUUID().toString());
        submission.setSubmission_url(submissionUrl);
        submission.setSubmission_date(LocalDateTime.now().toString());
        submission.setSubmission_updated(LocalDateTime.now().toString());
        submission.setAssignment(assignment);
        submission.setUser(user);
        return submissionRepository.save(submission);

    }
}