package br.com.ada.reservala.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class Room {
    private Integer roomNumber;
    private String type;
    private Integer price;
    private Boolean available;
}
