//import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
//import lejos.hardware.motor.UnregulatedMotor;
//import lejos.hardware.port.MotorPort;
//import lejos.utility.Delay;

public class MotorDrive implements Runnable{
	

    private DataExchange DE;
//    private UnregulatedMotor motorA; 
//    private UnregulatedMotor motorB; 
    
    final float EDGE_LOWER_THRESHOLD = 15.0f;
    final float EDGE_UPPER_THRESHOLD = 21.0f;
//    final float OBSTACLE_DISTANCE_THRESHOLD = 20.0f; // Adjust as needed

 
    
    public MotorDrive(DataExchange dataExchange) {
        this.DE = dataExchange;
//        motorA = new UnregulatedMotor(MotorPort.C);
//	    motorB = new UnregulatedMotor(MotorPort.D);
    }
        


	@Override
	public void run() {
		
		while (!DE.isStopThreads()) {
			if (DE.getCurrent_CMD() ==  DataExchange.followLine ) {
//			if (DE.getCurrentThreshold() !=  0 ) {
				followLine();  
		}
		  else if (DE.getCurrent_CMD() == DataExchange.avoid) {
			  //if(DE.isAvoidObstacle()) {
                  driveAvoid();
                  backToLine();
//              }
//			  else if (DE.isObstacleDetected()) {
//				  end();
//	              break;
//	            }
			  
	        }
		  else if (DE.getCurrent_CMD() == DataExchange.end) {
			  end();
			
		  }
			
		  }
              
	} 
      
          
            private void driveAvoid() {
            	 Motor.C.stop(); 
                 Motor.D.stop(); 
                 
                 Motor.C.setSpeed(100); 
                 Motor.D.setSpeed(100); 

                 Motor.C.rotate(-200); 
                 Motor.D.rotate(200); 

                 
                 Motor.C.forward(); 
                 Motor.D.forward();
            }
            
            private void followLine() {
            	if (DE.getCurrentThreshold() >= EDGE_LOWER_THRESHOLD && DE.getCurrentThreshold() <= EDGE_UPPER_THRESHOLD)  { 
            		
                	Motor.C.setSpeed(80);
                	Motor.D.setSpeed(80);
                    Motor.C.forward();
                    Motor.D.forward();
                }else if (DE.getCurrentThreshold() < EDGE_LOWER_THRESHOLD) {
                	
                	Motor.C.setSpeed(50);
                	Motor.D.setSpeed(30);
                    Motor.C.forward();
                    Motor.D.forward();
                }else if (DE.getCurrentThreshold() > EDGE_UPPER_THRESHOLD ){ 
                	Motor.C.setSpeed(30);
                	Motor.D.setSpeed(50);
                    Motor.C.forward();
                    Motor.D.forward(); 
                }
            }
            private void backToLine() {
            	for(int n = 0; n <= 4; n ++) {
                    int angle = 90; 
     
                    Motor.C.rotate(-angle, true); 
                    Motor.D.rotate(angle); 

                    Motor.C.forward(); 
                    Motor.D.forward();
                    if(DE.getCurrent_CMD() == DataExchange.followLine) {
                    	return;
                    }
                    Motor.C.rotate(angle, true); 
                    Motor.D.rotate(-angle); 

                    Motor.C.forward(); 
                    Motor.D.forward();
                    if(DE.getCurrent_CMD() == DataExchange.followLine) {
                    	return;
                    }
                    
//                    else {
//                    	end();
//                    }
            	}
            }
            
            private void end() {
            	
            	 LCD.clear();
                 LCD.drawString("Program ends!", 0, 0);
                 LCD.drawString("Thank you!", 1, 1);
                // Delay.msDelay(2000);
                 Motor.C.stop(); 
                 Motor.D.stop();
                 Motor.C.close(); 
                 Motor.D.close();
                 DE.setStopThreads(true);
            }
           
            
}
    
	
    
            
            
            
            
            
     
	
