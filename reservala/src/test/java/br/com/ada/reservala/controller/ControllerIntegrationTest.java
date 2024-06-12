package br.com.ada.reservala.controller;

import br.com.ada.reservala.controller.dto.RoomDto;
import br.com.ada.reservala.factory.InstanceFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Objects;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ControllerIntegrationTest {
    @Autowired
    public RoomController controller;

    private final RoomDto roomDtoEmpty = RoomDto.builder()
            .roomNumber(1)
            .type("Test Room")
            .price(50)
            .available(true)
            .build();

    private final RoomDto roomDtoOccupied = RoomDto.builder()
            .roomNumber(2)
            .type("Test Room")
            .price(60)
            .available(false)
            .build();

    private final RoomDto roomDtoInvalid = RoomDto.builder()
            .roomNumber(-1)
            .type("")
            .price(-50)
            .available(false)
            .build();


    @BeforeEach
    public void populateDatabase(){
        controller.createRoom(roomDtoEmpty);
        controller.createRoom(roomDtoOccupied);
    }

    @AfterEach
    public void clearDatabase(){
        controller.getRoomService().getRoomRepository().deleteAllRooms();
    }

    @Test
    public void createRoom_ValidRoom_SuccessfulCreateTest(){
        RoomDto roomDto = InstanceFactory.getNewRoomDto();

        ResponseEntity<RoomDto> response = controller.createRoom(roomDto);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(roomDto, response.getBody());
    }

    @Test
    public void createRoom_AlreadyExistentRoom_SuccessfulConflictReturnTest(){
        ResponseEntity<RoomDto> response = controller.createRoom(roomDtoEmpty);

        Assertions.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void createRoom_InvalidRoom_SuccessfulBadRequestReturnTest(){
        ResponseEntity<RoomDto> response = controller.createRoom(roomDtoInvalid);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void readAllRooms_PopulatedDatabase_SuccessfulRead(){
        List<RoomDto> roomsDtoExpectedList = List.of(roomDtoEmpty, roomDtoOccupied);

        ResponseEntity<List<RoomDto>> response = controller.readAllRooms();

        Assertions.assertTrue(
                Objects.requireNonNull(response.getBody()).containsAll(roomsDtoExpectedList));
    }

    @Test
    public void updateRoom_ValidRoom_SuccessfulUpdateTest(){
        roomDtoEmpty.setPrice(40);
        roomDtoEmpty.setType("Updated Room");

        ResponseEntity<RoomDto> response = controller.updateRoom(roomDtoEmpty);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(roomDtoEmpty, response.getBody());
    }

    @Test
    public void deleteRoom_ValidRoom_SuccessfulDeleteTest(){
        ResponseEntity<Void> response = controller.deleteRoom(roomDtoEmpty.getRoomNumber());
        ResponseEntity<List<RoomDto>> responseList = controller.readAllRooms();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertFalse(
                Objects.requireNonNull(responseList.getBody()).contains(roomDtoEmpty));
    }

    @Test
    public void getOccupation_PopulatedDatabase_SuccessfulGetOccupationTest(){
        ResponseEntity<Double> response = controller.getOccupation();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(50, response.getBody());
    }

    @Test
    public void getRevenue_PopulatedDatabase_SuccessfulGetRevenueTest(){
        ResponseEntity<Double> response = controller.getRevenue();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(60 , response.getBody());
    }

}
