package com.candido.server.domain.v1.opportunity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpportunityTagId implements Serializable {
    @Column(name = "fk_opportunity_id")
    private Long opportunityId;

    @Column(name = "fk_tag_id")
    private Long tagId;
}
