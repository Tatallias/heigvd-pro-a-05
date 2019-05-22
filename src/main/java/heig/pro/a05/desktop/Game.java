package heig.pro.a05.desktop;

import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import heig.pro.a05.common.spells.*;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.tiled.TiledMap;

import heig.pro.a05.common.Wizard;
import heig.pro.a05.util.Vector;

public class Game extends BasicGameState {
	private GameContainer container;
	private TiledMap map;
	private LinkedList<Wizard> wizards;
	private LinkedList<AttackSpell> attackSpells, attackSpellstoRemove;
	private LinkedList<ShieldSpell> shieldSpells, shieldSpellstoRemove;
	private LinkedList<ElementalOrb> elementalOrbs, elementalOrbstoRemove;
	private static String[] args = new String[3];
	private static boolean changed;
	private static int id;
	private Wizard winner = null;

	/**
	 * Method init, used to set the values correctly
	 * @param gc
	 * @param sbg
	 * @throws SlickException
	 */
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		container = gc;
	    map = new TiledMap("img/tiles/map1.tmx");
	    wizards = new LinkedList<>();
	    attackSpellstoRemove = new LinkedList<>();
	    shieldSpellstoRemove = new LinkedList<>();
	    shieldSpells = new LinkedList<>();
	    attackSpells = new LinkedList<>();
	    elementalOrbs = new LinkedList<>();
		elementalOrbstoRemove = new LinkedList<>();
	    wizards.add(new Wizard(135, 245, new Image("img/wizard_pink.png")));
	    wizards.add(new Wizard(489, 245, new Image("img/wizard_blue.png")));
	    wizards.add(new Wizard(312, 68, new Image("img/wizard_green.png")));
	    wizards.add(new Wizard(312, 422, new Image("img/wizard_brown.png")));
		setId();
	}

	/**
	 * Method used to set the id's of the wizards.
	 */
	private void setId(){
		int id = 0;
		for(Wizard w : wizards){
			w.setId(id);
			id++;
		}
	}

	/**
	 * Method render used to render and display the game.
	 * @param gc
	 * @param sbg
	 * @param g
	 * @throws SlickException
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		if(winner!=null){
			g.clear();
			g.setColor(Color.white);
			g.drawString("Wizard number " + (winner.getId()+1) + " won!",gc.getWidth()/2 - 100, gc.getHeight()/2 -10);

		} else {
			g.scale(2, 2);
			map.render(0, 0);
			for (Wizard wizard : wizards) {
				wizard.render(g);
			}
			for (AttackSpell as : attackSpells) {
				as.render(g);
			}
			for (ElementalOrb orb : elementalOrbs) {
				orb.render(g);
			}
		}

	}

	/**
	 * Method used to check if a wizard won.
	 * @return
	 */
	public Wizard checkWinner(){
		int winnerIndex = 0;
		int nbAlive = 0;
		for(int i = 0; i < wizards.size(); i++){
			if(!wizards.get(i).isDead()){
				nbAlive++;
				winnerIndex = i;
			}
		}
		if(nbAlive == 1){
			return wizards.get(winnerIndex);
		}
		return null;
	}

	/**
	 * Method update, called each time the game is updated (each frame)
	 * @param gc
	 * @param sbg
	 * @param a
	 * @throws SlickException
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int a) throws SlickException {
		//We remove the objects that should not be displayed
		remove();
		//We set the winner
		winner = checkWinner();
		if(winner==null) {
			checkUpdate();
			for (AttackSpell as : attackSpells) {
				as.move();
				for (Wizard wizard : wizards) {
					if (wizard.checkCollision(as)) {
						wizard.getHit(as);
						//If the spell bounce, we do not remove it
						if (!as.isBounce()) {
							attackSpellstoRemove.add(as);
							if (wizard.getOrbs().size() != 0) {
								for (int i = 0; i < wizard.getOrbs().size(); i++) {
									elementalOrbstoRemove.add(wizard.getOrbs().get(i));
								}
								wizard.getOrbs().clear();
							}
						}
					}
				}
				if (as.isOver())
					attackSpellstoRemove.add(as);
			}

			for (ShieldSpell sp : shieldSpells) {
				if (sp.isOver()) {
					shieldSpellstoRemove.add(sp);
				}
			}

			for (Wizard wizard : wizards) {
				for (ElementalOrb orb : wizard.getOrbs()) {
					orb.move();
					if (orb.isCast()) {
						throwAttack(orb.getTarget(), wizard);
					}
				}
				wizard.getOrbs().removeAll(elementalOrbstoRemove);
			}
		} else {
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					sbg.enterState(0, new FadeOutTransition(org.newdawn.slick.Color.black), new FadeInTransition(org.newdawn.slick.Color.black));
				}
			}, 2000);
		}
	}

	/**
	 * Get the ID of this game state
	 *
	 * @return The game unique ID of this game state
	 */
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 1;
	}

	/**
	 * Helper method used to cast a shield
	 * @param caster the caster of the shield
	 */
	private void castShield(Wizard caster){
		if(caster.getOrbs().size() != 0) {
			for (int i = 0; i < caster.getShield().size(); i++) {
				shieldSpellstoRemove.add(caster.getShield().get(i));
				caster.getShield().get(i).setOver(true);
				caster.getShield().clear();
			}
		}
		LinkedList<ElementalOrb> orbs = caster.getOrbs();
		for(int i = 0; i < orbs.size(); i++) {
			ElementalOrb orb = orbs.get(i);
			shieldSpells.add(new ShieldSpell(orb));
			elementalOrbstoRemove.add(orb);
		}
		orbs.clear();
	}

	/**
	 * Helper method used to throw an attack at the target
	 * @param target the target of the spell
	 * @param caster the caster of the spell
	 */
	private void throwAttack(Wizard target, Wizard caster){
		if(target != null) {
			LinkedList<ElementalOrb> orbs = caster.getOrbs();
			if (orbs != null) {
				for (int i = 0; i < orbs.size(); i++) {
					if (orbs.get(i).isCast()) {
						ElementalOrb orb = orbs.get(i);
						attackSpells.add(new AttackSpell(orb, target));
						elementalOrbstoRemove.add(orb);
					}
				}
			}
		}
	}

	/**
	 * Method used to cast an attack and find the target using a vector
	 * @param v the vector of the direction of the attack
	 * @param caster the caster of the attack
	 */
	private void castAttack(Vector v, Wizard caster){
		Wizard target = findTarget(v, caster);
		if(target != null) {
			for (ElementalOrb orb : caster.getOrbs()) {
				orb.setPrepare();
				orb.setTarget(target);
			}
		}
	}

	/**
	 * Helper method used to find the correct target using a direction vector.
	 * @param v the direction vector
	 * @param caster the caster of the attack spell.
	 * @return the target of the spell, or null if the target has not been found.
	 */
	private Wizard findTarget (Vector v, Wizard caster){
		Wizard result = null;
		double minAngle = 10;
		for(Wizard target : wizards){
			if(target != caster) {
				double angle = Math.abs(v.getAngle(new Vector(caster.getX(), target.getX(), caster.getY(), target.getY())));
				if (angle < minAngle && angle < Math.PI/4) {
					minAngle = angle;
					result = target;
				}
			}
		}
		return result;
	}

	/**
	 * Helper method used to cast an orb
	 * @param caster the caster of the orb
	 * @param qual the quality of the drawing of the spell
	 * @param type the type of the orb
	 */
	private void castOrb(Wizard caster, Quality qual, MagicType type) {
			ElementalOrb orb = new ElementalOrb(caster, qual, type);
			if (caster.addOrb(orb)) {
				elementalOrbs.add(orb);
			}

	}

	/**
	 * Method used to parse a request and do something accordingly
	 * @param id the id of the wizard that send the request.
	 * @param request the request sent.
	 */
	public void parse(int id, byte[] request){
		String[] requestStringSplit = new String[3];
		for(int i = 0; i < requestStringSplit.length; i++){
			requestStringSplit[i] = "";
		}
		int indexString = 0;

		for(int i = 0; i < request.length; i++){
			if(((char)request[i])!='$') {
				if (((char) request[i]) == ' ') {
					indexString += 1;
				} else {
					requestStringSplit[indexString] += (char) request[i];
				}
			} else {
				break;
			}
		}

		args[0] = requestStringSplit[0];
		if(request.length > 1) {
			if (args[0].equals("ATT")) {
				args[1] = requestStringSplit[1];
				args[2] = requestStringSplit[2];
				this.id = id;
				changed = true;
			} else if (args[0].equals("SHI")) {
				this.id = id;
				changed = true;
			} else if (args[0].equals("CHA")){
				this.id = id;
				args[1] = requestStringSplit[1];
				args[2] = requestStringSplit[2];
				changed = true;
			}
		}
	}

	/**
	 * Hehlper method used to check if a new request has been sent.
	 */
	private void checkUpdate(){
		if(changed){
			changed = false;
			if(args[0].equals("ATT")){
				castAttack(new Vector(Double.parseDouble(args[1]), Double.parseDouble(args[2])), wizards.get(id));
			} else if(args[0].equals("SHI")){
				castShield(wizards.get(id));
			} else if(args[0].equals("CHA")){
				castOrb(wizards.get(id), Quality.values()[Integer.parseInt(args[2].charAt(0) + "")], MagicType.values()[Integer.parseInt(args[1])]);
			}
		}
	}

	/**
	 * Helper method that removes the elements that have been schedule to be removed
	 */
	private void remove(){
		for(AttackSpell as : attackSpellstoRemove) {
			attackSpells.remove(as);
		}
		attackSpellstoRemove.clear();

		for(ShieldSpell sp : shieldSpellstoRemove) {
			shieldSpells.remove(sp);
		}
		shieldSpellstoRemove.clear();

		for(ElementalOrb orb : elementalOrbstoRemove) {
			elementalOrbs.remove(orb);
		}
		elementalOrbstoRemove.clear();
	}

}
