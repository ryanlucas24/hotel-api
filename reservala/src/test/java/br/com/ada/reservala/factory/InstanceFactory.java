package br.com.ada.reservala.factory;

import br.com.ada.reservala.controller.RoomController;
import br.com.ada.reservala.controller.dto.RoomDto;
import br.com.ada.reservala.domain.Room;
import br.com.ada.reservala.repository.RoomRepository;
import br.com.ada.reservala.service.RoomService;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Random;

public class InstanceFactory {
    private static final Random random = new Random();
    private static JdbcTemplate template;
    private static RoomRepository repository;
    private static RoomService service;
    private static RoomController controller;

    public static JdbcTemplate getTemplateMock(){
        if(template == null)
            template = Mockito.mock(JdbcTemplate.class);

        return template;
    }
    public static RoomRepository getRepositoryMock(){
        if(repository == null)
            repository = new RoomRepository(getTemplateMock());

        return repository;
    }
    public static RoomService getServiceMock(){
        if(service == null)
            service = new RoomService(getRepositoryMock());

        return service;
    }

    public static RoomController getControllerMock(){
        if(controller == null)
            controller = new RoomController(getServiceMock());

        return controller;
    }

    public static Room getNewRoom(){
        return Room.builder()
                .roomNumber(getPositiveInteger())
                .type("Test Room")
                .price(getPositiveInteger())
                .available(random.nextBoolean())
                .build();
    }
    public static RoomDto getNewRoomDto(){
        return RoomDto.builder()
                .roomNumber(getPositiveInteger())
                .type("Test Room")
                .price(getPositiveInteger())
                .available(random.nextBoolean())
                .build();
    }

    private static int getPositiveInteger(){
        int integer = random.nextInt(1000);
        if(integer == 0)
            integer = getPositiveInteger();
        return integer;
    }
}
