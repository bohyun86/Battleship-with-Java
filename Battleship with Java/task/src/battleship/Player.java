package battleship;

import ships.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player {
    Field field = new Field();
    Field fogField = new Field();
    List<List<String>> filedMap = field.getField();
    List<List<String>> fogFieldMap = fogField.getField();
    String name;

    public Player(String name) {
        this.name = name;
    }

    private List<Ship> ships = new ArrayList<>();

    public void makeField() {
        field.makeField();
        field.printField();
        fogField.makeField();
    }

    public void addShip() {
        ships.add(new AircraftCarrier(field, "Aircraft Carrier", 5));
        ships.add(new Battleship(field, "Battleship", 4));
        ships.add(new Submarine(field, "Submarine", 3));
        ships.add(new Cruiser(field, "Cruiser", 3));
        ships.add(new Destroyer(field, "Destroyer", 2));
        placeShips();
    }

    public void placeShips() {
        for (Ship ship : ships) {
            ship.placeShip();
        }
    }

    public void takeAShot() {
        Scanner scanner = new Scanner(System.in);
        String shot = scanner.nextLine();
        int x = shot.charAt(0) - 64;
        int y = Integer.parseInt(shot.substring(1));
        if (!checkShot(x, y)) {
            takeAShot();
        } else {
            if (filedMap.get(x).get(y).equals("O ")) {
                filedMap.get(x).set(y, "X ");
                fogFieldMap.get(x).set(y, "X ");
                checkHit(shot);
            } else if (filedMap.get(x).get(y).equals("X ")) {
                fogFieldMap.get(x).set(y, "X ");
                System.out.println("You hit a ship!");
            } else {
                filedMap.get(x).set(y, "M ");
                fogFieldMap.get(x).set(y, "M ");
                System.out.println("You missed!");
            }
        }
    }

    private void checkHit(String shot) {
        for (Ship ship : ships) {
            if (ship.getParts().contains(shot)) {
                ship.hit();
            }
        }
    }

    public boolean checkShot(int x, int y) {
        if (x < 1 || x > 10 || y < 1 || y > 10) {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            return false;
        }
        return true;
    }

    public boolean isLose() {
        return ships.stream().allMatch(Ship::isSunk);
    }

}
