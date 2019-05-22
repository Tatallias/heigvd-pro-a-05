package heig.pro.a05.desktop;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Credits extends BasicGameState {
    private Image mainMenu;

    /**
     * Get the ID of this credits state
     *
     * @return The game unique ID of this credits state
     */
    @Override
    public int getID() {
        return 2;
    }

    /**
     * Initialise the state. It loads the resources
     *
     * @param gameContainer The container holding the game
     * @param stateBasedGame The game holding this state
     * @throws SlickException Indicates a failure to initialise a resource
     */
    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        mainMenu = new Image("img/Menu.png");
    }

    /**
     * Render this state to the game's graphics context
     *
     * @param gameContainer The container holding the game
     * @param stateBasedGame The game holding this state
     * @param graphics The graphics context to render to
     * @throws SlickException Indicates a failure to render an artifact
     */
    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        int center = gameContainer.getWidth() / 2;
        graphics.drawString("Credits to :", center - 50, 50);
        graphics.drawString("Alison Savary", center - 55, 150);
        graphics.drawString("Benjamin Le Guillou", center - 82, 200);
        graphics.drawString("Guillaume Vetter", center - 67, 250);
        graphics.drawString("Jorge André Fulgencio Esteves", center - 125, 300);
        graphics.drawString("Julien Huguet", center - 55, 350);
        graphics.drawString("Luca Reis de Carvalho", center - 85, 400);
        graphics.drawString("Thanks for their awesome work", center - 120, 470);
        graphics.drawImage(mainMenu, center - mainMenu.getWidth() / 2, 550);
    }

    /**
     * Update the state's logic based on the amount of time that has passed.
     * In this case, we're looking for user's clicks fot the button.
     *
     * @param gameContainer The container holding the game
     * @param stateBasedGame The game holding this state
     * @param i The amount of time that has passed in millisecond since last update
     * @throws SlickException Indicates an internal error that will be reported through the standard framework mechanism
     */
    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        int x = Mouse.getX();
        int y = gameContainer.getHeight() - Mouse.getY();

        // Menu button is pressed
        if((x > gameContainer.getWidth() / 2 - 50 && x < gameContainer.getWidth() / 2 + 50) && (y > 550 && y < 600)) {
            if(Mouse.isButtonDown(0)) {
                stateBasedGame.enterState(0);
            }
        }
    }
}
