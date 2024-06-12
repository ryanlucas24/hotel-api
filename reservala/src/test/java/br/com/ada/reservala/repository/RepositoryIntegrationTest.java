package br.com.ada.reservala.repository;

import br.com.ada.reservala.domain.Room;
import br.com.ada.reservala.factory.InstanceFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class RepositoryIntegrationTest {

    @Autowired
    private RoomRepository repository;
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
            .build();;

    @BeforeEach
    public void populateDatabase(){
        repository.createRoom(roomEmpty);
        repository.createRoom(roomOccupied);
    }
    @AfterEach
    public void excludeDatabaseData(){
        repository.deleteAllRooms();
    }

    @Test
    public void createRoom_ValidRoom_SuccessfulCreateTest(){
        Room room = InstanceFactory.getNewRoom();
        Room responseRoom = repository.createRoom(room);

        Assertions.assertEquals(room, responseRoom);
    }

    @Test
    public void createRoom_ValidExistentRoom_SuccessfulThrowExceptionTest(){
        Assertions.assertThrows(
                DuplicateKeyException.class,
                () -> repository.createRoom(roomEmpty));
    }

    @Test
    public void readRoom_PopulatedDatabase_SuccessfulReadTest(){
        List<Room> expectedRoomsList = List.of(roomEmpty, roomOccupied);
        List<Room> responseRoomsList = repository.readAllRooms();

        Assertions.assertTrue(expectedRoomsList.containsAll(responseRoomsList));
    }
    @Test
    public void readOccupiedRoom_PopulatedDatabase_SuccessfulReadTest(){
        List<Room> responseRoomsList = repository.readOccupiedRooms();

        Assertions.assertFalse(responseRoomsList.contains(roomEmpty));
        Assertions.assertTrue(responseRoomsList.contains(roomOccupied));
    }
    @Test
    public void readOneRoom_PopulatedDatabase_SuccessfulReadTest(){
        Optional<Room> roomResponse = repository.readOneRoom(roomEmpty.getRoomNumber());

        Assertions.assertEquals(roomResponse.get(), roomEmpty);
    }



    @Test
    public void updateRoom_ValidRoom_SuccessfulUpdateTest(){
        roomEmpty.setType("Updated Room");
        roomEmpty.setPrice(42);

        Room roomResponse = repository.updateRoom(roomEmpty);

        Assertions.assertEquals(roomEmpty, roomResponse);
    }

    @Test
    public void deleteRoom_ValidRoom_SuccessDeleteTest(){
        repository.deleteRoom(roomEmpty.getRoomNumber());

        List<Room> roomsListResponse = repository.readAllRooms();

        Assertions.assertFalse(roomsListResponse.contains(roomEmpty));
    }

    @Test
    public void deleteAllRooms_PopulatedDatabase_SuccessfulDeleteTest(){
        repository.deleteAllRooms();

        List<Room> roomsListResponse = repository.readAllRooms();

        Assertions.assertFalse(
                roomsListResponse.contains(roomEmpty) ||
                        roomsListResponse.contains(roomOccupied));
    }
}
