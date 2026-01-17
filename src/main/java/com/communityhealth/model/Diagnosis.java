
package com.communityhealth.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="diagnosis")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Diagnosis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Column(length = 2000)
    private String symptoms;

    @Column(length = 2000)
    private String diagnosisResult;

    private boolean valid;
}
