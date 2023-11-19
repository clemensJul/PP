public interface Instrument extends FormicariumItem {
    /**
     * @return an enum-state of type EUsage quality, must be >= 0 and should be one of {@link EUsage}
     */
    int quality();
}
