package heig.pro.a05.desktop;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class LogIn extends BasicGameState {

    private Image qrCode, start;
    static private int nbPlayers = 0;

    /**
     * Initialise the state. It loads the resources
     *
     * @param gameContainer The container holding the game
     * @param stateBasedGame The game holding this state
     * @throws SlickException Indicates a failure to initialise a resource
     */
    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        try {
            QRCodeGenerator.generateQRCodeImageWithIPsAndPort(600, 600);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        qrCode = new Image("src/main/resources/img/qr.jpg");
        start = new Image("img/Start.png");
    }

    /**
     * Render this state to the game's graphics context.
     * Renders the QrCode, the number of players currently connected
     * and a button to start a game.
     *
     * @param gameContainer The container holding the game
     * @param stateBasedGame The game holding this state
     * @param graphics The graphics context to render to
     * @throws SlickException Indicates a failure to render an artifact
     */
    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.drawString("Connexion", gameContainer.getWidth() / 2 - 50, 50);
        qrCode.draw(gameContainer.getWidth() / 2 - 300, 75);
        start.draw(gameContainer.getWidth() / 2 - 50, 685);
        graphics.drawString("Players connected : " + nbPlayers + "/4",
                gameContainer.getWidth() / 2 - 100, 740);
    }

    /**
     * Update the state's logic based on the amount of time that has passed.
     * In this case, we're looking for user's clicks for the button.
     *
     * @param gameContainer The container holding the game
     * @param stateBasedGame The game holding this state
     * @param i The amount of time that has passed in millisecond since last update
     * @throws SlickException Indicates an internal error
     */
    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        int x = Mouse.getX();
        int y = gameContainer.getHeight() - Mouse.getY();

        // Start button is pressed
        if((x > gameContainer.getWidth() / 2 - 50 && x < gameContainer.getWidth() / 2 + 50)
                && (y > 700 && y < 750)) {
            if(Mouse.isButtonDown(0)) {
                stateBasedGame.getState(1).init(gameContainer, stateBasedGame);
                stateBasedGame.enterState(1);
            }
        }
    }

    /**
     * Get the ID of this login state
     *
     * @return The game unique ID of this login state
     */
    @Override
    public int getID() {
        return 3;
    }

    /**
     * Setter for the number of players
     *
     * @param nb new value for the number of players
     */
    static public void setNbPlayers(int nb) {
        nbPlayers = nb;
    }
}