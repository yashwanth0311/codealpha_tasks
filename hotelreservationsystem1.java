import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.text.ParseException;

class Room {
    private int roomNumber;
    private String roomType;
    private double price;
    private boolean isAvailable;

    public Room(int roomNumber, String roomType, double price) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
        this.isAvailable = true;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber=" + roomNumber +
                ", roomType='" + roomType + '\'' +
                ", price=" + price +
                ", isAvailable=" + isAvailable +
                '}';
    }
}

class Reservation {
    private int reservationId;
    private Room room;
    private String guestName;
    private Date checkInDate;
    private Date checkOutDate;
    private double totalPrice;

    public Reservation(int reservationId, Room room, String guestName, Date checkInDate, Date checkOutDate, double totalPrice) {
        this.reservationId = reservationId;
        this.room = room;
        this.guestName = guestName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = totalPrice;
    }

    public int getReservationId() {
        return reservationId;
    }

    public Room getRoom() {
        return room;
    }

    public String getGuestName() {
        return guestName;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }
    public double getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", room=" + room.getRoomNumber() +
                ", guestName='" + guestName + '\'' +
                ", checkInDate=" + dateFormat.format(checkInDate) +
                ", checkOutDate=" + dateFormat.format(checkOutDate) +
                ", Total Price="+ totalPrice +
                '}';
    }
}

class PaymentProcessor {
    public static boolean processPayment(double amount, String cardNumber, String expiryDate, String cvv) {
        // Simulate payment processing
        System.out.println("Processing payment of $" + amount + "...");
        // In a real system, you'd integrate with a payment gateway
        if(cardNumber.length() == 16 && cvv.length() == 3){
            System.out.println("Payment successful!");
            return true;
        }else{
            System.out.println("Payment failed. Invalid card details.");
            return false;
        }
    }
}

public class hotelreservationsystem1 {

    private static List<Room> rooms = new ArrayList<>();
    private static List<Reservation> reservations = new ArrayList<>();
    private static int reservationCounter = 1;

    public static void main(String[] args) {
        initializeRooms();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nHotel Reservation System");
            System.out.println("1. Search Rooms");
            System.out.println("2. Make Reservation");
            System.out.println("3. View Reservations");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    searchRooms(scanner);
                    break;
                case 2:
                    makeReservation(scanner);
                    break;
                case 3:
                    viewReservations();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void initializeRooms() {
        rooms.add(new Room(101, "Single", 100.0));
        rooms.add(new Room(102, "Single", 120.0));
        rooms.add(new Room(201, "Double", 150.0));
        rooms.add(new Room(202, "Double", 200.0));
        rooms.add(new Room(301, "Suite", 250.0));
        rooms.add(new Room(302, "Suite", 299.0));
    }

    private static void searchRooms(Scanner scanner) {
        System.out.print("Enter check-in date (dd-MM-yyyy): ");
        String checkInDateStr = scanner.nextLine();
        System.out.print("Enter check-out date (dd-MM-yyyy): ");
        String checkOutDateStr = scanner.nextLine();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            @SuppressWarnings("unused")
            Date checkInDate = dateFormat.parse(checkInDateStr);
            @SuppressWarnings("unused")
            Date checkOutDate = dateFormat.parse(checkOutDateStr);
            System.out.println("\nAvailable Rooms:");
            for (Room room : rooms) {
                if (room.isAvailable()) {
                    System.out.println(room);
                }
            }
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use dd-MM-yyyy.");
        }
    }

    private static void makeReservation(Scanner scanner) {
        System.out.print("Enter room number: ");
        int roomNumber = scanner.nextInt();
        scanner.nextLine();

        Room selectedRoom = null;
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber && room.isAvailable()) {
                selectedRoom = room;
                break;
            }
        }

        if (selectedRoom == null) {
            System.out.println("Room not available or does not exist.");
            return;
        }

        System.out.print("Enter guest name: ");
        String guestName = scanner.nextLine();

        System.out.print("Enter check-in date (dd-MM-yyyy): ");
        String checkInDateStr = scanner.nextLine();
        System.out.print("Enter check-out date (dd-MM-yyyy): ");
        String checkOutDateStr = scanner.nextLine();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date checkInDate = dateFormat.parse(checkInDateStr);
            Date checkOutDate = dateFormat.parse(checkOutDateStr);

            long duration = checkOutDate.getTime() - checkInDate.getTime();
            int days = (int) (duration / (1000 * 60 * 60 * 24));
            double totalPrice = selectedRoom.getPrice() * days;

            System.out.print("Enter card number: ");
            String cardNumber = scanner.nextLine();
            System.out.print("Enter expiry date: ");
            String expiryDate = scanner.nextLine();
            System.out.print("Enter CVV: ");
            String cvv = scanner.nextLine();

            if (PaymentProcessor.processPayment(totalPrice, cardNumber, expiryDate, cvv)) {
                Reservation reservation = new Reservation(reservationCounter++, selectedRoom, guestName, checkInDate, checkOutDate, totalPrice);
                reservations.add(reservation);
                selectedRoom.setAvailable(false);
                System.out.println("Reservation successful! Reservation ID: " + reservation.getReservationId());

            } else {
                System.out.println("Reservation failed due to payment issues.");
            }

        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use dd-MM-yyyy.");
        }
    }

    private static void viewReservations() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            System.out.println("\nReservations:");
            for (@SuppressWarnings("unused") Reservation reservation : reservations);
            }
            System.out.println(reservations);
    }
}
