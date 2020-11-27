import java.text.DecimalFormat;
import java.util.*;

public class UniformCostTreeSearch {
    RoomModel startingNode;
    Queue<RoomModel> fringe = new PriorityQueue<>();
    List<RoomModel> expansionList = new ArrayList<>();
    List<RoomModel> mProblemGoals;
    private int expandedNodes = 0;
    private int generatedNodes = 0;
    boolean roomCleaned = false;
    double solutionCost;

    public UniformCostTreeSearch(RoomModel startingNode) {
        this.startingNode = startingNode;
        startingNode.setParentRoom(null);
        this.startingNode.setParentRoom(null);
        addNewNode(startingNode,0, null);
    }

    public RoomModel ucts(RoomModel[][] problemSet, List<RoomModel> problemGoals){
        mProblemGoals = problemGoals;

        Date startTime = new Date();
        System.out.println("Starting Uniform Cost Tree Search Algorithm");
        if (problemSet == null) {
            System.out.println("Problem set null. Algo Failed");
            return null;
        }

        int loop = 1;
        while (true) {
            System.out.println("Iteration "+loop++);
            if (fringe.isEmpty()) {
                System.out.println("Fringe is empty. Algo Failed");
                return null;
            }

            RoomModel node = fringe.poll();
            int r = node.getRow()+1;
            int c = node.getColumn()+1;
            double pc = node.getPathCost();
            System.out.println("Node: N["+r+"]["+c+"] Path cost: "+pc);
            if (pc != -1) {
                solutionCost = pc;
            }

            // get all options [L,R,U,D,S] and go for cheapest
            double currentCost = node.getPathCost();
            double moveUpCost = (node.getRow()-1) >= 0? (Costs.MOVE_UP_COST + currentCost) : -1;
            double moveDownCost = (node.getRow()+1) < 4? (Costs.MOVE_DOWN_COST + currentCost) : -1;
            double moveLeftCost = (node.getColumn()-1) >= 0? (Costs.MOVE_LEFT_COST + currentCost) : -1;
            double moveRightCost = (node.getColumn()+1) < 5? (Costs.MOVE_RIGHT_COST + currentCost) : -1;
            double suckCost = (node.isRoomDirty())? (Costs.SUCK_DIRT_COST + currentCost): -1;


            RoomModel newNode_up = null;
            RoomModel newNode_down = null;
            RoomModel newNode_left = null;
            RoomModel newNode_right = null;



            if (node.isRoomDirty()) {
                node.setRoomDirty(false);
                node.setVacPresent(true);
                node.setPathCost(suckCost);
                System.out.println("Goal state------------------------ ");
                roomCleaned = true;
            }

            // success
            if (checkGoalState(problemSet)) {
                solutionCost = solutionCost+0.2;
                System.out.println("Success. Goal state achieved with Uniform Cost Search Algorithm.");
                long timeToRun = (new Date().getTime() - startTime.getTime());
                System.out.println("Time required to run = "+timeToRun+" ms.");
                System.out.println("First 5 nodes in the order of their expansion");
                int i = 0;
                for (RoomModel rRoom: expansionList) {
                    if (i==5) break;
                    int row = rRoom.getRow()+1;
                    int col = rRoom.getColumn()+1;
                    System.out.print("R["+row+"]["+col+"] -->");
                    i++;
                }
                System.out.println("||");
                System.out.println("Total number of expanded nodes "+expandedNodes);
                System.out.println("Total number of generated nodes "+generatedNodes);

                RoomModel finalNode = null;

                System.out.println("Expansion List");
                for (RoomModel room: expansionList) {
                    int row = room.getRow()+1;
                    int col = room.getColumn()+1;
                    int parent_row = room.getParentRoom() == null? 0: room.getParentRoom().getRow() +1;
                    int parent_col = room.getParentRoom() == null? 0: room.getParentRoom().getColumn() +1;
                    System.out.print("room["+row+"]["+col+"] -->");

                }
                System.out.println("||");

                // print solution
                printNodes(node);

                return node;
            } else if (roomCleaned) {
                roomCleaned = false;
                fringe.clear();
                if (moveUpCost != -1) moveUpCost = moveUpCost +0.2;
                if (moveDownCost != -1) moveDownCost = moveDownCost +0.2;
                if (moveLeftCost != -1) moveLeftCost = moveLeftCost +0.2;
                if (moveRightCost != -1) moveRightCost = moveRightCost +0.2;
            }


            // add to the fringe

            if (moveUpCost != -1) {
                newNode_up = problemSet[node.getRow()-1][node.getColumn()];
                addNewNode(newNode_up,moveUpCost, node);
            }
            if (moveDownCost != -1) {
                newNode_down = problemSet[node.getRow()+1][node.getColumn()];
                addNewNode(newNode_down,moveDownCost, node);
            }
            if (moveLeftCost != -1) {
                newNode_left = problemSet[node.getRow()][node.getColumn()-1];
                addNewNode(newNode_left,moveLeftCost, node);
            }
            if (moveRightCost != -1) {
                newNode_right = problemSet[node.getRow()][node.getColumn()+1];
                addNewNode(newNode_right,moveRightCost, node);
            }
            expansionList.add(node);
            expandedNodes++;

            int element = 1;
            for (RoomModel room: fringe) {
                int row = room.getRow()+1;
                int col = room.getColumn()+1;
                int parent_row = room.getParentRoom() == null? 0: room.getParentRoom().getRow() +1;
                int parent_col = room.getParentRoom() == null? 0: room.getParentRoom().getColumn() +1;
                System.out.println("Fringe element "+element+" : room["+row+"]["+col+"] with path cost: "+room.getPathCost()+"" +
                        " : Parent R["+parent_row+"]["+parent_col+"]");
                element++;
            }
        }
    }

