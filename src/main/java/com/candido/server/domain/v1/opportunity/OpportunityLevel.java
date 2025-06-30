package com.candido.server.domain.v1.opportunity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "opportunity_level", schema = "candido")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpportunityLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "opportunity_level_id")
    private Long opportunityLevelId;

    @Column(name = "key", unique = true, nullable = false)
    private String key;

    @Column(name = "label", nullable = false)
    private String label;
}

