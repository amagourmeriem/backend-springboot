package com.example.marsamaroc.mappers;
import com.example.marsamaroc.dao.entities.Status;

public class StatusUpdateRequest {
    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}