public interface Part extends Rated<Part, Quality> {

    /**
     * the type of the Part will be described by the String of toString
     *
     * @return String
     */
    String toString();

    /**
     * returns an object of type Quality. Most of the time it will be a worst object, but if two parts are not compatible return "NOTUSABLE"
     *
     * @param p P must be != null
     * @return
     */
    Quality rated(Part p);
}



