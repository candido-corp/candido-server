package com.candido.server.dto.v1.response.application;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ResponseUserApplication {

    @JsonProperty("application_id")
    private int applicationId;

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
            String filledBody,
            int progress,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            LocalDateTime deletedAt,
            LocalDateTime sentAt
    ) {
        this.applicationId = applicationId;
        this.filledBody = filledBody;
        this.progress = progress;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.sentAt = sentAt;
    }
}
