import java.util.HashMap;
import java.util.LinkedList;

public class Institute {
    private final LinkedList<Formicarium> inventoryForms;
    private final HashMap<Formicarium,Ant> occupiedForms;

    public Institute(){
        this.inventoryForms = new LinkedList<>();
        this.occupiedForms = new HashMap<>();
    }

    /**
     * add a new formicarium to the inventory
     * @param formicarium
     */
    public void addForm(Formicarium formicarium){
        inventoryForms.add(formicarium);
    }

    /**
     * deletes a formicarium that is present from the inventory
     * @param formicarium
     */
    public void deleteForm(Formicarium formicarium){
        inventoryForms.remove(formicarium);
    }


    /**
     * returns a fitting Formicarium and removes it from the inventory
     * @param ant for which the most fitting Formicarium is selected
     * @return returns a fitting Formicarium or null if nothing is found
     */
    public Formicarium assignForm(Ant ant){
        Fitable bestFit = Fitable.TOO_SMALL;
        Formicarium bestFitFormicarium = null;

        for (Formicarium formicarium : inventoryForms) {
            Fitable currentFit = ant.fits(formicarium);
            if (currentFit == Fitable.PERFECT) {
                bestFitFormicarium = formicarium;
                bestFit = Fitable.PERFECT;
                break;
            } else if (bestFit.ordinal() < currentFit.ordinal()) {
                bestFit = currentFit;
                bestFitFormicarium = formicarium;
            }
        }

        if (bestFit == Fitable.TOO_SMALL) {
            return null;
        }
        else {
            inventoryForms.remove(bestFitFormicarium);
            occupiedForms.put(bestFitFormicarium,ant);
            return bestFitFormicarium;
        }
    }

    /**
     * removes the mapping of a formicarium to a ant and adds it back to the inventory
     * @param formicarium
     */
    public void returnForm(Formicarium formicarium){
        if(occupiedForms.remove(formicarium) != null){
            inventoryForms.add(formicarium);
        }
    }

    /**
     *
     * @return sum of prices of the available formicariums
     */
    public double priceFree(){
        return inventoryForms.stream().mapToDouble(Formicarium::price).sum();
    }

    /**
     * @return sum of prices of occupied formicariums
     */
    public double priceOccupied(){
        return occupiedForms.keySet().stream().mapToDouble(Formicarium::price).sum();
    }

    /**
     * @return every formicarium in a list with its corresponding information
     */
    public String showFormicarium(){
        StringBuilder result = new StringBuilder();
        inventoryForms.forEach(form -> result.append(form).append("\n"));
        return result.toString();
    }

    /**
     *
     *@return every ant with its information and what formicarium it is associated with
     */
    public String showAnts(){
        StringBuilder result = new StringBuilder();
        occupiedForms.forEach((key, value) -> result.append(value).append("\t").append(key));
        return result.toString();
    }
}
