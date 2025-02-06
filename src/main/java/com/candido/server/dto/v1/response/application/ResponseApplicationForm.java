package com.candido.server.dto.v1.response.application;

import com.candido.server.domain.v1.application.ApplicationForm;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseApplicationForm {

    @JsonProperty("application_form_id")
    private int applicationFormId;

    @JsonProperty("display_name")
    private String displayName;

    @JsonProperty("notes")
    private String notes;

    @JsonProperty("url_code")
    private String urlCode;

    @JsonProperty("start_date")
    private LocalDateTime startDate;

    @JsonProperty("end_date")
    private LocalDateTime endDate;

    public ResponseApplicationForm(
            int applicationFormId,
            String displayName,
            String notes,
            String urlCode,
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {
        this.applicationFormId = applicationFormId;
        this.displayName = displayName;
        this.notes = notes;
        this.urlCode = urlCode;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static ResponseApplicationForm mapToResponseApplicationForm(ApplicationForm applicationForm) {
        return new ResponseApplicationForm(
                applicationForm.getApplicationFormId(),
                applicationForm.getDisplayName(),
                applicationForm.getNotes(),
                applicationForm.getUrlCode(),
                applicationForm.getStartDate(),
                applicationForm.getEndDate()
        );
    }

}
