import java.util.ArrayList;
import java.util.List;

public class Main {

    RoomModel[][] rooms = new RoomModel[4][5];
    List<RoomModel> problemGoals1 = new ArrayList<>();
    List<RoomModel> problemGoals2 = new ArrayList<>();

    RoomModel1[][] roomsIDS = new RoomModel1[4][5];
    List<RoomModel1> problemGoalsIDS1 = new ArrayList<>();
    List<RoomModel1> problemGoalsIDS2 = new ArrayList<>();

    public static void main(String[] args) {
        Main main = new Main();

        // Just use one algorithm and one configuration at a time. Keep the calls to the other algos commented.

        // Uniform cost tree search config 1
        // uncomment below 4 lines to trigger the algo

        /*main.initializeRoomsWithConfig1();
        main.printConfig();
        UniformCostTreeSearch ucts1 = new UniformCostTreeSearch(main.rooms[1][1]);
        ucts1.ucts(main.rooms, main.problemGoals1);*/

        // Uniform cost tree search config 2
        // uncomment below 4 lines to trigger the algo

        /*main.initializeRoomsWithConfig2();
        main.printConfig();
        UniformCostTreeSearch ucts2 = new UniformCostTreeSearch(main.rooms[2][1]);
        ucts2.ucts(main.rooms, main.problemGoals2);*/


        // IDS config 1
        // uncomment below 4 lines to trigger the algo

        /*main.initializeRoomsWithConfig1();
        main.printConfig();
        IDS ids1 = new IDS(main.roomsIDS[1][1]);
        ids1.ids(main.roomsIDS, main.problemGoalsIDS1);*/

        // IDS config 2
        // uncomment below 4 lines to trigger the algo

        main.initializeRoomsWithConfig2();
        main.printConfig();
        IDS ids2 = new IDS(main.roomsIDS[2][1]);
        ids2.ids(main.roomsIDS, main.problemGoalsIDS2);


        // uniform cost graph search config 1
        // uncomment below 4 lines to trigger the algo

//        main.initializeRoomsWithConfig1();
//        main.printConfig();
//        UniformCostGraphSearch ucgs1 = new UniformCostGraphSearch(main.rooms[1][1]);
//        ucgs1.ucgs(main.rooms, main.problemGoals1);


        // uniform cost search config 2
        // uncomment below 4 lines to trigger the algo

//        main.initializeRoomsWithConfig1();
//        main.printConfig();
//        UniformCostGraphSearch ucgs2 = new UniformCostGraphSearch(main.rooms[1][1]);
//        ucgs2.ucgs(main.rooms, main.problemGoals1);
    }

    private void printConfig() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                int row = i+1;
                int col = j+1;
                String status = rooms[i][j].isRoomDirty()? "Dirty":"Clean";
                String v_status = rooms[i][j].isVacPresent()? "|Vaccuum":"        ";
                System.out.print("["+row+","+col+"]"+status+v_status+"   ");
            }
            System.out.println("");
        }
    }

    private void initializeRoomsWithConfig1() {
        basicInitialization();
        // set vacuum location
        rooms[1][1].setVacPresent(true);
        // set dirty rooms
        rooms[0][1].setRoomDirty(true);
        rooms[1][3].setRoomDirty(true);
        rooms[2][4].setRoomDirty(true);
        problemGoals1.add(rooms[0][1]);
        problemGoals1.add(rooms[1][3]);
        problemGoals1.add(rooms[2][4]);

        // set vacuum location
        roomsIDS[1][1].setVacPresent(true);
        roomsIDS[1][1].setDepth(0);
        // set dirty rooms
        roomsIDS[0][1].setRoomDirty(true);
        roomsIDS[1][3].setRoomDirty(true);
        roomsIDS[2][4].setRoomDirty(true);
        problemGoalsIDS1.add(roomsIDS[0][1]);
        problemGoalsIDS1.add(roomsIDS[1][3]);
        problemGoalsIDS1.add(roomsIDS[2][4]);
    }

    private void initializeRoomsWithConfig2() {
        basicInitialization();
        // set vacuum location
        rooms[2][1].setVacPresent(true);
        // set dirty rooms
        rooms[0][1].setRoomDirty(true);
        rooms[1][0].setRoomDirty(true);
        rooms[1][3].setRoomDirty(true);
        rooms[2][2].setRoomDirty(true);
        rooms[3][3].setRoomDirty(true);
        problemGoals2.add(rooms[0][1]);
        problemGoals2.add(rooms[1][0]);
        problemGoals2.add(rooms[1][3]);
        problemGoals2.add(rooms[2][2]);
        problemGoals2.add(rooms[3][3]);

        // set vacuum location
        roomsIDS[2][1].setVacPresent(true);
        roomsIDS[2][1].setDepth(0);
        // set dirty rooms
        roomsIDS[0][1].setRoomDirty(true);
        roomsIDS[1][0].setRoomDirty(true);
        roomsIDS[1][3].setRoomDirty(true);
        roomsIDS[2][2].setRoomDirty(true);
        roomsIDS[3][3].setRoomDirty(true);
        problemGoalsIDS2.add(roomsIDS[0][1]);
        problemGoalsIDS2.add(roomsIDS[1][0]);
        problemGoalsIDS2.add(roomsIDS[1][3]);
        problemGoalsIDS2.add(roomsIDS[2][2]);
        problemGoalsIDS2.add(roomsIDS[3][3]);
    }

    private void basicInitialization() {
        int roomNumber = 1;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                rooms[i][j] = new RoomModel(i,j,false,false);
                rooms[i][j].setRoomNumber(roomNumber++);
                rooms[i][j].setPathCost(0);


                roomsIDS[i][j] = new RoomModel1(i,j,false,false);
                roomsIDS[i][j].setRoomNumber(roomNumber++);
                roomsIDS[i][j].setPathCost(0);
            }
        }
    }
}
