package heig.pro.a05.common.spells;

import javafx.util.Pair;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Define a defensive spell
 */
public class ShieldSpell extends Spell {

    private ElementalOrb orb;
    private int frameCounter = computePower() * 25;
    private boolean over = false;
    private static int counter = -1;
    private int radius;

    /**
     * Constructor taking an orb as parameter.
     *
     * @param orb the magic orb defining the spell.
     */
    public ShieldSpell(ElementalOrb orb) {
        super(orb);
        counter += 4;
        radius = counter * 4;
        this.orb = orb;
        orb.getCaster().addShield(this);
    }

    /**
     * Helper method used to compute the angle at which the shield needs to be oriented
     *
     * @return a Pair of integers that represents the starting and ending angles of the arc of the shield.
     */
    private Pair<Integer, Integer> getArcBounds() {
        switch (orb.getCaster().getId()) {
            case 0:
                return new Pair<>(270, 90);
            case 1:
                return new Pair<>(90, 270);
            case 2:
                return new Pair<>(0, 180);
            case 3:
                return new Pair<>(180, 0);
            default:
                return new Pair<>(0, 0);
        }
    }

    /**
     * Method used to render a shield
     *
     * @param g graphics
     * @throws SlickException in case of emergency.
     */
    public void render(Graphics g) throws SlickException {
        g.setColor(Color.black);
        g.setLineWidth(4);
        g.drawArc(orb.getCaster().getX() - 18 - radius / 2 - counter,
                orb.getCaster().getY() - 18 - radius / 2 - counter,
                36 + radius + counter * 2, 36 + radius + counter * 2,
                getArcBounds().getKey(),
                getArcBounds().getValue());

        frameCounter--;
        if (!orb.getCaster().getShield().isEmpty() && frameCounter == 0) {
            orb.getCaster().getShield().clear();
            over = true;
        }
    }

    /**
     * Method used to check if the shield is over.
     *
     * @return true if the shield is over, false otherwise.
     */
    public boolean isOver() {
        counter = -1;
        return over;
    }

}
