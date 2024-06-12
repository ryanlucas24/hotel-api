package br.com.ada.reservala.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class Room {
    private Integer roomNumber;
    private String type;
    private Integer price;
    private Boolean available;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room)) return false;
        Room room = (Room) o;
        return Objects.equals(getRoomNumber(), room.getRoomNumber())
                && Objects.equals(getType(), room.getType())
                && Objects.equals(getPrice(), room.getPrice())
                && Objects.equals(getAvailable(), room.getAvailable());
    }
}
