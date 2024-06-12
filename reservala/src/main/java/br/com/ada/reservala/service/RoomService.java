package br.com.ada.reservala.service;

import br.com.ada.reservala.domain.Room;
import br.com.ada.reservala.repository.RoomRepository;
import br.com.ada.reservala.service.utils.RoomNotValidException;
import br.com.ada.reservala.service.utils.RoomValidator;
import br.com.ada.reservala.utils.EntityAlreadyExistsException;
import br.com.ada.reservala.utils.EntityNotFoundException;
import br.com.ada.reservala.utils.Notification;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Getter
public class RoomService extends RoomValidator{

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository){
        this.roomRepository = roomRepository;
    }

    public Room createRoom(Room room){
        Notification validation = validate(room);
        if(validation.hasErrors())
            throw new RoomNotValidException(validation.errorMessage());

        if(roomRepository.readOneRoom(room.getRoomNumber()).isPresent())
            throw new EntityAlreadyExistsException("Room already exist!");

        return roomRepository.createRoom(room);
    }

    public List<Room> readAllRooms(){
        return roomRepository.readAllRooms();
    }

    public Room updateRoom(Room room){
        Notification validation = validate(room);
        if(validation.hasErrors())
            throw new RoomNotValidException(validation.errorMessage());

        if(roomRepository.readOneRoom(room.getRoomNumber()).isEmpty())
            throw new EntityNotFoundException("Room not found!");

        return roomRepository.updateRoom(room);
    }

    public void deleteRoom(Integer roomNumber){
        if(roomRepository.readOneRoom(roomNumber).isEmpty())
            throw new EntityNotFoundException("Room not found!");

        roomRepository.deleteRoom(roomNumber);
    }

    //Deve calcular o percentual de quartos ocupados
    public Double getOccupation(){
        List<Room> allRooms = roomRepository.readAllRooms();
        List<Room> occupiedRooms = roomRepository.readOccupiedRooms();

        return calculateOccupation(allRooms.size(), occupiedRooms.size());
    }

    //Deve calcular a receita obtida
    public Double getRevenue(){
        List<Room> occupiedRooms = roomRepository.readOccupiedRooms();
        return (double) occupiedRooms.stream()
                .mapToInt(Room::getPrice).sum();
    }

    private Double calculateOccupation(int size1, int size2){
        if(size1 == 0 || size2 == 0) return 0d;
        return (double) ((size2*100)/size1);
    }

}
