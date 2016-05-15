### README.md for Team14 fyt application

- Synopsis
    - Team14's program creates a dashboard that displays the user's calories,	steps, distance, floors and heart rate for the day, and also displays more info screens to show detailed information on each of these topics. The user is also able to view their goals and select workout programs that provide a variety of suggested goals based on the workout selected.
	- The program connects to the Fitbit API to obtain the data required for each measurement.
- To clone team14's repository
	- git clone ssh://git@repo.gaul.csd.uwo.ca:7999/cs2212_w2016/team14.git

- Instructions for correct mvn package and run both normal and test cases.
    - To mvn package, set the current working directory to be "fyt"
   	- Type "mvn package"
    	- To run program in normal from "fyt" directory, type "java -jar target/team14_fyt-0.1-jar-with-dependencies.jar"
    	- To run program in test from "fyt" directory type "java -jar target/team14_fyt-0.1-jar-with-dependencies.jar test"
		- NOTE: The only difference in the above commands is that Test mode has an argument called "test" 
		
- This desktop application was developed for Microsoft Windows 7 and above 

- Link to video demonstration of the project: https://www.youtube.com/watch?v=_4DZtEVBDtI

- Documentation for the program can be found in the docs folder of the team14 repository