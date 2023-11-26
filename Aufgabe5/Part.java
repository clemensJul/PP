public interface Part extends Rated<Part, Quality> {

    /**
     * the type of the Part will be described by the String of toString
     *
     * @return String
     */
    String toString();

    /**
     * Returns an object of type Quality. Most of the time it will be the worst object, but if two parts are not compatible return "NOTUSABLE"
     *
     * @param p P must be != null
     * @return rated Quality
     */
    Quality rated(Part p);

    /**
     * @return the Quality of the object
     */
    Quality getQuality();
}



