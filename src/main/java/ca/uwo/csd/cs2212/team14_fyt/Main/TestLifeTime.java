package ca.uwo.csd.cs2212.team14_fyt.Main;

/**
 * Class to test the lifetime data elements without having to use the API
 * 
 * @author Fyt Team
 * @version 1.0
 * @since March 15, 2016
 */
public class TestLifeTime implements LifetimeModel{
    //declare and init variables for lifetime totals
    private int lifetimeSteps = 333999;
    private int lifetimeFloors = 680;
    private double lifetimeDistance = 249.59;

    //declare and init variables for lifetime bests
    private int bestSteps = 14709;
    private double bestFloors = 34.00;
    private double bestDistance = 12.354;
    private String stepsDate = "2016-05-11";
    private String floorsDate = "2015-11-01";
    private String distanceDate = "2014-01-01";
    
    public void getLifetimeData() {}

    //===================== Getter methods for total ===================
    /**
     * Method to return the value of the user's lifetime steps taken
     * @return int This method returns the value stored in the private variable lifetimeSteps
     */
    public int getLifetimeSteps() {
        return lifetimeSteps;
    }

    /**
     * Method to return the value of the user's lifetime floors climbed
     * @return int This method returns the value stored in the private variable lifetimeFloors
     */
    public int getLifetimeFloors() {
        return lifetimeFloors;
    }

    /**
     * Method to return the value of the user's lifetime distance travelled
     * @return double This method returns the value stored in the private variable lifetimeDistance
     */
    public double getLifetimeDistance() {
        return lifetimeDistance;
    }
    //=================== End of total getter methods ==================

    //=================== Getter methods for best ======================
    /**
     * Method to return the value of the user's best day for steps taken
     * @return int This method returns the value stored in the private variable bestSteps
     */
    public int getBestSteps() {
        return bestSteps;
    }

    /**
     * Method to return the value of the user's best day for floors climbed
     * @return int This method returns the value stored in the private variable bestFloors
     */
    public double getBestFloors() {
        return bestFloors;
    }

    /**
     * Method to return the value of the user's best day for distance traveled
     * @return double This method returns the value stored in the private variable lifetimeSteps
     */
    public double getBestDistance() {
        return bestDistance;
    }

    /**
     * Method to return the date the user achieved their best day for steps taken
     * @return String This method returns the value stored in the private variable stepsDate
     */
    public String getStepsDate() {
        return stepsDate;
    }

    /**
     * Method to return the date the user achieved their best day for floors climbed
     * @return String This method returns the value stored in the private variable floorsDate
     */
    public String getFloorsDate() {
        return floorsDate;
    }

    /**
     * Method to return the date the user achieved their best day for distance traveled
     * @return String This method returns the value stored in the private variable distanceDate
     */
    public String getDistanceDate() {
        return distanceDate;
    }
    //=================== End of best methods ===========================
}
