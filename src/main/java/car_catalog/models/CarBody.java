package car_catalog.models;

public class CarBody {

    private int bodyId;
    private String bodyName;

    public CarBody() {
    }

    public CarBody(int bodyId) {
        this.bodyId = bodyId;
    }

    public int getBodyId() {
        return bodyId;
    }

    public void setBodyId(int bodyId) {
        this.bodyId = bodyId;
    }

    public String getBodyName() {
        return bodyName;
    }

    public void setBodyName(String bodyName) {
        this.bodyName = bodyName;
    }
}