public class Quality implements Calc<Quality> {
    public static final Quality NOT_USABLE = new Quality(0);
    public static final Quality BEGINNER = new Quality(1);
    public static final Quality SEMIPRO = new Quality(2);
    public static final Quality PRO = new Quality(3);
    private final int quality;

    private enum EQuality {
        NOT_USABLE,
        BEGINNER,
        SEMIPRO,
        PRO
    }

    /**
     * @param quality is only "NOT_USABLE","BEGINNER","SEMIPRO" and "PRO possible
     * @return the quality object
     */
    public static Quality getQuality(String quality) {
        switch(EQuality.valueOf(quality)) {
            case NOT_USABLE -> {
                return NOT_USABLE;
            }
            case BEGINNER -> {
                return BEGINNER;
            }
            case SEMIPRO -> {
                return SEMIPRO;
            }
            case PRO -> {
                return PRO;
            }
        }
        return null;
    }

    /**
     * @param quality is only "NOT_USABLE","BEGINNER","SEMIPRO" and "PRO possible
     * @return the quality object
     */
    public static Quality getQuality(int quality) {
        switch(quality) {
            case 0 -> {
                return NOT_USABLE;
            }
            case 1 -> {
                return BEGINNER;
            }
            case 2 -> {
                return SEMIPRO;
            }
            case 3 -> {
                return PRO;
            }
        }
        return null;
    }

    /**
     * @param quality must be between (including) 0 and 3
     */
    private Quality(int quality) {
        this.quality = quality;
    }

    /**
     * returns the worst quality
     *
     * @param add must not be null
     * @return
     */
    @Override
    public Quality sum(Quality add) {
        return this.quality >= add.quality ? add : this;
    }

    /**
     * returns itself without looking at the parameter
     *
     * @param ratio must not be null
     * @return
     */
    @Override
    public Quality ratio(int ratio) {
        return this;
    }

    /**
     * returns if this is at least as good as compareTo
     *
     * @param compareTo must not be null
     * @return
     */
    @Override
    public boolean atLeast(Quality compareTo) {
        return this.quality >= compareTo.quality;
    }

    @Override
    public String toString() {
        return "the quality is " + EQuality.values()[quality].toString();
    }
}
