package br.com.ada.reservala.controller;

import br.com.ada.reservala.controller.dto.RoomDto;
import br.com.ada.reservala.domain.Room;
import br.com.ada.reservala.mapper.RoomMapper;
import br.com.ada.reservala.service.RoomService;
import br.com.ada.reservala.service.utils.RoomNotValidException;
import br.com.ada.reservala.utils.EntityAlreadyExistsException;
import br.com.ada.reservala.utils.EntityNotFoundException;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Getter
@RestController
@RequestMapping("/room")
public class RoomController {
    private final RoomService roomService;
    private final RoomMapper mapper = new RoomMapper();

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    public ResponseEntity<RoomDto> createRoom(@RequestBody RoomDto newRoom) {
        try {
            Room room = mapper.toEntity(newRoom);
            RoomDto response = mapper.toDto(roomService.createRoom(room));

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);
        } catch (RoomNotValidException exception) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        } catch (EntityAlreadyExistsException exception) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .build();
        }
    }

    @GetMapping
    public ResponseEntity<List<RoomDto>> readAllRooms() {
        List<RoomDto> response = mapper.toDto(roomService.readAllRooms());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PutMapping
    public ResponseEntity<RoomDto> updateRoom(@RequestBody RoomDto roomDto) {
        try {
            Room room = mapper.toEntity(roomDto);
            RoomDto response = mapper.toDto(roomService.updateRoom(room));

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
        } catch (RoomNotValidException exception) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        } catch (EntityNotFoundException exception) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @DeleteMapping
    @RequestMapping("/{roomNumber}")
    public ResponseEntity<Void> deleteRoom(@PathVariable("roomNumber") Integer roomNumber) {
        try {
            roomService.deleteRoom(roomNumber);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (EntityNotFoundException exception) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @GetMapping("/occupation")
    public ResponseEntity<Double> getOccupation() {
        Double occupation = roomService.getOccupation();
        return ResponseEntity.status(HttpStatus.OK).body(occupation);
    }

    @GetMapping("/revenue")
    public ResponseEntity<Double> getRevenue() {
        Double revenue = roomService.getRevenue();
        return ResponseEntity.status(HttpStatus.OK).body(revenue);
    }
}
