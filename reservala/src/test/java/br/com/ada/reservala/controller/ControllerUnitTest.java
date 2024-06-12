package br.com.ada.reservala.controller;

import br.com.ada.reservala.controller.dto.RoomDto;
import br.com.ada.reservala.factory.InstanceFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;

public class ControllerUnitTest {
    private final RoomController controller = InstanceFactory.getControllerMock();

    private final RoomDto roomDtoEmpty = RoomDto.builder()
            .roomNumber(1)
            .type("Test Room")
            .price(50)
            .available(true)
            .build();

    private final RoomDto roomDtoInvalid = RoomDto.builder()
            .roomNumber(-1)
            .type("")
            .price(-50)
            .available(false)
            .build();



    @Test
    public void createRoom_ValidRoom_SuccessfulCreateTest(){
        ResponseEntity<RoomDto> response = controller.createRoom(roomDtoEmpty);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(roomDtoEmpty, response.getBody());
    }

    @Test
    public void createRoom_InvalidRoom_SuccessfulBadRequestReturnTest(){
        ResponseEntity<RoomDto> response = controller.createRoom(roomDtoInvalid);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void createRoom_NullRoom_SuccessfulBadRequestReturnTest(){
        ResponseEntity<RoomDto> response = controller.createRoom(null);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void readAllRooms_EmptyDatabase_SuccessfulReadTest(){
        ResponseEntity<List<RoomDto>> response = controller.readAllRooms();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(new ArrayList<>(), response.getBody());
    }

    @Test
    public void updateRoom_InvalidRoom_SuccessfulBadRequestReturnTest(){
        ResponseEntity<RoomDto> response = controller.updateRoom(roomDtoInvalid);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void updateRoom_EmptyDatabase_SuccessfulNotFoundReturnTest(){
        ResponseEntity<RoomDto> response = controller.updateRoom(roomDtoEmpty);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void updateRoom_NullRoom_SuccessfulBadRequestReturnTest(){
        ResponseEntity<RoomDto> response = controller.updateRoom(null);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void deleteRoom_EmptyDatabase_SuccessfulNotFoundReturnTest(){
        ResponseEntity<Void> response = controller.deleteRoom(roomDtoEmpty.getRoomNumber());

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getOccupation_EmptyDatabase_SuccessfulZeroReturnTest(){
        ResponseEntity<Double> response = controller.getOccupation();

        Assertions.assertEquals(0, response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getRevenue_EmptyDatabase_SuccessfulZeroReturnTest(){
        ResponseEntity<Double> response = controller.getRevenue();

        Assertions.assertEquals(0, response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
