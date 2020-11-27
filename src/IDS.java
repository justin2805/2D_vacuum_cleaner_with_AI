import java.text.DecimalFormat;
import java.util.*;

public class IDS {
    RoomModel1 startingNode;
    Queue<RoomModel1> fringe = new LinkedList<>();
    List<RoomModel1> expansionList = new ArrayList<>();
    List<RoomModel1> expansionList_shadow = new ArrayList<>();
    List<RoomModel1> solutionList = new ArrayList<>();
    List<RoomModel1> mProblemGoals;
    private int expandedNodes = 0;
    private int generatedNodes = 0;
    boolean roomCleaned = false;
    double solutionCost;
    boolean incrementDepth = false;

    public IDS(RoomModel1 startingNode) {
        this.startingNode = startingNode;
        startingNode.setParentRoom(null);
        this.startingNode.setParentRoom(null);
        addNewNode(startingNode, 0, null);
    }

    public RoomModel1 ids(RoomModel1[][] problemSet, List<RoomModel1> problemGoals) {
        mProblemGoals = problemGoals;

        Date startTime = new Date();
        System.out.println("Starting Iterative Deepening Tree Search Algorithm");
        if (problemSet == null) {
            System.out.println("Problem set null. Algo Failed");
            return null;
        }

        int loop = 1;
        int depth = 0;
        while (true) {
            incrementDepth = false;
            System.out.println("Depth at " + depth);

            while (true) {
//                System.out.println("Iteration depth " + loop);
                if (fringe.isEmpty()) {
//                    System.out.println("Fringe is empty. Algo Failed");
//                    return null;
                    System.out.println("Empty fringe");
                    break;
                }


                RoomModel1 node = fringe.poll();
                int r = node.getRow() + 1;
                int c = node.getColumn() + 1;
                double pc = node.getPathCost();
                System.out.println("Node: N[" + r + "][" + c + "] Path cost: " + pc+" : depth: "+node.getDepth());
                if (pc != -1) {
                    solutionCost = pc;
                }

                // get all options [L,R,U,D,S] and go for cheapest
                double currentCost = node.getPathCost();
                double moveUpCost = ((node.getRow() - 1) >= 0 && depth != 0) ? (Costs.MOVE_UP_COST + currentCost) : -1;
                double moveDownCost = ((node.getRow() + 1) < 4 && depth != 0) ? (Costs.MOVE_DOWN_COST + currentCost) : -1;
                double moveLeftCost = ((node.getColumn() - 1) >= 0 && depth != 0) ? (Costs.MOVE_LEFT_COST + currentCost) : -1;
                double moveRightCost = ((node.getColumn() + 1) < 5 && depth != 0) ? (Costs.MOVE_RIGHT_COST + currentCost) : -1;
                double suckCost = (node.isRoomDirty()) ? (Costs.SUCK_DIRT_COST + currentCost) : -1;


                RoomModel1 newNode_up = null;
                RoomModel1 newNode_down = null;
                RoomModel1 newNode_left = null;
                RoomModel1 newNode_right = null;


                if (node.isRoomDirty()) {
                    node.setRoomDirty(false);
                    node.setVacPresent(true);
                    node.setPathCost(suckCost);
                    System.out.println("Goal state------------------------ ");
                    roomCleaned = true;
                    this.startingNode = node;

                    RoomModel1 localNode = node;

                    List<RoomModel1> tempList = new ArrayList<>();
                    while (localNode != null && !tempList.contains(localNode)) {
                        int row = localNode.getRow() + 1;
                        int col = localNode.getColumn() + 1;
                        tempList.add(localNode);
                        localNode = localNode.getParentRoom();
                    }
                    Collections.reverse(tempList);
                    solutionList.addAll(tempList);
                }


                // success
                if (checkGoalState(problemSet)) {
                    solutionCost = solutionCost + 0.2;
                    System.out.println("Success. Goal state achieved with Uniform Cost Search Algorithm.");
                    long timeToRun = (new Date().getTime() - startTime.getTime());
                    System.out.println("Time required to run = " + timeToRun + " ms.");
                    System.out.println("First 5 nodes in the order of their expansion");
                    int i = 0;
                    for (RoomModel1 rRoom : expansionList_shadow) {
                        if (i == 5) break;
                        int row = rRoom.getRow() + 1;
                        int col = rRoom.getColumn() + 1;
                        System.out.print("R[" + row + "][" + col + "] -->");
                        i++;
                    }
                    System.out.println("||");
                    System.out.println("Total number of expanded nodes " + expandedNodes);
                    System.out.println("Total number of generated nodes " + generatedNodes);

                    RoomModel1 finalNode = null;

                    System.out.println("Expansion List");
                    for (RoomModel1 room : expansionList_shadow) {
                        int row = room.getRow() + 1;
                        int col = room.getColumn() + 1;
                        int parent_row = room.getParentRoom() == null ? 0 : room.getParentRoom().getRow() + 1;
                        int parent_col = room.getParentRoom() == null ? 0 : room.getParentRoom().getColumn() + 1;
                        System.out.print("room[" + row + "][" + col + "] -->");

                    }
                    System.out.println("||");

                    // print solution
                    printNodes(solutionList);

                    return node;
                } else if (roomCleaned) {
                    roomCleaned = false;
                    fringe.clear();
                    depth = 0;
                    if (moveUpCost != -1) moveUpCost = moveUpCost + 0.2;
                    if (moveDownCost != -1) moveDownCost = moveDownCost + 0.2;
                    if (moveLeftCost != -1) moveLeftCost = moveLeftCost + 0.2;
                    if (moveRightCost != -1) moveRightCost = moveRightCost + 0.2;
                }
                expansionList.add(node);
                expandedNodes++;

                if (node.getDepth() == depth) {
                    System.out.println("depth equal continue");
                    continue;
                }

                // add to the fringe

                if (moveUpCost != -1) {
                    newNode_up = problemSet[node.getRow() - 1][node.getColumn()];
                    addNewNode(newNode_up, moveUpCost, node);
                }
                if (moveDownCost != -1) {
                    newNode_down = problemSet[node.getRow() + 1][node.getColumn()];
                    addNewNode(newNode_down, moveDownCost, node);
                }
                if (moveLeftCost != -1) {
                    newNode_left = problemSet[node.getRow()][node.getColumn() - 1];
                    addNewNode(newNode_left, moveLeftCost, node);
                }
                if (moveRightCost != -1) {
                    newNode_right = problemSet[node.getRow()][node.getColumn() + 1];
                    addNewNode(newNode_right, moveRightCost, node);
                }


                int element = 1;
                for (RoomModel1 room : fringe) {
                    int row = room.getRow() + 1;
                    int col = room.getColumn() + 1;
                    int parent_row = room.getParentRoom() == null ? 0 : room.getParentRoom().getRow() + 1;
                    int parent_col = room.getParentRoom() == null ? 0 : room.getParentRoom().getColumn() + 1;
                    System.out.println("Fringe element " + element + " : room[" + row + "][" + col + "] with path cost: " + room.getPathCost() + "" +
                            " : Parent R[" + parent_row + "][" + parent_col + "] depth : "+room.getDepth());
                    element++;
                }
            }
            System.out.println("\n\n ---------------CLEAR--------------------\n");
            fringe.clear();
            expansionList_shadow.addAll(expansionList);
            expansionList.clear();
//            expandedNodes = 0;
//            generatedNodes = 0;
            solutionCost = 0;
            depth++;
            incrementDepth = true;
            this.startingNode.setDepth(0);
            fringe.add(this.startingNode);
//            addNewNode(this.startingNode,0, null);
        }

    }

