package com.example.csye6225assignment02.repository;

import com.example.csye6225assignment02.entity.Assignment;
import com.example.csye6225assignment02.entity.User;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, String> {

//    List<Assignment> findByUserId(String id);

////    @Transactional
////    void deleteByUserId(String id);
//    void deleteByUserId();

    @Override
    void deleteById(String id);



}
