/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.uwo.csd.cs2212.team14_fyt.Main;

/**
 * This is interface for TestLifeTime
 * 
 * @authorFyt Team
 * @version 1.0
 * @since March 19, 2016
 */
public interface LifetimeModel {
    
    public void getLifetimeData();
    public int getLifetimeSteps();
    public int getLifetimeFloors();
    public double getLifetimeDistance();
    public int getBestSteps();
    public double getBestDistance();
    public double getBestFloors();
    public String getStepsDate();
    public String getFloorsDate();
    public String getDistanceDate();
}
