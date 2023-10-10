import java.util.ArrayList;
import java.util.LinkedList;

public interface Cell extends Entity {
    double scent = 0;
    LinkedList<Ant> ants = new LinkedList<>();

    //returns length of ants
    int numberOfAntsPresent();
    LinkedList<Ant> getAnts();

}
