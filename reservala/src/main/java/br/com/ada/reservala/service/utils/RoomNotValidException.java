package br.com.ada.reservala.service.utils;

public class RoomNotValidException extends RuntimeException{
    public RoomNotValidException(String message) {
        super(message);
    }
}
