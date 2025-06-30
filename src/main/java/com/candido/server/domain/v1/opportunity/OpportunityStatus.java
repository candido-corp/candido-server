package com.candido.server.domain.v1.opportunity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "opportunity_status", schema = "candido")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpportunityStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "opportunity_status_id")
    private Long opportunityStatusId;

    @Column(name = "key", unique = true, nullable = false)
    private String key;

    @Column(name = "label")
    private String label;
}

