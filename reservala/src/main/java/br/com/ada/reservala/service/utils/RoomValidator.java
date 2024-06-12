package br.com.ada.reservala.service.utils;

import br.com.ada.reservala.domain.Room;
import br.com.ada.reservala.utils.Notification;
import br.com.ada.reservala.utils.Validator;

public class RoomValidator implements Validator<Room> {

    @Override
    public boolean isNull(Room room) {
        return room == null;
    }

    @Override
    public Notification validate(Room room) {
        Notification notification = new Notification();
        if(isNull(room)){
            notification.addError("Room is null!");
            return notification;
        }
        if(hasNullAttributes(room, notification).hasErrors())
            return notification;
        if(room.getRoomNumber() <= 0)
            notification.addError("Room number negative or equals zero!");
        if(room.getPrice() <= 0)
            notification.addError("Room price negative or equals zero!");
        if(room.getType().isBlank())
            notification.addError("Room type is blank!");
        return notification;
    }

    private Notification hasNullAttributes(Room room, Notification notification) {
        if (room.getType() == null)
            notification.addError("Room type is null!");
        if (room.getAvailable() == null)
            notification.addError("Room available is null!");
        return notification;
    }
}
