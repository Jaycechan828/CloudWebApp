package com.example.csye6225assignment02.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -6103902533679096106L;

    private String submission_url;
}
