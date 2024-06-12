package br.com.ada.reservala.repository;

import br.com.ada.reservala.domain.Room;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;
import static br.com.ada.reservala.factory.InstanceFactory.*;

public class RepositoryUnitTest {
    private final RoomRepository repository = getRepositoryMock();

    private final Room room = Room.builder()
            .roomNumber(1)
            .type("Vazio")
            .price(123)
            .available(true)
            .build();

    @Test
    public void createRoom_ValidRoom_SuccessfulCreateTest() {
        Room response = repository.createRoom(room);

        Mockito.verify(repository.getJdbcTemplate(),
                        Mockito.times(1))
                .update(
                        Mockito.eq(repository.getCreateSQL()),
                        Mockito.eq(room.getRoomNumber()),
                        Mockito.eq(room.getType()),
                        Mockito.eq(room.getPrice()),
                        Mockito.eq(room.getAvailable()));

        Assertions.assertEquals(room.getRoomNumber(), response.getRoomNumber());
    }

    @Test
    public void readRoom_EmptyDatabase_SuccessfulReadTest() {
        List<Room> roomsExpected = new ArrayList<>();
        List<Room> roomsResponse = repository.readAllRooms();

        Assertions.assertEquals(roomsExpected, roomsResponse);
    }

    @Test
    public void updateRoom_ValidRoom_SuccessfulUpdateTest() {
        Room roomResponse = repository.updateRoom(room);

        Mockito.verify(repository.getJdbcTemplate(),
                Mockito.times(1))
                .update(Mockito.eq(repository.getUpdateSQL()),
                        Mockito.eq(room.getType()),
                        Mockito.eq(room.getPrice()),
                        Mockito.eq(room.getAvailable()),
                        Mockito.eq(room.getRoomNumber()));

        Assertions.assertEquals(room.getRoomNumber(), roomResponse.getRoomNumber());
    }

    @Test
    public void deleteRoom_ValidRoom_SuccessfulDeleteTest(){
        repository.deleteRoom(room.getRoomNumber());

        Mockito.verify(repository.getJdbcTemplate(),
                Mockito.times(1))
                .update(Mockito.eq(repository.getDeleteSQL()),
                        Mockito.eq(room.getRoomNumber()));
    }

    @Test
    public void deleteAllRooms_SuccessfulDeleteTest(){
        repository.deleteAllRooms();

        Mockito.verify(repository.getJdbcTemplate(),
                Mockito.times(1))
                .update(Mockito.eq(repository.getDeleteAllSQL()));
    }
}
