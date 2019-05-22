package heig.pro.a05.common.spells;

/**
 * Enum to define the quality of a spell
 */
public enum Quality {
    PERFECT, GOOD, OKAY;

    public int computePower() {
        switch (this) {
            case PERFECT:
                return 10;
            case GOOD:
                return 5;
            case OKAY:
                return 3;
            default:
                return 0;
        }
    }
}
