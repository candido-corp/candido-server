package com.candido.server.dto.v1.response.application;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ResponseUserApplication {
    private int applicationId;
    private String filledBody;
    private int progress;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
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