    private void addNewNode(RoomModel newNode, double cost, RoomModel parentNode){
        double pathcost = newNode.getPathCost();
        DecimalFormat df = new DecimalFormat("0.00");
        if (!isNodeInFringe(newNode.getRoomNumber()) && !isNodeInList(newNode.getRoomNumber())
                && newNode.getPathCost()<=cost) {
            newNode.setPathCost(Double.parseDouble((df.format(cost))));
            newNode.setParentRoom(parentNode);
            int room = newNode.getRow()+1;
            int col = newNode.getColumn()+1;
            System.out.println("Added : N ["+room+"]["+col+"]  ->"+newNode.getPathCost());
            generatedNodes++;
            fringe.add(newNode);
        }
    }

    private static final Comparator<RoomModel> costComparator = new Comparator<RoomModel>() {
        @Override
        public int compare(RoomModel o1, RoomModel o2) {
            return (int) (o1.getPathCost() - o2.getPathCost());
        }
    };

    private boolean checkGoalState(RoomModel[][] problemSet) {
        for (int i = 0; i <4 ; i++) {
            for (int j = 0; j<5; j++) {
                if (problemSet[i][j].isRoomDirty())
                    return false;
            }
        }
        return true;
    }

    private boolean isNodeInFringe(int roomNumber) {
        for (RoomModel roomModel: fringe) {
            if (roomModel.getRoomNumber() == roomNumber) {
                return true;
            }
        }
        return false;
    }

    private boolean isNodeInList(int roomNumber) {
        for (RoomModel roomModel: expansionList) {
            if (roomModel.getRoomNumber() == roomNumber) {
                return true;
            }
        }
        return false;
    }

    private void printNodes(RoomModel node) {
        RoomModel localNode = node;
        int iterations = 0;
        System.out.println("Cost of solution: "+solutionCost);

        System.out.println("Solution:: ");
        while (localNode != null) {
                int row = localNode.getRow() +1;
                int col = localNode.getColumn() +1;
                System.out.print("Room["+row+"]["+col+"] -->");
                localNode = localNode.getParentRoom();
                iterations++;
        }
        System.out.println("||");
        System.out.println("Number of moves in solution:: "+iterations);

    }
}
