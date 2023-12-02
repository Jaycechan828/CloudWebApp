package com.example.csye6225assignment02.repository;

import com.example.csye6225assignment02.entity.Assignment;
import com.example.csye6225assignment02.entity.Submission;
import com.example.csye6225assignment02.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, String> {

    @Query("SELECT COUNT(s) FROM Submission s WHERE s.assignment.id = :id and s.user.email = :email")
    Integer countSubmissionsByIdAndEmail(@Param("id") String id, @Param("email") String email);


}