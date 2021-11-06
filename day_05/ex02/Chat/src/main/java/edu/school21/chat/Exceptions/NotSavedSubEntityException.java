package edu.school21.chat.Exceptions;

public class NotSavedSubEntityException extends  RuntimeException {
    public NotSavedSubEntityException(String msg) {
        super(msg);
    }
}
