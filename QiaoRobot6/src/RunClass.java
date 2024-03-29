
public class RunClass {

	public static void main(String[] args) {
		
	DataExchange DE = new DataExchange();

	
	ColorDetector colorDetector = new ColorDetector(DE);
	MotorDrive motorDriver = new MotorDrive(DE);
	ObstacleDetector obstacleDetector = new ObstacleDetector(DE);
	//SingSongs SingSongs = new SingSongs();
	
	
	
	Thread colorDetectJob = new Thread(colorDetector);
	Thread motorDriveJob = new Thread(motorDriver);
	Thread obstacleDetectJob = new Thread(obstacleDetector);
	//Thread SingSongsJob = new Thread(SingSongs);
	
//	colorDetectJob.setDaemon(true);
//    motorDriveJob.setDaemon(true);
//    obstacleDetectJob.setDaemon(true);


	colorDetectJob.start();
	motorDriveJob.start();
	obstacleDetectJob.start();
	//SingSongsJob.start();
	
	 try {
         Thread.sleep(10000); 
     } catch (InterruptedException e) {
         e.printStackTrace();
     }
	
	}
}
	

