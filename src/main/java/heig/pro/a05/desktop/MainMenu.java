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

public class MainMenu extends BasicGameState {
	private Image startGame, exit, credits;

	/**
	 * Initialise the state. It loads the resources
	 *
	 * @param gameContainer The container holding the game
	 * @param stateBasedGame The game holding this state
	 * @throws SlickException Indicates a failure to initialise a resource
	 */
	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
		startGame = new Image("img/Start.png");
		credits = new Image("img/Credits.png");
		exit = new Image("img/Exit.png");
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
		graphics.drawString("Welcome to our Game", gameContainer.getWidth() / 2 - 100, 50);
		startGame.draw(gameContainer.getWidth() / 2 - 50, 100);
		credits.draw(gameContainer.getWidth() / 2 - 50, 200);
		exit.draw(gameContainer.getWidth() / 2 - 50, 300);
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
		//startGame button pressed
		if((x > gameContainer.getWidth() / 2 - 50 && x < gameContainer.getWidth() / 2 + 50)
				&& (y > 100 && y < 150)) {
			if(Mouse.isButtonDown(0)) {
				stateBasedGame.enterState(3, new FadeOutTransition(org.newdawn.slick.Color.black),
						new FadeInTransition(org.newdawn.slick.Color.black));
			}
		}
		
		//credits button pressed
		if((x > gameContainer.getWidth() / 2 - 50 && x < gameContainer.getWidth() / 2 + 50)
				&& (y > 200 && y < 250)) {
			if(Mouse.isButtonDown(0)) {
				stateBasedGame.enterState(2);
			}
		}
		
		//exit button pressed
		if((x > gameContainer.getWidth() / 2 - 50 && x < gameContainer.getWidth() / 2 + 50)
				&& (y > 300 && y < 350)) {
			if(Mouse.isButtonDown(0)) {
				System.exit(0);
			}
		}
	}

	/**
	 * Get the ID of this menu state
	 *
	 * @return The game unique ID of this menu state
	 */
	@Override
	public int getID() {
		return 0;
	}

}
