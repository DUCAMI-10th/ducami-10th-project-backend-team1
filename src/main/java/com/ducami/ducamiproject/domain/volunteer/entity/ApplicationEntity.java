package com.ducami.ducamiproject.domain.volunteer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "volunteerApplication")
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ApplicationEntity {
    @Id
    @Column(name = "application_id")
    private long id;

}
