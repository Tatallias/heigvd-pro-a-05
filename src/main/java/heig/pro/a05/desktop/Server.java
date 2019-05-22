package heig.pro.a05.desktop;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Server extends StateBasedGame {
	private static final int MENU = 0;
	private static final int GAME = 1;
	private static final int CREDITS = 2;
	private static final int LOG_IN = 3;

	private static Server instance;

	/**
	 * Returns the unique instance of this game and initializes
	 * it if not done prior to this call.
	 *
	 * @return The unique instance of this game.
	 */
	public static Server getInstance(){
		if(instance == null){
			instance = new Server("PRO");
		}
		return instance;
	}

	/**
	 * Default constructor, initializes the different frames for the game
	 *
	 * @param name Name of the window.
	 */
	private Server(String name) {
		super(name);
		this.addState(new MainMenu());
		this.addState(new Game());
		this.addState(new LogIn());
		this.addState(new Credits());
	}

	/**
	 *
	 * @param gameContainer The container holding the game
	 * @throws SlickException Indicates a failure to initialise a resource
	 */
	@Override
	public void initStatesList(GameContainer gameContainer) throws SlickException {
		this.getState(MENU).init(gameContainer, this);
		this.getState(GAME).init(gameContainer, this);
		this.getState(CREDITS).init(gameContainer, this);
		this.getState(LOG_IN).init(gameContainer, this);
	}


}
