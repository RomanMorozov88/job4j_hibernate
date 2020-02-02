package car_catalog;

import car_catalog.models.*;
import car_catalog.services.daos.*;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RunIntoDBWithMainMethod {

    private static final Logger log = Logger.getLogger(RunIntoDBWithMainMethod.class);

    public static void main(String[] args) {

        /*
        Создаютя необходимые для демонстрации объекты,
        всё это записывается в БД.
        */

        EngineDAO engineDAO = new EngineDAO();
        TransmissionDAO transmissionDAO = new TransmissionDAO();
        CarBodyDAO carBodyDAO = new CarBodyDAO();
        CarDAO carDAO = new CarDAO();
        OwnerDAO ownerDAO = new OwnerDAO();

        Engine engineBuffer = new Engine(1);
        engineBuffer.setEngineName("V4");
        engineDAO.addEntity(engineBuffer);
        engineBuffer.setEngineId(2);
        engineBuffer.setEngineName("V6");
        engineDAO.addEntity(engineBuffer);
        engineBuffer.setEngineId(3);
        engineBuffer.setEngineName("V8");
        engineDAO.addEntity(engineBuffer);

        CarBody bodyBuffer = new CarBody(1);
        bodyBuffer.setBodyName("sedan");
        carBodyDAO.addEntity(bodyBuffer);
        bodyBuffer.setBodyId(2);
        bodyBuffer.setBodyName("van");
        carBodyDAO.addEntity(bodyBuffer);

        Transmission transmissionBuffer = new Transmission(1);
        transmissionBuffer.setTransmissionName("AT");
        transmissionDAO.addEntity(transmissionBuffer);
        transmissionBuffer.setTransmissionId(2);
        transmissionBuffer.setTransmissionName("MT");
        transmissionDAO.addEntity(transmissionBuffer);

        Car carBufferOne = new Car(1, "First_Car",
                new Engine(1), new CarBody(2),
                new Transmission(1));
        Car carBufferTwo = new Car(2, "Second_Car",
                new Engine(3), new CarBody(1),
                new Transmission(1));
        Car carBufferThree = new Car(3, "Third_Car",
                new Engine(2), new CarBody(1),
                new Transmission(2));
        Owner ownerBufferOne = new Owner(1, "First_owner");
        Owner ownerBufferTwo = new Owner(2, "Second_owner");

        carBufferOne.setOwners(new HashSet<>(Arrays.asList(ownerBufferOne, ownerBufferTwo)));
        carBufferTwo.setOwners(new HashSet<>(Arrays.asList(ownerBufferTwo)));
        carBufferThree.setOwners(new HashSet<>(Arrays.asList(ownerBufferOne, ownerBufferTwo)));
        ownerBufferOne.setCars(new HashSet<>(Arrays.asList(carBufferOne, carBufferThree)));
        ownerBufferTwo.setCars(new HashSet<>(Arrays.asList(carBufferOne, carBufferTwo, carBufferThree)));

        carDAO.addEntity(carBufferOne);
        carDAO.addEntity(carBufferTwo);
        carDAO.addEntity(carBufferThree);
        ownerDAO.addEntity(ownerBufferOne);
        ownerDAO.addEntity(ownerBufferTwo);

        /*
        Теперь будем доставать из Бд разные штуки.
         */

        List<Car> allCars = carDAO.getEntities();
        log.info("\n> > > ALL CARS:");
        for (Car c : allCars) {
            log.info(" * * *");
            log.info(c.getCarId() + " : " + c.getCarName());
            log.info(c.getEngine().getEngineName() + ", "
                    + c.getCarBody().getBodyName() + ", "
                    + c.getTransmission().getTransmissionName());
            Set<Owner> set = c.getOwners();
            for (Owner o : set) {
                log.info("    " + o.getOwnerId() + " : " + o.getOwnerName());
            }
        }

        List<Owner> allOwners = ownerDAO.getEntities();
        log.info("\n> > > ALL OWNERS:");
        for (Owner o : allOwners) {
            log.info(" * * *");
            log.info(o.getOwnerId() + " : " + o.getOwnerName());
            for (Car c : o.getCars()) {
                log.info("    " + c.getCarId() + " : " + c.getCarName());
            }
        }

        log.info("\n> > > UPDATE and GET BY ID");
        Car testCar = carDAO.getEntityById(1);
        log.info(testCar.getCarName() + ", engine: " + testCar.getEngine().getEngineName());
        testCar.setCarName("First_Car_Updated");
        carDAO.addEntity(testCar);
        testCar = carDAO.getEntityById(1);
        log.info(testCar.getCarName() + ", engine: " + testCar.getEngine().getEngineName());

        log.info("\n> > > DELETE");
        engineBuffer = new Engine();
        engineBuffer.setEngineId(10);
        engineBuffer.setEngineName("For_Delete");
        engineDAO.addEntity(engineBuffer);
        log.info("Check: " + engineDAO.getEntityById(10).getEngineName());
        engineDAO.deleteEntity(10);
        engineBuffer = engineDAO.getEntityById(10);
        if (engineBuffer == null) {
            log.info("Deleted.");
        }
    }

}