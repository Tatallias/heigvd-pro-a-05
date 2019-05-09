package desktop;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class ServerMain extends StateBasedGame{
	private static final int MENU = 0;
	private static final int CREDITS = 2;
	private static final int GAME = 1;
	private static final int LOG_IN = 3;
	
	
	public ServerMain(String name) {
		super(name);
		this.addState(new MainMenu());
		this.addState(new Game());
		this.addState(new LogIn());
	}
	
	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(MENU).init(gc, this);
		//this.getState(CREDITS).init(arg0, this);
		this.getState(GAME).init(gc, this);
		this.getState(LOG_IN).init(gc, this);
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer apgc;
		try {
			apgc = new AppGameContainer(new ServerMain("PRO"), 640, 480, false);
			apgc.setTargetFrameRate(60);
			apgc.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
