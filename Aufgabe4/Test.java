public class Test {
    public static void main(String[] args) {
        ESubstrat s = ESubstrat.TEST;

        // compositeFormicarium
        FormicariumItem fi_compositeFormicarium = new CompositeFormicarium();
        FormicariumPart fp_compositeFormicarium = new CompositeFormicarium();
        Formicarium f_compositeFormicarium = new CompositeFormicarium();

        // formicarium
        FormicariumItem fi_formicarium = new Formicarium();
        FormicariumPart fp_formicarium = new Formicarium();

        // nest
        FormicariumPart fp_nest = new Nest();
        FormicariumItem fi_nest = new Nest();

        // antfarm
        FormicariumPart fp_antFarm = new AntFarm();
        FormicariumItem fi_antFarm = new AntFarm();

        // arena
        FormicariumPart fp_arena = new Arena();
        FormicariumItem fi_arena = new Arena();

        // thermometer
        CompatibleInstrument ci_thermometer = new Thermometer();
        Instrument i_thermometer = new Thermometer();
        FormicariumPart fp_thermometer = new Thermometer();
        FormicariumItem fi_thermometer = new Thermometer();

        // forceps
        FormicariumItem fi_forceps = new Forceps();
        Instrument i_forceps = new Forceps();
    }
}
