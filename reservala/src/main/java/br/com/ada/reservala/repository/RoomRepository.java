package br.com.ada.reservala.repository;

import br.com.ada.reservala.domain.Room;
import lombok.Getter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
@Getter
public class RoomRepository {

    private final JdbcTemplate jdbcTemplate;

    private final String createSQL = "insert into room(roomNumber, type, price, available) values (?, ?, ?, ?)";
    private final String readSQL = "select * from room";
    private final String updateSQL = "update room set type = ?, price = ?, available = ? where roomNumber = ?";
    private final String deleteSQL = "delete from room where roomNumber = ?";
    private final String deleteAllSQL = "delete from room";
    private final String selectOccupiedSQL = "select * from room where available = false";

    public RoomRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Room createRoom(Room room) {
        jdbcTemplate.update(
                createSQL,
                room.getRoomNumber(),
                room.getType(),
                room.getPrice(),
                room.getAvailable()
        );
        return room;
    }

    public List<Room> readAllRooms() {
        RowMapper<Room> rowMapper = ((rs, rowNum) -> new Room(
                rs.getInt("roomNumber"),
                rs.getString("type"),
                rs.getInt("price"),
                rs.getBoolean("available")
        ));
        return jdbcTemplate.query(readSQL, rowMapper);
    }

    public List<Room> readOccupiedRooms() {
        RowMapper<Room> rowMapper = ((rs, rowNum) -> new Room(
                rs.getInt("roomNumber"),
                rs.getString("type"),
                rs.getInt("price"),
                rs.getBoolean("available")
        ));
        return jdbcTemplate.query(selectOccupiedSQL, rowMapper);
    }

    public Room updateRoom(Room room) {
        jdbcTemplate.update(
                updateSQL,
                room.getType(),
                room.getPrice(),
                room.getAvailable(),
                room.getRoomNumber());
        return room;
    }

    public Optional<Room> readOneRoom(int roomNumber){
        return readAllRooms().stream()
                .filter(r -> r.getRoomNumber() == roomNumber)
                .findAny();
    }

    public void deleteRoom(Integer roomNumber) {
        jdbcTemplate.update(deleteSQL, roomNumber);
    }

    public void deleteAllRooms(){
        jdbcTemplate.update(deleteAllSQL);
    }
}
