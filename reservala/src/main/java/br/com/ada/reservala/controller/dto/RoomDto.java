package br.com.ada.reservala.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RoomDto {
    private int roomNumber;
    private String type;
    private Integer price;
    private Boolean available;
}


