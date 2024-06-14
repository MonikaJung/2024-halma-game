package org.example;

import org.example.errors.InvalidBoardInitException;
import org.example.model.Game;

public class Main {

    public static void main(String[] args) throws InvalidBoardInitException {
        Game game = new Game();
        game.testStart();
        //game.start(2, 16, 1);
    }
}