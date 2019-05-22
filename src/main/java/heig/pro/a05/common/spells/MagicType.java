package heig.pro.a05.common.spells;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Enum to define the element of magic
 */
public enum MagicType {
    EARTH,FIRE,LIGHTNING,WATER;

    /**
     * Return the sprite associated to the element
     * @return Image, the sprite associated to the element
     */
    public Image getSprite() {
        try {
            switch (this) {
                case FIRE:
                    return new Image("img/orb_feu.png");
                case LIGHTNING:
                    return new Image("img/orb_elec.png");
                case WATER:
                    return new Image("img/orb_eau.png");
                case EARTH:
                    return new Image("img/orb_terre.png");
            }
        } catch (
                SlickException e) {
            e.printStackTrace();
        }
        return null;
    }
}
