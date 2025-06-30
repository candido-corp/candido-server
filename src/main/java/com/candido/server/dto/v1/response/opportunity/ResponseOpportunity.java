package com.candido.server.dto.v1.response.opportunity;

import com.candido.server.domain.v1.opportunity.Opportunity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
public class ResponseOpportunity {

    @JsonProperty("opportunity_id")
    private Long opportunityId;

    @JsonProperty("account_id")
    private Long accountId;

    @JsonProperty("display_name")
    private String displayName;

    @JsonProperty("notes")
    private String notes;

    @JsonProperty("description")
    private String description;

    @JsonProperty("url_code")
    private String urlCode;

    @JsonProperty("max_applicants")
    private Integer maxApplicants;

    @JsonProperty("form_schema")
    private String formSchema;

    @JsonProperty("compensation_amount")
    private BigDecimal compensationAmount;

    @JsonProperty("compensation_note")
    private String compensationNote;

    @JsonProperty("currency_code")
    private String currencyCode;

    @JsonProperty("location")
    private String location;

    @JsonProperty("location_type_key")
    private String locationTypeKey;

    @JsonProperty("opportunity_type_key")
    private String opportunityTypeKey;

    @JsonProperty("opportunity_status_key")
    private String opportunityStatusKey;

    @JsonProperty("opportunity_level_key")
    private String opportunityLevelKey;

    @JsonProperty("start_date")
    private OffsetDateTime startDate;

    @JsonProperty("end_date")
    private OffsetDateTime endDate;

    @JsonProperty("feedback_publication_date")
    private OffsetDateTime feedbackPublicationDate;

    @JsonProperty("feedback_expiration_date")
    private OffsetDateTime feedbackExpirationDate;

    @JsonProperty("created_at")
    private OffsetDateTime createdAt;

    @JsonProperty("updated_at")
    private OffsetDateTime updatedAt;

    @JsonProperty("deleted_at")
    private OffsetDateTime deletedAt;

    public ResponseOpportunity(
            Long opportunityId,
            Long accountId,
            String displayName,
            String notes,
            Integer maxApplicants,
            String formSchema,
            String urlCode,
            OffsetDateTime startDate,
            OffsetDateTime endDate,
            OffsetDateTime createdAt,
            OffsetDateTime updatedAt,
            OffsetDateTime deletedAt,
            OffsetDateTime feedbackPublicationDate,
            OffsetDateTime feedbackExpirationDate,
            String description,
            BigDecimal compensationAmount,
            String compensationNote,
            String currencyCode,
            String location,
            String locationTypeKey,
            String opportunityTypeKey,
            String opportunityStatusKey,
            String opportunityLevelKey
    ) {
        this.opportunityId = opportunityId;
        this.accountId = accountId;
        this.displayName = displayName;
        this.notes = notes;
        this.maxApplicants = maxApplicants;
        this.formSchema = formSchema;
        this.urlCode = urlCode;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.feedbackPublicationDate = feedbackPublicationDate;
        this.feedbackExpirationDate = feedbackExpirationDate;
        this.description = description;
        this.compensationAmount = compensationAmount;
        this.compensationNote = compensationNote;
        this.currencyCode = currencyCode;
        this.location = location;
        this.locationTypeKey = locationTypeKey;
        this.opportunityTypeKey = opportunityTypeKey;
        this.opportunityStatusKey = opportunityStatusKey;
        this.opportunityLevelKey = opportunityLevelKey;
    }

    public static ResponseOpportunity mapToResponseOpportunity(Opportunity opportunity) {
        return new ResponseOpportunity(
                opportunity.getOpportunityId(),
                opportunity.getAccount() != null ? opportunity.getAccount().getId() : null,
                opportunity.getDisplayName(),
                opportunity.getNotes(),
                opportunity.getMaxApplicants(),
                opportunity.getFormSchema(),
                opportunity.getUrlCode(),
                opportunity.getStartDate(),
                opportunity.getEndDate(),
                opportunity.getCreatedAt(),
                opportunity.getUpdatedAt(),
                opportunity.getDeletedAt(),
                opportunity.getFeedbackPublicationDate(),
                opportunity.getFeedbackExpirationDate(),
                opportunity.getDescription(),
                opportunity.getCompensationAmount(),
                opportunity.getCompensationNote(),
                opportunity.getCurrency() != null ? opportunity.getCurrency().getCode() : null,
                opportunity.getLocation(),
                opportunity.getLocationType() != null ? opportunity.getLocationType().getKey() : null,
                opportunity.getOpportunityType() != null ? opportunity.getOpportunityType().getKey() : null,
                opportunity.getOpportunityStatus() != null ? opportunity.getOpportunityStatus().getKey() : null,
                opportunity.getOpportunityLevel() != null ? opportunity.getOpportunityLevel().getKey() : null
        );
    }
}
