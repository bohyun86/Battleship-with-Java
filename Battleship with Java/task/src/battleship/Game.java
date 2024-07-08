package battleship;

import java.util.Scanner;

public class Game {

    Player player1 = new Player("Player 1");
    Player player2 = new Player("Player 2");
    Player currentPlayer = player1;
    Player nextPlayer = player2;
    Player temp;

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Player 1, place your ships on the game field");
        player1.makeField();
        player1.addShip();
        System.out.println("Press Enter and pass the move to another player");
        scanner.nextLine();

        System.out.println("Player 2, place your ships on the game field");
        player2.makeField();
        player2.addShip();


        while (true) {
            System.out.println("Press Enter and pass the move to another player");
            scanner.nextLine();
            nextPlayer.fogField.printField();
            System.out.println("---------------------");
            currentPlayer.field.printField();
            System.out.println(currentPlayer.name + ", it's your turn:");
            nextPlayer.takeAShot();
            if (nextPlayer.isLose()) {
                System.out.println("You sank the last ship. You won. Congratulations!");
                break;
            }
            temp = currentPlayer;
            currentPlayer = nextPlayer;
            nextPlayer = temp;
        }
    }
}