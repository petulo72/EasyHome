package pt.easyhome.model;


public class Room {

	private int roomId, houseId;
	private String roomName;

    /**
     * room constructor
     * @param roomName
     * @param houseId
     */
	public Room(String roomName, int houseId) {
		this.roomName = roomName;
        this.houseId = houseId;
	}
	
	@Override
	public String toString() {
		return roomName;
	}

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getHouseId() {
        return houseId;
    }

    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
