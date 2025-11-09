package com.candido.server.dto.v1.response.application;

import com.candido.server.domain.v1.application.Application;
import com.candido.server.domain.v1.application.ApplicationStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ResponseUserApplication {

    @JsonProperty("application_id")
    private int applicationId;

    @JsonProperty("status")
    private ApplicationStatus applicationStatus;

    @JsonProperty("filled_body")
    private String filledBody;

    @JsonProperty("progress")
    private int progress;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    @JsonProperty("deleted_at")
    private LocalDateTime deletedAt;

    @JsonProperty("sent_at")
    private LocalDateTime sentAt;

    public ResponseUserApplication(
            int applicationId,
            ApplicationStatus applicationStatus,
            String filledBody,
            int progress,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            LocalDateTime deletedAt,
            LocalDateTime sentAt
    ) {
        this.applicationId = applicationId;
        this.applicationStatus = applicationStatus;
        this.filledBody = filledBody;
        this.progress = progress;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.sentAt = sentAt;
    }

    public static ResponseUserApplication mapToResponseUserApplication(Application application) {
        return new ResponseUserApplication(
                application.getApplicationId(),
                application.getApplicationStatus(),
                application.getFilledBody(),
                application.getProgress(),
                application.getCreatedAt(),
                application.getUpdatedAt(),
                application.getDeletedAt(),
                application.getSentAt()
        );
    }
}
