package ships;


import battleship.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Ship {

    String name;
    int length;
    String startRow;
    String endRow;
    int startColumn;
    int endColumn;
    List<String> parts;
    Field field;
    private int hitCount = 0;
    private boolean isSunk = false;

    public List<String> getParts() {
        return parts;
    }

    public void setParts(List<String> parts) {
        this.parts = parts;
    }

    public int getHitCount() {
        return hitCount;
    }

    public void setHitCount(int hitCount) {
        this.hitCount = hitCount;
    }

    public boolean isSunk() {
        return isSunk;
    }

    public void setSunk(boolean sunk) {
        isSunk = sunk;
    }

    public Ship(Field field, String name, int length) {
        this.field = field;
        this.name = name;
        this.length = length;
    }

    public void hit() {
        hitCount++;
        if (hitCount == length) {
            isSunk = true;
            System.out.println("You sank a ship!");
        } else {
            System.out.println("You hit a ship!");
        }
    }

    public void placeShip() {
        System.out.println("Enter the coordinates of the " + name + " (" + length + " cells):");
        boolean isPlaced = false;
        while (!isPlaced) {
            getLengthAndParts(enterCoordinates());
            if (!checkIfShipCanBePlaced()) {
                continue;
            } else {
                placeShipOnField();
                field.printField();
                isPlaced = true;
            }
        }
    }

    public List<String> enterCoordinates() {
        Scanner scanner = new Scanner(System.in);
        return new ArrayList<>(List.of(scanner.nextLine().split(" ")));
    }

    private void checkValidity(String startRow, String endRow, int startColumn, int endColumn) {
        int fieldSize = field.getField().size() - 1;

        if (startRow.equals(endRow)) {
            if (startColumn == endColumn) {
                System.out.println("Error! Wrong ship location! Try again:");
                getLengthAndParts(enterCoordinates());
            }
        } else if (startColumn == endColumn) {
            if (startRow.charAt(0) == endRow.charAt(0)) {
                System.out.println("Error! Wrong ship location! Try again:");
                getLengthAndParts(enterCoordinates());
            }
        } else if (startColumn > fieldSize || endColumn > fieldSize || startColumn < 1 || endColumn < 1) {
            System.out.println("Error! Wrong ship location! Try again:");
            getLengthAndParts(enterCoordinates());
        } else if (startRow.charAt(0) > (char) (fieldSize + 64) || endRow.charAt(0) > (char) (fieldSize + 64) || startRow.charAt(0) < (char) 65 || endRow.charAt(0) < (char) 65) {
            System.out.println("Error! Wrong ship location! Try again:");
            getLengthAndParts(enterCoordinates());
        } else if (startColumn != endColumn && startRow.charAt(0) != endRow.charAt(0)) {
            System.out.println("Error! Wrong ship location! Try again:");
            getLengthAndParts(enterCoordinates());
        }
    }

    public void getLengthAndParts(List<String> coordinates) {
        startRow = coordinates.get(0).split("")[0];
        endRow = coordinates.get(1).split("")[0];
        startColumn = Integer.parseInt(coordinates.get(0).substring(1));
        endColumn = Integer.parseInt(coordinates.get(1).substring(1));
        checkValidity(startRow, endRow, startColumn, endColumn);
        if (startRow.equals(endRow)) {
            getHorizontalLengthAndParts(startRow, startColumn, endColumn);
        } else {
            getVerticalLengthAndParts(startRow, endRow, startColumn);
        }
    }

    private void getHorizontalLengthAndParts(String row, int startColumn, int endColumn) {
        int length = Math.abs(endColumn - startColumn) + 1;
        StringBuilder parts = new StringBuilder();
        if (startColumn > endColumn) {
            for (int i = startColumn; i >= endColumn; i--) {
                parts.append(row).append(i).append(" ");
            }
        } else {
            for (int i = startColumn; i <= endColumn; i++) {
                parts.append(row).append(i).append(" ");
            }
        }
        makeParts(length, parts);
    }

    public void getVerticalLengthAndParts(String startRow, String endRow, int column) {
        int length = Math.abs(endRow.charAt(0) - startRow.charAt(0)) + 1;
        StringBuilder parts = new StringBuilder();
        if (startRow.charAt(0) > endRow.charAt(0)) {
            for (int i = startRow.charAt(0); i >= endRow.charAt(0); i--) {
                parts.append((char) i).append(column).append(" ");
            }
        } else {
            for (int i = startRow.charAt(0); i <= endRow.charAt(0); i++) {
                parts.append((char) i).append(column).append(" ");
            }
        }
        makeParts(length, parts);
    }

    private void makeParts(int length, StringBuilder parts) {
        if (length != this.length) {
            System.out.println("Error! Wrong length of the " + name + "! Try again:");
            getLengthAndParts(enterCoordinates());
        } else {
            this.parts = new ArrayList<>(List.of(parts.toString().split(" ")));
        }
    }

    private void placeShipOnField() {
        for (String part : parts) {
            int row = part.charAt(0) - 64;
            int column = Integer.parseInt(part.substring(1));
            field.getField().get(row).set(column, "O ");
        }
    }

    private boolean checkIfShipCanBePlaced() {
        for (String part : parts) {
            int row = part.charAt(0) - 64;
            int column = Integer.parseInt(part.substring(1));
            if (!field.getField().get(row).get(column).equals("~ ")) {
                System.out.println("Error! You placed it too close to another one. Try again:");
                return false;
            }
            for (int i = row - 1; i <= row + 1; i++) {
                for (int j = column - 1; j <= column + 1; j++) {
                    if (i > 0 && i < 11 && j > 0 && j < 11) {
                        if (!field.getField().get(i).get(j).equals("~ ")) {
                            System.out.println("Error! You placed it too close to another one. Try again:");
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
