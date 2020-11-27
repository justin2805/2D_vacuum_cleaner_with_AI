public class RoomModel1{
    private int row;
    private int column;
    private int roomNumber;
    private boolean isRoomDirty;
    private boolean isVacPresent;
    private double pathCost;
    private RoomModel1 parentRoom;
    private int depth;

    public RoomModel1(int row, int column, boolean isRoomDirty, boolean isVacPresent) {
        this.row = row;
        this.column = column;
        this.isRoomDirty = isRoomDirty;
        this.isVacPresent = isVacPresent;
        this.depth = -1;
    }

    public boolean isRoomDirty() {
        return isRoomDirty;
    }

    public void setRoomDirty(boolean roomDirty) {
        isRoomDirty = roomDirty;
    }

    public boolean isVacPresent() {
        return isVacPresent;
    }

    public void setVacPresent(boolean vacPresent) {
        isVacPresent = vacPresent;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public double getPathCost() {
        return pathCost;
    }

    public void setPathCost(double pathCost) {
        this.pathCost = pathCost;
    }

    public RoomModel1 getParentRoom() {
        return parentRoom;
    }

    public void setParentRoom(RoomModel1 parentRoom) {
        this.parentRoom = parentRoom;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
}
