package br.com.ada.reservala.service;

import br.com.ada.reservala.domain.Room;
import br.com.ada.reservala.factory.InstanceFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ServiceIntegrationTest {

    @Autowired
    public RoomService service;

    private final Room roomEmpty = Room.builder()
            .roomNumber(1)
            .type("Test Room")
            .price(80)
            .available(true)
            .build();
    private final Room roomOccupied =  Room.builder()
            .roomNumber(2)
            .type("Test Room")
            .price(90)
            .available(false)
            .build();

    @BeforeEach
    public void populateDatabase(){
        service.createRoom(roomEmpty);
        service.createRoom(roomOccupied);
    }
    @AfterEach
    public void excludeDatabaseData(){
        service.getRoomRepository().deleteAllRooms();
    }

    @Test
    public void createRoom_ValidRoom_SuccessfulCreateTest(){
        Room newRoom = InstanceFactory.getNewRoom();
        Room roomResponse = service.createRoom(newRoom);

        Assertions.assertEquals(newRoom, roomResponse);
    }

    @Test
    public void readAllRooms_PopulatedDatabase_SuccessfulReadTest(){
        List<Room> roomsListExpected = List.of(roomEmpty, roomOccupied);
        List<Room> roomsListResponse = service.readAllRooms();

        Assertions.assertTrue(roomsListResponse.containsAll(roomsListExpected));
    }

    @Test
    public void updateRoom_ValidRoom_SuccessfulUpdateTest(){
        roomEmpty.setPrice(50);
        roomEmpty.setType("Room Updated");

        Room roomResponse = service.updateRoom(roomEmpty);

        Assertions.assertEquals(
                roomEmpty,
                roomResponse);
    }

    @Test
    public void deleteRoom_ValidRoom_SuccessfulDeleteTest(){
        service.deleteRoom(roomEmpty.getRoomNumber());

        List<Room> allRooms = service.readAllRooms();

        Assertions.assertFalse(allRooms.contains(roomEmpty));
    }

    @Test
    public void getOccupation_PopulatedDatabase_SuccessfulGetOccupationTest(){
        Assertions.assertEquals(
                50,
                service.getOccupation()
        );
    }

    @Test
    public void getRevenue_PopulatedDatabase_SuccessfulGetRevenueTest(){
        Assertions.assertEquals(
                (double) roomOccupied.getPrice(),
                service.getRevenue()
        );
    }
}
