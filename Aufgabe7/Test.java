public class Test {
    public static void main(String[] args) {
        {
            System.out.println("Test");
            Institute institute = new Institute();

            //create forms
            Formicarium srf = new SmallRegulatedFormicarium(12);
            Formicarium suf = new SmallUnregulatedFormicarium(10);

            Formicarium mrf = new MediumRegulatedFormicarium(24);
            Formicarium muf = new MediumUnregulatedFormicarium(20);

            Formicarium luf = new LargeUnregulatedFormicarium(30);
            Formicarium lrf = new LargeRegulatedFormicarium(36);

            //add forms to institute

            institute.addForm(srf);
            institute.addForm(suf);
            institute.addForm(mrf);
            institute.addForm(muf);
            institute.addForm(lrf);
            institute.addForm(luf);

            // create ants
            Ant sea = new SmallEuropeanAnt();
            Ant sta = new SmallTropicalAnt();

            Ant mea = new MediumEuropeanAnt();
            Ant mta = new MediumTropicalAnt();

            Ant lea = new LargeEuropeanAnt();
            Ant lta = new LargeTropicalAnt();

            institute.assignForm(sea);
            institute.assignForm(sea);
            institute.assignForm(sea);
            Formicarium shoudBeNull = institute.assignForm(sea);
            System.out.println();
        }
    }
}
