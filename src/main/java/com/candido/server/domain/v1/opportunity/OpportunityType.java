package com.candido.server.domain.v1.opportunity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "opportunity_type")
public class OpportunityType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "opportunity_type_id")
    private Long opportunityTypeId;

    @Column(name = "key", unique = true, nullable = false)
    private String key;

    @Column(name = "label")
    private String label;
}