    private void addNewNode(RoomModel1 newNode, double cost, RoomModel1 parentNode) {
        double pathcost = newNode.getPathCost();
        DecimalFormat df = new DecimalFormat("0.00");
        if (!isNodeInFringe(newNode.getRoomNumber()) && !isNodeInList(newNode.getRoomNumber())
                && (newNode.getPathCost() <= cost || incrementDepth)) {
            newNode.setDepth(parentNode == null? 0:parentNode.getDepth()+1);
            newNode.setPathCost(Double.parseDouble((df.format(cost))));
            newNode.setParentRoom(parentNode);
            int room = newNode.getRow() + 1;
            int col = newNode.getColumn() + 1;
            System.out.println("Added : N [" + room + "][" + col + "]  ->" + newNode.getPathCost()+" : depth: "+newNode.getDepth());
            generatedNodes++;
            fringe.add(newNode);
        }
    }

//    private static final Comparator<RoomModel1> costComparator = new Comparator<RoomModel1>() {
//        @Override
//        public int compare(RoomModel1 o1, RoomModel1 o2) {
//            return (int) (o1.getPathCost() - o2.getPathCost());
//        }
//    };

    private boolean checkGoalState(RoomModel1[][] problemSet) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (problemSet[i][j].isRoomDirty())
                    return false;
            }
        }
        return true;
    }

    private boolean isNodeInFringe(int roomNumber) {
        for (RoomModel1 roomModel : fringe) {
            if (roomModel.getRoomNumber() == roomNumber) {
                return true;
            }
        }
        return false;
    }

    private boolean isNodeInList(int roomNumber) {
        for (RoomModel1 roomModel : expansionList) {
            if (roomModel.getRoomNumber() == roomNumber) {
                return true;
            }
        }
        return false;
    }

    private void printNodes(List<RoomModel1> solutionList) {
        Collections.reverse(solutionList);
        System.out.println("Cost of solution : "+solutionList.get(0).getPathCost());
        System.out.println("Solution:: ");

        for (RoomModel1 roomModel: solutionList) {
            int row = roomModel.getRow() + 1;
            int col = roomModel.getColumn() + 1;
            System.out.print("Room[" + row + "][" + col + "] -->");
        }
        System.out.println("||");
        System.out.println("Number of moves in the solution: "+solutionList.size());


        /*RoomModel1 localNode = node;
        int iterations = 0;
        System.out.println("Cost of solution: " + solutionCost);

        System.out.println("Solution:: ");
        while (localNode != null) {
            int row = localNode.getRow() + 1;
            int col = localNode.getColumn() + 1;
            System.out.print("Room[" + row + "][" + col + "] -->");
            localNode = localNode.getParentRoom();
            iterations++;
        }
        System.out.println("||");
        System.out.println("Number of moves in solution:: " + iterations);*/

    }
}
