import java.util.HashMap;
import java.util.LinkedList;

public class Institute {


    private LinkedList<Formicarium> inventoryForms;
    private HashMap<Formicarium,Ant> occupiedForms;

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
        Formicarium bestFit = 
        inventoryForms.forEach(formicarium -> {

        });
        return null;
    }

    /**
     *
     * @param formicarium
     */
    public void returnForm(Formicarium formicarium){

    }

    public int priceFree(){
        return 0;
    }

    public int priceOccupied(){
        return 0;
    }

    public String showFormicarium(){
        return "";
    }

    public String showAnts(){
        return "";
    }

}
