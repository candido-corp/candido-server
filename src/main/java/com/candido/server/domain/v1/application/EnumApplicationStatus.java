package com.candido.server.domain.v1.application;

public enum EnumApplicationStatus {
    IN_PROGRESS(1),
    SUBMITTED(2),
    UNDER_REVIEW(3),
    COMPLETED(4),
    EXPIRED(5);

    private final int statusId;

    EnumApplicationStatus(int statusId) {
        this.statusId = statusId;
    }

    public int getStatusId() {
        return statusId;
    }
}
