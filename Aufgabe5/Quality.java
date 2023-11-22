public class Quality implements Calc<Quality> {

    private static Quality[] qualities = new Quality[4];
    private int quality;

    private enum EQuality {
        NOTUSABLE,
        BEGINNER,
        SEMIPRO,
        PRO
    }

    /**
     * @param quality is only "NOTUSABLE","BEGINNER","SEMIPRO" and "PRO possible
     * @return the quality object
     */
    public static Quality getQuality(String quality) {

        Quality output;
        int cardinality = EQuality.valueOf(quality).ordinal();
        if (qualities[cardinality] == null) new Quality(cardinality);
        output = qualities[cardinality];
        return output;
    }

    /**
     * @param quality must be between (including) 0 and 3
     */
    private Quality(int quality) {
        qualities[quality] = this;
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
