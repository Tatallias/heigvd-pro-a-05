package heig.pro.a05.common.spells;

import heig.pro.a05.common.Wizard;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Define an elemental orb
 */
public class ElementalOrb implements Movable {
    private double x, y;
    private double angle = 0;
    private Wizard caster;
    private Quality quality;
    private MagicType type;
    private double radius = 30.0;
    private boolean cast = false;
    private boolean prepare = false;
    private Wizard target;
    private Image sprite;

    /**
     * Constructor of a magic orb, used to build powerful spell
     *
     * @param caster  the caster of the orb
     * @param quality the quality of the cast
     * @param type    the type (element) of the spell
     */
    public ElementalOrb(Wizard caster, Quality quality, MagicType type) {
        this.x = caster.getX();
        this.y = caster.getY() - radius;
        this.quality = quality;
        this.type = type;
        this.caster = caster;
        this.sprite = type.getSprite();
    }

    /**
     * Setter to true for prepare
     */
    public void setPrepare() {
        this.prepare = true;
    }

    /**
     * Getter for prepare
     *
     * @return boolean, true if the spell is prepared, false if not
     */
    public boolean isPrepared() {
        return prepare;
    }

    /**
     * Getter for cast
     *
     * @return boolean, true if the spell is casted, false if not
     */
    public boolean isCast() {
        return cast;
    }

    /**
     * Getter for the caster
     *
     * @return Wizard, the caster of the orb
     */
    public Wizard getCaster() {
        return caster;
    }

    /**
     * Method used to render the orb
     *
     * @param g the graphics
     * @throws SlickException in case of emergency.
     */
    public void render(Graphics g) throws SlickException {
        g.drawImage(sprite, (int) x - 8, (int) y - 8);
    }

    /**
     * Method used to move the orb on the trajectory.
     */
    public void move() {
        this.x = caster.getX() - radius * Math.sin(angle);
        this.y = caster.getY() - radius * Math.cos(angle);
        double speed = Math.PI / 50;

        angle -= speed;
        if (prepare && x - caster.getX() <= 0.01 && y - caster.getY() + radius <= 0.01) {
            this.cast = true;
        }
    }

    /**
     * Getter for x coordinate of the orb.
     *
     * @return double, the x coordinate of the orb.
     */
    public double getX() {
        return x;
    }

    /**
     * Getter for the y coordinate of the orb
     *
     * @return double, the y coordinate of the orb.
     */
    public double getY() {
        return y;
    }

    /**
     * Getter for the quality of the orb.
     *
     * @return Quality, quality of the orb
     */
    public Quality getQuality() {
        return quality;
    }

    /**
     * Getter for the type of the orb
     *
     * @return the type (element) of the orb.
     */
    public MagicType getType() {
        return type;
    }

    /**
     * Setter for the target
     *
     * @param target target of the orb
     */
    public void setTarget(Wizard target) {
        this.target = target;
    }

    /**
     * Getter for the target
     *
     * @return Wizard, target of the orb
     */
    public Wizard getTarget() {
        return target;
    }
}
