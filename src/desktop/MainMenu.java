package desktop;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainMenu extends BasicGameState {
	private Image startGame, exit, credits;
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		startGame = new Image("img/Start.png");
		credits = new Image("img/Credits.png");
		exit = new Image("img/Exit.png");
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		g.drawString("Welcome to our Game", arg0.getWidth() / 2 - 100, 50);
		startGame.draw(arg0.getWidth() / 2 - 50, 100);
		credits.draw(arg0.getWidth() / 2 - 50, 200);
		exit.draw(arg0.getWidth() / 2 - 50, 300);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame arg1, int arg2) throws SlickException {
		int x = Mouse.getX();
		int y = gc.getHeight() - Mouse.getY();
		//startGame button pressed
		if((x > gc.getWidth() / 2 - 50 && x < gc.getWidth() / 2 + 50) && (y > 100 && y < 150)) {
			if(Mouse.isButtonDown(0)) {
				arg1.enterState(3);
			}
		}
		
		//credits button pressed
		if((x > gc.getWidth() / 2 - 50 && x < gc.getWidth() / 2 + 50) && (y > 200 && y < 250)) {
			if(Mouse.isButtonDown(0)) {
				arg1.enterState(2);
			}
		}
		
		//exit button pressed
		if((x > gc.getWidth() / 2 - 50 && x < gc.getWidth() / 2 + 50) && (y > 300 && y < 350)) {
			if(Mouse.isButtonDown(0)) {
				System.exit(0);
			}
		}
	}

	@Override
	public int getID() {
		return 0;
	}

}
