package model;

import java.util.Objects;

public class Room implements IRoom {
    String roomNumber;
    Double price;
    final RoomType roomType;

    public Room(String roomNumber, RoomType roomType) {
        this.roomNumber = roomNumber;
        this.price = 0.0;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "roomNumber='" + roomNumber + '\'' + ", price=" + price + ", roomType=" + roomType;
    }

    @Override
    public String getRoomNumber() {
        return this.roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return this.getRoomPrice();
    }

    @Override
    public RoomType getRoomType() {
        return this.roomType;
    }

    @Override
    public boolean isFree() {
        return price == 0.0;
    }

    @Override
    public void setPrice(Double i) {
        this.price = i;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return roomNumber.equals(room.roomNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber);
    }
}
