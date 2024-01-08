package com.strandhvilliam.driveravailability.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DriverEntity {

  @Id()
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  @Column(name = "name")
  private String name;
  @Column(name = "email")
  private String email;
  @Column(name = "phone")
  private String phone;

  @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL)
  private List<JobEntity> jobs;


}
