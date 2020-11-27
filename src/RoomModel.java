public class RoomModel implements Comparable<RoomModel>{
    private int row;
    private int column;
    private int roomNumber;
    private boolean isRoomDirty;
    private boolean isVacPresent;
    private double pathCost;
    private RoomModel parentRoom;

    public RoomModel(int row, int column, boolean isRoomDirty, boolean isVacPresent) {
        this.row = row;
        this.column = column;
        this.isRoomDirty = isRoomDirty;
        this.isVacPresent = isVacPresent;
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

    public RoomModel getParentRoom() {
        return parentRoom;
    }

    public void setParentRoom(RoomModel parentRoom) {
        this.parentRoom = parentRoom;
    }

    @Override
    public int compareTo(RoomModel o) {
        if (this.getPathCost() > o.getPathCost()) {
            return 1;
        } else if (this.getPathCost() < o.getPathCost()) {
            return -1;
        } else return 0;
    }
}
