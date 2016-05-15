package ca.uwo.csd.cs2212.team14_fyt.Main;

import ca.uwo.csd.cs2212.team14_fyt.Twitter.Tweet;
import twitter4j.TwitterException;

import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 * This class is for GUI + Data combining section
 * 
 * @author Fyt Team
 * @version  1.0
 * @since 2016-02-25
 */
public class Panel extends JPanel implements Runnable{

	//dimensions
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;

	/**
	 * Constructor to create panels 
	 * specefied by the value
	 * @param value The number of panels to be created
	 */
	public Panel(int value){
		super();
		setPreferredSize(new Dimension (WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
		initTest();
	}


	/**
	 * Constructor to create a panel
	 */
	public Panel(){
		super();
		setPreferredSize(new Dimension (WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
	}

	public void run() {
		// TODO Auto-generated method stub

	}

	/**
	 * Method to print out test values for the test data
	 */
	public void initTest(){
		TestDataDaily test = new TestDataDaily();
		System.out.println("Goal Active Minutes: " + test.getGoalsActiveMinutes());
		System.out.println("Summary Active Minutes: " + test.getSummaryFairlyActiveMinutes());
	}
}
