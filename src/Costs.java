public class Costs {

    public final static double MOVE_LEFT_COST = 1;
    public final static double MOVE_RIGHT_COST = 0.9;
    public final static double MOVE_UP_COST = 0.8;
    public final static double MOVE_DOWN_COST = 0.7;
    public final static double SUCK_DIRT_COST = 0.2;

    public enum ACTIONS{
        MOVE_UP(0),
        MOVE_DOWN(1),
        MOVE_LEFT(2),
        MOVE_RIGHT(3),
        SUCK(4);

        private final int actions;

        ACTIONS(final int newActions) {
            actions = newActions;
        }
        public int getActions() {
            return actions;
        }
    }
}
