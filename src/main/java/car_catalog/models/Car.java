package car_catalog.models;

import java.util.Set;

public class Car {

    private int carId;
    private String carName;
    private Engine engine;
    private CarBody carBody;
    private Transmission transmission;
    //т.к. в маппинге указан <set>
    private Set<Owner> owners;

    public Car() {
    }

    public Car(int carId, String carName, Engine engine, CarBody carBody, Transmission transmission) {
        this.carId = carId;
        this.carName = carName;
        this.engine = engine;
        this.carBody = carBody;
        this.transmission = transmission;
    }

    public Car(int carId) {
        this.carId = carId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public CarBody getCarBody() {
        return carBody;
    }

    public void setCarBody(CarBody carBody) {
        this.carBody = carBody;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    public Set<Owner> getOwners() {
        return owners;
    }

    public void setOwners(Set<Owner> owners) {
        this.owners = owners;
    }
}