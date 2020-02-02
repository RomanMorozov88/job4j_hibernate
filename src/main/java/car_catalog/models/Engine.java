package car_catalog.models;

public class Engine {

    private int engineId;
    private String engineName;

    public Engine() {
    }

    public Engine(int engineId) {
        this.engineId = engineId;
    }

    public int getEngineId() {
        return engineId;
    }

    public void setEngineId(int engineId) {
        this.engineId = engineId;
    }

    public String getEngineName() {
        return engineName;
    }

    public void setEngineName(String engineName) {
        this.engineName = engineName;
    }
}