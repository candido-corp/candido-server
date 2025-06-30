package com.candido.server.domain.v1.opportunity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "xref_account_opportunity_saved")
public class XrefAccountOpportunitySaved {

    @EmbeddedId
    private XrefAccountOpportunitySavedId id;

    @OneToOne
    @JoinColumn(name = "fk_opportunity_id", insertable = false, updatable = false)
    private Opportunity opportunity;
}

