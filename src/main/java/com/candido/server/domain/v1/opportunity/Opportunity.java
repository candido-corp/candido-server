package com.candido.server.domain.v1.opportunity;

import com.candido.server.domain.v1.account.Account;
import com.candido.server.domain.v1.geo.Territory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "opportunity")
public class Opportunity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "opportunity_id")
    private Long opportunityId;

    @ManyToOne
    @JoinColumn(name = "fk_account_id")
    private Account account;

    @Column(name = "display_name", length = 128)
    private String displayName;

    @Column(name = "url_code", length = 4)
    private String urlCode;

    @Column(name = "form_schema", columnDefinition = "jsonb")
    private String formSchema;

    @Column(name = "max_applicants")
    private Long maxApplicants;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "notes", length = 255)
    private String notes;

    @Column(name = "compensation_amount", precision = 10, scale = 2)
    private BigDecimal compensationAmount;

    @Column(name = "compensation_note", length = 255)
    private String compensationNote;

    @ManyToOne
    @JoinColumn(name = "fk_currency_id")
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "fk_territory_id")
    private Territory territory;

    @ManyToOne
    @JoinColumn(name = "fk_location_type_id")
    private LocationType locationType;

    @ManyToOne
    @JoinColumn(name = "fk_opportunity_type_id")
    private OpportunityType opportunityType;

    @ManyToOne
    @JoinColumn(name = "fk_opportunity_status_id")
    private OpportunityStatus opportunityStatus;

    @ManyToOne
    @JoinColumn(name = "fk_opportunity_level_id")
    private OpportunityLevel opportunityLevel;

    @Column(name = "start_date")
    private OffsetDateTime startDate;

    @Column(name = "end_date")
    private OffsetDateTime endDate;

    @Column(name = "feedback_publication_date")
    private OffsetDateTime feedbackPublicationDate;

    @Column(name = "feedback_expiration_date")
    private OffsetDateTime feedbackExpirationDate;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;
}