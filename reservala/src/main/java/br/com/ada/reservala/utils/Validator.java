package br.com.ada.reservala.utils;

public interface Validator <T>{
    boolean isNull(T type);
    Notification validate(T type);
}
