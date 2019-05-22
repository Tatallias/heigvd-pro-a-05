package heig.pro.a05.common.spells;

import heig.pro.a05.common.Wizard;

/**
 * Abstract class to define spells
 */
public abstract class Spell {
    private Quality quality;
    private MagicType type;
    private boolean over;
    private Wizard caster;

    /**
     * Constructor of a spell, taking an orb
     *
     * @param orb the magical orb used to set the values of the spell
     */
    public Spell(ElementalOrb orb) {
        this.quality = orb.getQuality();
        this.type = orb.getType();
        this.caster = orb.getCaster();
    }

    /**
     * Return if the spell has finished. It should disappear in that case.
     *
     * @return true if the spell has finished. false otherwise.
     */
    public boolean isOver() {
        return over;
    }

    /**
     * Setter for over
     *
     * @param b the new value for over
     */
    public void setOver(boolean b) {
        over = b;
    }

    /**
     * Return the spell's caster
     *
     * @return Wizard who casted the spell
     */
    public Wizard getCaster() {
        return caster;
    }

    /**
     * Set the spell's caster
     *
     * @param wizard Wizard who casted the spell
     */
    public void setCaster(Wizard wizard) {
        caster = wizard;
    }

    /**
     * Method used to compute the power of the spell depending of its quality
     *
     * @return int, the power of the spell
     */
    public int computePower() {
        return quality.computePower();
    }

    /**
     * Getter for the spell's element
     *
     * @return MagicType, element of the spell.
     */
    public MagicType getType() {
        return type;
    }
}
