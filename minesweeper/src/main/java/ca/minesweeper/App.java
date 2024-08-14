package ca.minesweeper;

import java.util.Scanner;

public class App 
{
    public static void main( String[] args )
    {
        Scanner scanner = new Scanner(System.in);
        boolean playAgain;

        do {
            System.out.println("Welcome to Minesweeper!");
            Game game = new Game(scanner);
            game.startGame();
            System.out.print("Press any key to play again or 'q' to quit...");
            String input = scanner.nextLine();
            playAgain = !input.equalsIgnoreCase("q");
        } while (playAgain);

        scanner.close();
        System.out.println("Thanks for playing!");
    }
}
