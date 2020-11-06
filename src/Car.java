//Juan Dupuy 
//Oct 13, 2020
//Car.java
//This file creates new cars, which keep track of speed, distance, and what color is the light that it is crossing. 

import javax.swing.*;
import java.math.*; 
import java.util.*;

public class Car extends JPanel implements Runnable{
	
	double speed = 60.0; //60 kmh 
	boolean go = true;
	boolean pause = false;
	double xPosition = 0.0;
	int end = 5000; //Road ends at 5000;
	
	Boolean light1 = false, light2 = false, light3 = false, light4 = false, light5 = false;
	
	JFrame frame;
	JPanel panel = new JPanel();
	JLabel label = new JLabel();
	String name;
	Thread t;
	
	ArrayList<trafficLight> lights; 
	
	
	Car(String threadName, int x, int y, int w, int d, JFrame frame,ArrayList<trafficLight> lights){
		name = threadName;
		t = new Thread(this,name);
		panel.setBounds(x,y,w,d);
		this.frame = frame;
		this.lights = lights;
		
		if(lights.size() == 3) {
			light4 = null; 
			light5 = null;
		}
		
		if(lights.size() == 4) {
			light5 = null;
		}
		
	}
	
	public void update(ArrayList<trafficLight> lights){
		this.lights = lights;
		
		if(lights.size() == 3) {
			light4 = null; 
			light5 = null;
		}
		
		if(lights.size() == 4) {
			light5 = null;
		}
		
	}
	
	
	
	public void run() {
		panel.add(label);
		frame.add(panel);
		double scale = Math.pow(10,1);
		while(go) {
			label.setText(name+" : " + xPosition + ", 0.0;      Speed: " + speed + "kmh");
			try {
				xPosition = (xPosition + (speed/3.6));
				xPosition = Math.round(xPosition * scale)/scale;
				Thread.sleep(1000);
				
				if(xPosition>1000 && light1 == false) {
					while(lights.get(0).current.equals("red")) {
						xPosition = 1000.0;
						label.setText(name+" : " + xPosition + ", 0.0;      Speed: 0 kmh");
					}
					light1 = true;
				}
				
				if(xPosition >2000 && !light2) {
					while(lights.get(1).current.equals("red")) {
						xPosition = 2000.0;
						label.setText(name+" : " + xPosition + ", 0.0;      Speed: 0 kmh" );
					}
					light2 = true;
				}
				
				if(xPosition >3000 && !light3) {
					while(lights.get(2).current.equals("red")) {
						xPosition = 3000.0;
						label.setText(name+" : " + xPosition + ", 0.0;      Speed: 0 kmh");
					}
					light3 = true;
				}
				
				if(light4 != null) {
					if(xPosition >4000 && !light4) {
						
						while(lights.get(3).current.equals("red")) {
							xPosition = 4000.0;
							label.setText(name+" : " + xPosition + ", 0.0;      Speed: 0 kmh");
						}
						light4 = true;
					}
				}
				
				if(light5 != null) {
					if(xPosition >5000 && !light5) {
						
						while(lights.get(4).current.equals("red")) {
							xPosition = 5000.0;
							label.setText(name+" : " + xPosition + ", 0.0;      Speed: 0 kmh");
						}
						
						light5 = true;
					}
				}
								
				if(xPosition > 5000) {
					xPosition = xPosition - 5000;
					light1 = false; 
					light2 = false; 
					light3 = false; 
					if(light4 != null) {
						light4 = false;
					}
					if(light5 != null) {
						light5 = false;
					}
					
				}
				
				while(pause) {
					
				}
				
			}catch(InterruptedException e) {
				label.setText("");
				frame.remove(panel);
			}
			
			panel.repaint();
		}

	}
}
