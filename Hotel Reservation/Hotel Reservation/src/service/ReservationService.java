package service;

import model.Customer;
import model.IRoom;
import model.Reservation;
import model.SortbyCheckOutDate;

import java.util.*;

public class ReservationService {

    static ArrayList<Reservation> reservations = new ArrayList<>();
    static ArrayList<IRoom> allRooms = new ArrayList<>();
    private static ReservationService reservationService = null;

    public static ArrayList<Reservation> getReservations() {
        return reservations;
    }

    public static void setReservations(ArrayList<Reservation> reservations) {
        ReservationService.reservations = reservations;
    }

    public static ArrayList<IRoom> getAllRooms() {
        return allRooms;
    }

    public static ReservationService getInstance() {
        if (reservationService == null) {
            reservationService = new ReservationService();
        }
        return reservationService;
    }

    public static Collection<Reservation> getCustomersReservation(Customer customer) {
        Collection<Reservation> collection = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getCustomer().equals(customer)) {
                collection.add(reservation);
            }
        }
        return collection;
    }

    ArrayList<IRoom> getReservedRooms() {

        ArrayList<IRoom> rooms = new ArrayList<>();
        for (Reservation reservation : reservations) {
            rooms.add(reservation.getRoom());
        }
        return rooms;
    }

    public void addRoom(IRoom room) {
        if (room.isFree()) {
            allRooms.add(room);
            System.out.println("Room number " + room.getRoomNumber() + " is successfully added.");

        } else {
            System.out.println("Room number " + room.getRoomNumber() + " is not available.");
        }
    }

    public IRoom getARoom(String roomId) {
        for (IRoom room : allRooms) {
            if (room.getRoomNumber().equals(roomId)) {
                return room;
            }
        }
        System.out.println("Room number " + roomId + " does not exists in our database.");
        return null;
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(reservation);
        room.setPrice(1.0);
        return reservation;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        Collection<IRoom> availableRooms = new ArrayList<>();
        for (IRoom room : allRooms) {
            if (room.isFree()) {
                availableRooms.add(room);
            }
        }
        availableRooms.addAll(findRoomsFromExistingReservation(checkInDate,checkOutDate));
        return availableRooms;
    }

    List<IRoom> findRoomsFromExistingReservation(Date checkInDate, Date checkOutDate){
        List<IRoom> rooms = new ArrayList<>();
        for (Reservation reservation : getReservations()) {
            if (reservation.getCheckOutDate().before(checkInDate) || reservation.getCheckInDate().after(checkOutDate) || reservation.getCheckInDate().equals(checkOutDate)) {
                rooms.add(reservation.getRoom());
            }
        }
        return rooms;
    }

    public void printAllReservation() {
        for (Reservation reservation : reservations) {
            System.out.println(reservation.toString());
        }
    }

    public Collection<Reservation> findEarliestAvailaleRoom(Date checkIn, Date checkOut) {
        Collection<Reservation> finalList = new ArrayList<>();
        List<Reservation> collection = new ArrayList<>();
        List<Reservation> sortedList = new ArrayList<>(reservations);
        Collections.sort(sortedList, new SortbyCheckOutDate());

        for (Reservation reservation: sortedList){
            if(reservation.getCheckOutDate().after(checkIn) && reservation.getCheckInDate().before(checkOut)){
                collection.add(reservation);
            }
        }

        for (int i = 0; i < collection.size() && i < 3; i++) {
            finalList.add(collection.get(i));
        }
        return collection;
    }
}
