package ru.sbt.javaschool.group2.onlinebank.error;

public class OnlineBankException extends RuntimeException {

    private String clientMessage;

    public String getClientMessage() {
        return clientMessage;
    }

    private void setClientMessage(String clientMessage) {
        this.clientMessage = clientMessage;
    }

    public OnlineBankException(String message, String clientMessage) {
        super(message);
        setClientMessage(clientMessage);
    }

    public OnlineBankException(String message) {
        this(message, message);
    }

    public OnlineBankException(Throwable cause) {
        super(cause);
        setClientMessage("Внутренняя ошибка");
    }

    public OnlineBankException(String message, String clientMessage, Throwable cause) {
        super(message, cause);
        setClientMessage(clientMessage);
    }

    public OnlineBankException(String message, Throwable cause) {
        this(message, message, cause);
    }
}
