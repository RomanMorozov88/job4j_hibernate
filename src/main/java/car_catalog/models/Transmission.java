package car_catalog.models;

public class Transmission {

    private int transmissionId;
    private String transmissionName;

    public Transmission() {
    }

    public Transmission(int transmissionId) {
        this.transmissionId = transmissionId;
    }

    public int getTransmissionId() {
        return transmissionId;
    }

    public void setTransmissionId(int transmissionId) {
        this.transmissionId = transmissionId;
    }

    public String getTransmissionName() {
        return transmissionName;
    }

    public void setTransmissionName(String transmissionName) {
        this.transmissionName = transmissionName;
    }
}