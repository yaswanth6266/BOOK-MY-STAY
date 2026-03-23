package HOTELBOOKING;

public class UseCase4RoomSearch {

    public static void main(String[] args) {

        SingleRoom single = new SingleRoom();
        DoubleRoom doubleRoom = new DoubleRoom();
        SuiteRoom suite = new SuiteRoom();

        System.out.println("Single Room:");
        single.displayRoomDetails();

        System.out.println("\nDouble Room:");
        doubleRoom.displayRoomDetails();

        System.out.println("\nSuite Room:");
        suite.displayRoomDetails();
    }
}