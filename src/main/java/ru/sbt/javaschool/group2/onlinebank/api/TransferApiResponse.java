package ru.sbt.javaschool.group2.onlinebank.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransferApiResponse {

    private int status;
    private String error;
    private String timestamp;
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private TransferApiResponse() {}

    public TransferApiResponse(int status, String error, String timestamp, String message) {
        setStatus(status);
        setError(error);
        setTimestamp(timestamp);
        setMessage(message);
    }
}
