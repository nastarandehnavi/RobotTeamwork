import lejos.hardware.sensor.EV3UltrasonicSensor;
//import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class ObstacleDetector implements Runnable{
	
	
	private DataExchange DE;
    private EV3UltrasonicSensor ultrasonicSensor;
    private SampleProvider distanceProvider;
    private float[] distanceSample;
    
    final float OBSTACLE_DISTANCE_THRESHOLD = 20.0f;
  //  private boolean obstacleDetected = false;
 
    
    public ObstacleDetector(DataExchange dataExchange) {
        this.DE = dataExchange;
        ultrasonicSensor = new EV3UltrasonicSensor(SensorPort.S2);
        distanceProvider = ultrasonicSensor.getDistanceMode();
        distanceSample = new float[distanceProvider.sampleSize()];
       
    }

	@Override
	public void run() {
		while (!DE.isStopThreads()) {
			distanceProvider.fetchSample(distanceSample, 0);
		    float distance = distanceSample[0] * 100; // Convert to centimeters
		    
            if (distance < OBSTACLE_DISTANCE_THRESHOLD) { // Adjust the threshold according  needs
  //          	 obstacleDetected = true;
            	LCD.drawString("Find Obstacle!", 1, 6);
            	//Sound.buzz();
            	
            	
            	if (DE.getFirstTime() == 0) {
            		DE.setFirstTime(System.currentTimeMillis());
            		DE.setAvoidObstacle(true);
            		DE.setCurrent_CMD(DataExchange.avoid);	
            	}
            	else {
            		DE.setSecondTime(System.currentTimeMillis());
            		long time = DE.getSecondTime() - DE.getFirstTime();
                    LCD.clear();
                    LCD.drawString("Time between:", 0, 0);
                    LCD.drawString(time / 1000 + " seconds", 0, 1);
                    DE.setCurrent_CMD(DataExchange.end);
                   // DE.setObstacleDetected(true);
                    
            	}
            	
            }	

//            else 
//            {
//  //              obstacleDetected = false;  
//            	DE.setCurrent_CMD(DataExchange.followLine);
//            }
//            
            try {
                Thread.sleep(500); // Control the frequency of checks
            } catch (InterruptedException e) {
                e.printStackTrace(); // Print error trace and exit the loop
 //               break;	
          
            }
            Delay.msDelay(100);
        }
		ultrasonicSensor.close();
    }
	
   
}
	


