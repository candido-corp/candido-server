package com.candido.server.domain.v1.opportunity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "opportunity_tag", schema = "candido")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpportunityTag {

    @EmbeddedId
    private OpportunityTagId id;

    @ManyToOne
    @MapsId("opportunityId")
    @JoinColumn(name = "fk_opportunity_id", nullable = false)
    private Opportunity opportunity;

    @ManyToOne
    @MapsId("tagId")
    @JoinColumn(name = "fk_tag_id", nullable = false)
    private Tag tag;
}

