package br.com.ada.reservala.service;

import br.com.ada.reservala.domain.Room;
import br.com.ada.reservala.factory.InstanceFactory;
import br.com.ada.reservala.service.utils.RoomNotValidException;
import br.com.ada.reservala.utils.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ServiceUnitTest {

    private final RoomService service = InstanceFactory.getServiceMock();
    private Room newRoom = InstanceFactory.getNewRoom();

    @Test
    public void createRoom_ValidRoom_SuccessfulCreateTest(){
        Room roomResponse = service.createRoom(newRoom);

        Assertions.assertEquals(
                newRoom,
                roomResponse);
    }

    @Test
    public void createRoom_InvalidRoom_SuccessfulThrowExceptionTest(){
        newRoom.setType("");
        newRoom.setRoomNumber(-1);
        newRoom.setPrice(0);

        Assertions.assertThrows(
                RoomNotValidException.class,
                () -> service.createRoom(newRoom));
    }

    @Test
    public void createRoom_NullRoom_SuccessfulThrowExceptionTest(){
        newRoom = null;

        Assertions.assertThrows(
                RoomNotValidException.class,
                () -> service.createRoom(newRoom));
    }

    @Test
    public void readRoom_EmptyDatabase_SuccessfulReadTest(){
        List<Room> roomsListExpected = new ArrayList<>();
        List<Room> roomsListResponse = service.readAllRooms();

        Assertions.assertEquals(
                roomsListExpected,
                roomsListResponse);
    }

    @Test
    public void updateRoom_ValidRoomButEmptyDatabase_SuccessfulThrowException(){
        Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> service.updateRoom(newRoom));
    }

    @Test
    public void updateRoom_InvalidRoom_SuccessfulThrowException(){
        newRoom.setRoomNumber(0);
        newRoom.setPrice(0);
        Assertions.assertThrows(
                RoomNotValidException.class,
                () -> service.updateRoom(newRoom));
    }

    @Test
    public void deleteRoom_EmptyDatabase_SuccessfulThrowException(){
        Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> service.deleteRoom(newRoom.getRoomNumber()));
    }

    @Test
    public void getOccupation_EmptyDatabase_SuccessfulZeroTest(){
        Assertions.assertEquals(0, service.getOccupation());
    }

    @Test
    public void getRevenue_EmptyDatabase_SuccessfulZeroTest(){
        Assertions.assertEquals(0, service.getRevenue());
    }

}
