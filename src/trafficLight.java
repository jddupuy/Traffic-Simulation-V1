//Juan Dupuy
//trafficLight.java
//Oct 13, 2020
//This file contains the definition of traffic lights, and keeps track of how long it takes for each light to change color.

import java.util.Random;
import javax.swing.*;
import java.awt.Color;

public class trafficLight extends JPanel implements Runnable{
	String current, name;
	String [] color = {"red","yellow","green"};
	Thread t; 
	Random rand = new Random();
	JPanel panel = new JPanel();
	JLabel label = new JLabel(name);
	JFrame frame;
	String loc;
	boolean pause = false;
	
	trafficLight(String threadName, int x, int y, int w, int d, JFrame frame, String loc){
		name = threadName;
		t = new Thread(this,name);
		panel.setBounds(x,y,w,d);
		this.frame = frame;
		this.loc = loc;
	}
	
	public void run() {
		current = color[rand.nextInt(3)];
		panel.add(label); 
		frame.add(panel);
		boolean go = true;
		while(go) {
			try {
				if(current.equals("green")) {
					current = "yellow"; 
					label.setText("(" + loc + ") " + name + " : " + current);
					System.out.println(name + current);
					Thread.sleep(3000);
				}else if(current.equals("yellow")) {
					current = "red"; 
					label.setText("(" + loc + ") " + name + " : " + current);
					System.out.println(name + current);
					Thread.sleep(5000);
				}else {
					current = "green"; 
					label.setText("(" + loc + ") " + name + " : " + current);
					System.out.println(name + current);
					Thread.sleep(7000);
				}
				
				while(pause) {
					
				}
				
			}catch(InterruptedException e) {
				System.out.println(name + " interrupted.");
				go = false;
				label.setText("");
				frame.remove(panel);
				
			}
		}
		
	}
	
	
}
