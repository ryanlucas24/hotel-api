package br.com.ada.reservala.mapper;

import br.com.ada.reservala.controller.dto.RoomDto;
import br.com.ada.reservala.domain.Room;
import br.com.ada.reservala.service.utils.RoomNotValidException;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoomMapper {

    public Room toEntity(RoomDto roomDto){
        if(roomDto == null)
            throw new RoomNotValidException("Room not valid!");

        return Room.builder()
                .roomNumber(roomDto.getRoomNumber())
                .type(roomDto.getType())
                .price(roomDto.getPrice())
                .available(roomDto.getAvailable())
                .build();
    }

    public RoomDto toDto(Room room){
        return RoomDto.builder()
                .roomNumber(room.getRoomNumber())
                .type(room.getType())
                .price(room.getPrice())
                .available(room.getAvailable())
                .build();
    }

    public List<RoomDto> toDto(List<Room> rooms){
        return rooms.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
