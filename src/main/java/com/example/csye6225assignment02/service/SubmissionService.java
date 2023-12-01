package com.example.csye6225assignment02.service;

import com.example.csye6225assignment02.entity.Assignment;
import com.example.csye6225assignment02.entity.Submission;
import com.example.csye6225assignment02.entity.User;
import com.example.csye6225assignment02.repository.AssignmentRepository;
import com.example.csye6225assignment02.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Transactional
@Service
public class SubmissionService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private SubmissionRepository submissionRepository;

    public boolean addSubmission(Submission submission, User user, Assignment assignment){

        String assignment_id = assignment.getId();
        //如果有submission，且和assignment_id 相同，则覆盖
        List<Submission> foundSubmissions = submissionRepository.findByAssignment_id(assignment_id);

        // 将字符串格式的deadline转换为LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        LocalDateTime deadline = LocalDateTime.parse(assignment.getDeadline(), formatter);
        LocalDateTime now = LocalDateTime.now();

        // 检查是否超过截止日期
        if (now.isAfter(deadline)) {
            return false; // 提交已超过截止日期
        }

        int attempts = assignment.getNum_of_attemps();
        if (foundSubmissions != null && attempts > 0 ){

            Submission latestSubmission = foundSubmissions.get(foundSubmissions.size()-1);
            latestSubmission.setSubmission_updated(LocalDateTime.now().toString());
            latestSubmission.setSubmission_url(submission.getSubmission_url());
            submissionRepository.save(latestSubmission);
            assignment.setNum_of_attemps(attempts - 1);
            assignmentRepository.save(assignment);
            return true;
        }else {
            return false;
        }
    }

}
