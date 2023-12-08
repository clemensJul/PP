public class Test {
    public static void main(String[] args) {
        // institute with only unregulated forms

        {
            System.out.println("Test institute with unregulated forms");
            Institute institute = new Institute();

            //create forms
            Formicarium suf = new SmallUnregulatedFormicarium(10);
            Formicarium muf = new MediumUnregulatedFormicarium(20);
            Formicarium luf = new LargeUnregulatedFormicarium(30);

            testEquals(institute.priceOccupied(), 0.0);
            testEquals(institute.priceFree(), 0.0);

            //add forms to institute
            institute.addForm(suf);
            institute.addForm(muf);
            institute.addForm(luf);

            testEquals(institute.priceFree(), 60.0);

            // create ants
            Ant sea = new SmallEuropeanAnt();
            Ant sta = new SmallTropicalAnt();

            Ant mea = new MediumEuropeanAnt();
            Ant mta = new MediumTropicalAnt();

            Ant lea = new LargeEuropeanAnt();
            Ant lta = new LargeTropicalAnt();

            // fill institute with small ants.
            // due to the fact that there are only one unregulated form of each size,
            // the first call gets added to a SmallFormicarium, then to a medium one and at last to a large one.
            Formicarium ant1 = institute.assignForm(sea);

            testEquals(institute.priceOccupied(), 10.0);
            testEquals(institute.priceFree(), 50.0);

            Formicarium ant2 = institute.assignForm(sea);
            Formicarium ant3 = institute.assignForm(sea);
            Formicarium shoudBeNull = institute.assignForm(sea);
            if (ant1 instanceof SmallUnregulatedFormicarium) {
                System.out.println("Successful test");
            } else {
                System.out.println("Failed test");
            }

            if (ant2 instanceof MediumUnregulatedFormicarium) {
                System.out.println("Successful test");
            } else {
                System.out.println("Failed test");
            }

            if (ant3 instanceof LargeUnregulatedFormicarium) {
                System.out.println("Successful test");
            } else {
                System.out.println("Failed test");
            }

            if (shoudBeNull == null) {
                System.out.println("Successful test");
            } else {
                System.out.println("Failed test");
            }

            // free a medium formicarium
            institute.returnForm(ant2);
            shoudBeNull = institute.assignForm(lea); // so adding a large ant should not work but a medium one should work

            if (shoudBeNull == null) {
                System.out.println("Successful test");
            } else {
                System.out.println("Failed test");
            }

            Formicarium ant4 = institute.assignForm(mea);
            if (ant4 instanceof MediumUnregulatedFormicarium) {
                System.out.println("Successful test");
            } else {
                System.out.println("Failed test");
            }
            // ant2 should be a medium formicarium, so adding a large ant should not work

            // remove all forms
            institute.returnForm(ant1);
            institute.returnForm(ant3);
            institute.returnForm(ant4);

            // test if adding tropical ants works
            shoudBeNull = institute.assignForm(sta);
            if (shoudBeNull == null) {
                System.out.println("Successful test");
            } else {
                System.out.println("Failed test");
            }

            shoudBeNull = institute.assignForm(mta);
            if (shoudBeNull == null) {
                System.out.println("Successful test");
            } else {
                System.out.println("Failed test");
            }

            shoudBeNull = institute.assignForm(lta);
            if (shoudBeNull == null) {
                System.out.println("Successful test");
            } else {
                System.out.println("Failed test");
            }
        }

        // institute with only regulated forms
        {
            System.out.println("Test institute with regulated forms");
            Institute institute = new Institute();

            //create forms
            Formicarium srf = new SmallRegulatedFormicarium(12);
            Formicarium mrf = new MediumRegulatedFormicarium(24);
            Formicarium lrf = new LargeRegulatedFormicarium(36);

            //add forms to institute
            institute.addForm(srf);
            institute.addForm(mrf);
            institute.addForm(lrf);

            // create ants
            Ant sea = new SmallEuropeanAnt();
            Ant sta = new SmallTropicalAnt();

            Ant mea = new MediumEuropeanAnt();
            Ant mta = new MediumTropicalAnt();

            Ant lea = new LargeEuropeanAnt();
            Ant lta = new LargeTropicalAnt();

            // fill institute with small ants.
            // due to the fact that there are only one regulated form of each size,
            // the first call gets added to a SmallFormicarium, then to a medium one and at last to a large one.
            Formicarium ant1 = institute.assignForm(sta);
            Formicarium ant2 = institute.assignForm(sta);
            Formicarium ant3 = institute.assignForm(sta);
            Formicarium shoudBeNull = institute.assignForm(sta);
            if (ant1 instanceof SmallRegulatedFormicarium) {
                System.out.println("Successful test");
            } else {
                System.out.println("Failed test");
            }

            if (ant2 instanceof MediumRegulatedFormicarium) {
                System.out.println("Successful test");
            } else {
                System.out.println("Failed test");
            }

            if (ant3 instanceof LargeRegulatedFormicarium) {
                System.out.println("Successful test");
            } else {
                System.out.println("Failed test");
            }

            if (shoudBeNull == null) {
                System.out.println("Successful test");
            } else {
                System.out.println("Failed test");
            }

            // free a medium formicarium
            institute.returnForm(ant2);
            shoudBeNull = institute.assignForm(lta); // so adding a large ant should not work but a medium one should work

            if (shoudBeNull == null) {
                System.out.println("Successful test");
            } else {
                System.out.println("Failed test");
            }

            Formicarium ant4 = institute.assignForm(mta);
            if (ant4 instanceof MediumRegulatedFormicarium) {
                System.out.println("Successful test");
            } else {
                System.out.println("Failed test");
            }
            // ant2 should be a medium formicarium, so adding a large ant should not work

            // remove all forms
            institute.returnForm(ant1);
            institute.returnForm(ant3);
            institute.returnForm(ant4);

            // test if adding tropical ants works
            shoudBeNull = institute.assignForm(sea);
            if (shoudBeNull == null) {
                System.out.println("Successful test");
            } else {
                System.out.println("Failed test");
            }

            shoudBeNull = institute.assignForm(mea);
            if (shoudBeNull == null) {
                System.out.println("Successful test");
            } else {
                System.out.println("Failed test");
            }

            shoudBeNull = institute.assignForm(lea);
            if (shoudBeNull == null) {
                System.out.println("Successful test");
            } else {
                System.out.println("Failed test");
            }
        }

        // institute with both regulated forms
        {
            System.out.println("Test institute with both forms");
            Institute institute = new Institute();

            //create forms
            Formicarium srf = new SmallRegulatedFormicarium(12);
            Formicarium mrf = new MediumRegulatedFormicarium(24);
            Formicarium lrf = new LargeRegulatedFormicarium(36);
            Formicarium suf = new SmallUnregulatedFormicarium(10);
            Formicarium muf = new MediumUnregulatedFormicarium(20);
            Formicarium luf = new LargeUnregulatedFormicarium(30);

            //add forms to institute
            institute.addForm(srf);
            institute.addForm(mrf);
            institute.addForm(lrf);
            institute.addForm(suf);
            institute.addForm(muf);
            institute.addForm(luf);

            // create ants
            Ant sea = new SmallEuropeanAnt();
            Ant sta = new SmallTropicalAnt();

            Ant mea = new MediumEuropeanAnt();
            Ant mta = new MediumTropicalAnt();

            Ant lea = new LargeEuropeanAnt();
            Ant lta = new LargeTropicalAnt();

            // fill institute with large ants.
            Formicarium ant1 = institute.assignForm(lta);
            Formicarium shoudBeNull = institute.assignForm(lta);
            if (ant1 instanceof LargeRegulatedFormicarium) {
                System.out.println("Successful test");
            } else {
                System.out.println("Failed test");
            }

            if (shoudBeNull == null) {
                System.out.println("Successful test");
            } else {
                System.out.println("Failed test");
            }

            // add two small ones
            Formicarium ant2 = institute.assignForm(sta);
            Formicarium ant3 = institute.assignForm(sta);

            // try adding a medium one should not work because there is no space
            shoudBeNull = institute.assignForm(mta);
            if (ant2 instanceof SmallRegulatedFormicarium) {
                System.out.println("Successful test");
            } else {
                System.out.println("Failed test");
            }

            if (ant3 instanceof MediumRegulatedFormicarium) {
                System.out.println("Successful test");
            } else {
                System.out.println("Failed test");
            }

            if (shoudBeNull == null) {
                System.out.println("Successful test");
            } else {
                System.out.println("Failed test");
            }

            // free the small ant which occupies the MediumFormicarium
            institute.returnForm(ant3);
            // add a medium one should work
            Formicarium ant4 = institute.assignForm(mta); // so adding a large ant should not work but a medium one should work

            if (ant4 instanceof MediumRegulatedFormicarium) {
                System.out.println("Successful test");
            } else {
                System.out.println("Failed test");
            }

            // add unregulated ants
            Formicarium ant5 = institute.assignForm(lea);
            if (ant5 instanceof LargeUnregulatedFormicarium) {
                System.out.println("Successful test");
            } else {
                System.out.println("Failed test");
            }

            Formicarium ant6 = institute.assignForm(mea);
            if (ant6 instanceof MediumUnregulatedFormicarium) {
                System.out.println("Successful test");
            } else {
                System.out.println("Failed test");
            }
            Formicarium ant7 = institute.assignForm(sea);
            if (ant7 instanceof SmallUnregulatedFormicarium) {
                System.out.println("Successful test");
            } else {
                System.out.println("Failed test");
            }

            testEquals(institute.priceFree(), 0.0);

            System.out.println("Show Ants:");
            System.out.println(institute.showAnts());

            // remove all forms
            institute.returnForm(ant1);
            institute.returnForm(ant2);
            institute.returnForm(ant4);
            institute.returnForm(ant5);
            institute.returnForm(ant6);
            institute.returnForm(ant7);
            testEquals(institute.priceOccupied(), 0.0);

            System.out.println("Show Formicariums:");
            System.out.println(institute.showFormicarium());

        }
        System.out.println("assignForms executed: " + AssignFormAspect.getAssignFormCount());
        System.out.println("visitor calls executed: " + VisitorAspect.getVisitorMethodsCount());
    }

    public static void testEquals(Object given, Object expected) {
        if (given.equals(expected)) {
            System.out.println("Successful test");
        } else {
            System.out.println("Test NOT successful! Expected value: " + expected.toString() + " / Given " +
                    "value: " + given.toString());
        }
    }
}
