package com.magisterka.interview;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "interview")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InterviewEntity {
    @Id
    @GeneratedValue
    private Long id;

    private Long patientId;

    private Double weight;

    private Double height;

    private String bloodType;
}
