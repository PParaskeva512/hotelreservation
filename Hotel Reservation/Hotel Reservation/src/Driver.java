import api.AdminResource;
import api.HotelResource;
import model.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Driver {
    static Scanner scanner = new Scanner(System.in);
    static AdminResource adminResource = new AdminResource();
    static HotelResource hotelResource = new HotelResource();

    public static void main(String[] args) {

        System.out.println("***************************************\n" + "* Welcome To Hotel Reservation System *\n" + "***************************************");

        printMainMenu();
        int choice = scanner.nextInt();
        runMainMenu(choice);
    }

    public static void runMainMenu(int choice) {

        boolean flag = true;
        while (flag) {
            switch (choice) {
                case 1:
                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    try {
                        System.out.println("Please enter checkIn date");
                        Date checkIn = dateFormat.parse(scanner.next());
                        System.out.println("Please enter checkOut date");
                        Date checkOut = dateFormat.parse(scanner.next());
                        if(checkIn.equals(checkOut)){
                            System.out.println("Check in and check out date can't be the same");
                            break;
                        }else if(checkOut.before(checkIn)){
                            System.out.println("Check out date can't be the before the check in date");
                            break;
                        }
                        Collection<IRoom> rooms = hotelResource.findARoom(checkIn, checkOut);
                        if (rooms.isEmpty()) {
                            System.out.println("No rooms are available in given date");
                            Collection<Reservation> earliestAvailableRooms = hotelResource.findEarliestAvailaleRoom(checkIn, checkOut);
                            if(earliestAvailableRooms.isEmpty()){
                                System.out.println("No Recommendations");
                            }else {
                                System.out.println("Below are the earliest available rooms");
                                for (Reservation reservation : earliestAvailableRooms) {
                                    System.out.println("Room number " + reservation.getRoom().getRoomNumber() + " is available after " + dateFormat.format(reservation.getCheckOutDate()));
                                }
                            }

                        } else {

                            for (IRoom room : rooms){
                                System.out.println(room.toString());
                            }

                            System.out.println("Please choose the room number");
                            String roomNumber = scanner.next();
                            System.out.println("Please enter email");
                            String email = scanner.next();
                            IRoom room = hotelResource.getRoom(roomNumber);
                            if(!rooms.contains(room)){
                                System.out.println("Room number "+roomNumber+" is not available.");
                                break;
                            }
                            if (room != null) {
                                hotelResource.bookARoom(email, room, checkIn, checkOut);
                                System.out.println("Booking successful");
                            }
                        }
                    } catch (ParseException parseException) {
                        System.out.println("Please enter the date in DD-MM-YYYY format");
                    }
                    break;
                case 2:
                    System.out.println("Please enter email");
                    String email = scanner.next();
                    Customer customer1 = adminResource.getCustomer(email);
                    if(customer1==null){
                        System.out.println("Customer not found.");
                        break;
                    }
                    Collection<Reservation>  reservations = hotelResource.getCustomersReservations(email);
                    if(reservations==null || reservations.isEmpty()){
                        System.out.println("No booking found for given email address");
                    }else {
                        System.out.println("Here are your bookings:");
                        for (Reservation reservation : reservations) {
                            System.out.println(reservation.toString());
                        }
                    }
                    break;
                case 3:
                    System.out.println("Please enter first name");
                    String firstName = scanner.next();
                    System.out.println("Please enter last name");
                    String lastName = scanner.next();
                    System.out.println("Please enter email");
                    email = scanner.next();
                    hotelResource.createACustomer(email, firstName, lastName);
                    break;
                case 4: {
                    printAdminMenu();
                    int choice1 = scanner.nextInt();
                    switch (choice1) {
                        case 1:
                            Collection<Customer> customers = adminResource.getAllCustomers();
                            for (Customer customer : customers) {
                                System.out.println(customer.toString());
                            }
                            break;
                        case 2:
                            Collection<IRoom> rooms = adminResource.getAllRooms();
                            for (IRoom room : rooms) {
                                System.out.println(room.toString());
                            }
                            break;
                        case 3:
                            adminResource.displayAllReservations();
                            break;
                        case 4:
                            System.out.println("How many rooms you want to add");
                            int count = scanner.nextInt();
                            rooms = new ArrayList<>();
                            for (int i = 0; i < count; i++) {
                                try {
                                    System.out.println("Please enter room number");
                                    String roomNumber = scanner.next();
                                    System.out.println("Please enter room type");
                                    String roomType = scanner.next();
                                    IRoom room = new FreeRoom(roomNumber, RoomType.valueOf(roomType.toUpperCase()));
                                    rooms.add(room);
                                } catch (IllegalArgumentException illegalArgumentException) {
                                    System.out.println("Invalid room type");
                                }
                            }
                            adminResource.addRoom(new ArrayList<>(rooms));
                            break;
                        case 5:
                            printMainMenu();
                            runMainMenu(scanner.nextInt());
                            break;
                        default:
                            System.out.println("Invalid Choice");
                            break;
                    }
                }
                break;
                case 5:
                    System.exit(0);
                default:
                    System.out.println("Invalid Choice");
//                    printMainMenu();
                    break;
            }
            printMainMenu();
            choice = scanner.nextInt();
        }
    }

    public static void printMainMenu() {
        System.out.println("***** Main Menu *****");

        System.out.println("1. Find and reserve a room");
        System.out.println("2. See my reservations");
        System.out.println("3. Create an account");
        System.out.println("4. Admin");
        System.out.println("5. Exit");
        System.out.println("*********************");

        System.out.println("Press 1, 2, 3, 4 or 5");

    }

    public static void printAdminMenu() {
        System.out.println("***** Admin Menu *****");

        System.out.println("1. See all customers");
        System.out.println("2. See all rooms");
        System.out.println("3. see all reservations");
        System.out.println("4. Add a room");
        System.out.println("5. Back to main menu");
        System.out.println("*********************");

        System.out.println("Press 1, 2, 3, 4 or 5");

    }
}
