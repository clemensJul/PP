import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Ant implements Runnable {
    private static Ant kingAnt;
    private Position position;
    private Leaf leaf;

    public Ant(Position position) throws RuntimeException {
        if (kingAnt == null) kingAnt = this;
        this.position = position;
        // as this gets called only from one thread and arena will check against each position, this will always aquire the mutexes
        Arena.getMutex(position).forEach(m-> m.tryAcquire());

    }

    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            // try acquiring mutex of all possible neighbors
            List<Position> availablePositions = getPositions();
            // locks only positions that are not already locked by other ants
            List<Position> lockedPositions = availablePositions
                    .stream()
                    .filter(p-> Arena.getMutex(p)
                            .stream()
                            .allMatch(Semaphore::tryAcquire)
                    )
                    .toList();

            // business logic

            Position chosenNewPos = lockedPositions.size() == 0? null : lockedPositions.get((int)(Math.random()* lockedPositions.size()));

            // release every mutex that was used baring the new chosen position
            lockedPositions
                    .stream()
                    .filter(p -> p != chosenNewPos)
                    .forEach(p-> Arena.getMutex(position)
                            .forEach(Semaphore::release)
                    );
            Arena.getMutex(this.position).forEach(Semaphore::release);

            if (chosenNewPos != null){
                Arena.updateArena(this,chosenNewPos);
                this.position = chosenNewPos;
            }
            //kingAnt time
            if (this == kingAnt) Arena.drawArena();
            try {
                Thread.sleep(5+(long)(Math.random()*45));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     *
     * @return list of valid positions within cone infront of an ant. all returned positions are valid positions the the grid
     */
    private   List<Position> getPositions() {
        LinkedList<Position> positions = new LinkedList<>();

        MyVector headPos = this.position.getPos1();
        MyVector lookDirection = this.position.getPos2();
        MyVector infrontHeadPos = MyVector.add(headPos, lookDirection);


        MyVector orthogonalCounterClockwise = MyVector.orthogonalVector(lookDirection, false);
        MyVector orthogonalClockwise = MyVector.orthogonalVector(lookDirection, true);


        MyVector upLeft = MyVector.add(infrontHeadPos, orthogonalCounterClockwise);
        positions.add(new Position(MyVector.add(headPos, orthogonalCounterClockwise), upLeft));
        positions.add(new Position(upLeft, MyVector.add(MyVector.add(infrontHeadPos, orthogonalCounterClockwise), orthogonalCounterClockwise)));

        positions.add(new Position(infrontHeadPos, MyVector.add(infrontHeadPos, lookDirection)));

        MyVector upRight = MyVector.add(infrontHeadPos, orthogonalClockwise);
        positions.add(new Position(MyVector.add(headPos, orthogonalClockwise), upRight));
        positions.add(new Position(upRight, MyVector.add(MyVector.add(infrontHeadPos, orthogonalClockwise), orthogonalCounterClockwise)));

        return positions.stream().filter(Arena::validPosition).toList();
    }

    public Position getPosition(){
        return position;
    }

    public static Ant getKingAnt() {
        return kingAnt;
    }
    public static char drawAntBody(){
        return  '+';
    }
    public char drawAntHead(){

        switch (position.getDirection()){
            case UP: return 'A';
            case RIGHT: return '>';
            case DOWN: return 'V';
            default: return '<';
        }

    }
}
