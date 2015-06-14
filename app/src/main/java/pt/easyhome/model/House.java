package pt.easyhome.model;


public class House {

    private int houseId, housePort, serverPort;
    private String houseName, houseIpAddress, houseMacAdress, serverIpAdress, password;


    public House(String houseName, String houseIpAddress, int housePort, String houseMacAdress, String serverIpAdress, int serverPort,  String password) {
        this.houseIpAddress = houseIpAddress;
        this.housePort = housePort;
        this.houseName = houseName;
        this.password = password;
        this.houseMacAdress = houseMacAdress;
        this.serverIpAdress = serverIpAdress;
        this.serverPort = serverPort;
    }

    public int getHouseId() {
        return houseId;
    }

    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }

    public int getHousePort() {
        return housePort;
    }

    public void setHousePort(int housePort) {
        this.housePort = housePort;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getHouseIpAddress() {
        return houseIpAddress;
    }

    public void setHouseIpAddress(String houseIpAddress) {
        this.houseIpAddress = houseIpAddress;
    }

    public String getHouseMacAdress() {
        return houseMacAdress;
    }

    public void setHouseMacAdress(String houseMacAdress) {
        this.houseMacAdress = houseMacAdress;
    }

    public String getServerIpAdress() {
        return serverIpAdress;
    }

    public void setServerIpAdress(String serverIpAdress) {
        this.serverIpAdress = serverIpAdress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
