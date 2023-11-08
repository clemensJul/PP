import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        ESubstrat s = ESubstrat.TEST;

        ArrayList<FormicariumPart> emptyList = new ArrayList<>();

        // compositeFormicarium
        FormicariumItem fi_compositeFormicarium = new CompositeFormicarium(emptyList);
        FormicariumPart fp_compositeFormicarium = new CompositeFormicarium(emptyList);
        Formicarium f_compositeFormicarium = new CompositeFormicarium(emptyList);

        // formicarium
        FormicariumItem fi_formicarium = new Formicarium(emptyList);
        FormicariumPart fp_formicarium = new Formicarium(emptyList);

        // nest
        FormicariumPart fp_nest = new Nest();
        FormicariumItem fi_nest = new Nest();

        // antfarm
        FormicariumPart fp_antFarm = new AntFarm(ESubstrat.GRAVEL);
        FormicariumItem fi_antFarm = new AntFarm(ESubstrat.GRAVEL);

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

        ArrayList<FormicariumItem> items = new ArrayList<>();
        items.add(fi_compositeFormicarium);
        items.add(fp_compositeFormicarium);
        items.add(f_compositeFormicarium);
        items.add(fi_formicarium);
        items.add(fp_formicarium);
        items.add(fp_nest);
        items.add(fi_nest);
        items.add(fi_antFarm);
        items.add(fp_antFarm);
        items.add(fi_arena);
        items.add(fp_arena);
        items.add(ci_thermometer);
        items.add(i_thermometer);
        items.add(fp_thermometer);
        items.add(fi_thermometer);
        items.add(fi_forceps);
        items.add(i_forceps);
    }
}
