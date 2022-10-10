package model;

public class FreeRoom extends Room {
    public FreeRoom(String roomNumber, RoomType roomType) {
        super(roomNumber, roomType);
        setPrice(0.0);
    }

    @Override
    public String toString() {
        return  "roomNumber='" + roomNumber + '\'' + ", roomType=" + roomType ;
    }
}
