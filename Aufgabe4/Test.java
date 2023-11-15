import java.util.ArrayList;
import java.util.Iterator;

public class Test {
    public static void main(String[] args) {
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
        FormicariumPart fp_arena = new Arena(ESubstrat.DIRT, EContainerMaterial.PLASTIC);
        FormicariumItem fi_arena = new Arena(ESubstrat);

        // thermometer
        Thermometer ci_thermometer = new Thermometer(EUsage.SEMI);
        Instrument i_thermometer = new Thermometer(EUsage.PRO);
        FormicariumPart fp_thermometer = new Thermometer(EUsage.BEGINNER);
        FormicariumItem fi_thermometer = new Thermometer(EUsage.SEMI);

        // forceps
        FormicariumItem fi_forceps = new Forceps(EUsage.BEGINNER);
        Instrument i_forceps = new Forceps(EUsage.PRO);

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

        //iterator test
        ArrayList<FormicariumPart> fp_list = new ArrayList<>();
        fp_list.add(new Formicarium(emptyList));
        fp_list.add(new Nest());
        fp_list.add(new AntFarm(ESubstrat.GRAVEL));
        fp_list.add(new Arena(ESubstrat.DIRT,EContainerMaterial.PLASTIC));

        Formicarium f_iteratorTest = new Formicarium(fp_list);
        Iterator<FormicariumPart> f_iterator = f_iteratorTest.iterator();

        while (f_iterator.hasNext()){

        }
        assert f_iterator.hasNext();
    }
}
