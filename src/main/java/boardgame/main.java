package boardgame;

import javafx.application.Application;
import boardgame.view.BoardGameApplication;

/**
 * Entry point for the board game application.
 * <p>
 * This class contains the main method, which serves as the entry point for the application.
 * It launches the JavaFX application by invoking the {@link javafx.application.Application#launch(Class, String[])}
 * method with the {@link boardgame.view.BoardGameApplication} class.
 * </p>
 */
public class main {

    /**
     * The main method to start the board game application.
     *
     * @param args Command-line arguments (not used in this application)
     */
    public static void main(String[] args) {
        Application.launch(BoardGameApplication.class, args);


    }
}
