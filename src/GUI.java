//Juan Dupuy
//GUI.java
//Oct 13, 2020
//This file defines the gui as well as creates trafficLight and Car threads that are to be displayed in the simulation.
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.SimpleDateFormat;


public class GUI {
	
	boolean simulationStart = true;
	boolean simulationPause = false;
	
	GUI(){
		
		//Frame setup
		JFrame frame = new JFrame("Traffic Simulation");
		frame.setSize(700,500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLayout(null);
		
		//Timer setup 
		JLabel time = new JLabel(); 
		time.setBounds(275,300,350,25);
		frame.add(time);
		TimerThread timer = new TimerThread(time);
		timer.start();
		
		//Lights gui setup
		JLabel lightText = new JLabel("Traffic Lights: "); 
		lightText.setBounds(35,10,150,30);
		frame.add(lightText);
		
		JLabel ul1 = new JLabel("--------------------------------------------------------"); 
		ul1.setBounds(35,25,250,10);
		frame.add(ul1);
		
		JButton addLight = new JButton("+");
		JButton minusLight = new JButton("-"); 
		addLight.setBounds(150,10,45,20);
		minusLight.setBounds(200,10,45,20);
		frame.add(addLight);
		frame.add(minusLight);
		
		JLabel numOfLights = new JLabel("3"); 
		numOfLights.setBounds(120,15,40,20);
		frame.add(numOfLights);
		
		/*
		JPanel panel = new JPanel(); 
		panel.setBounds(50,40,110,40);
		JLabel firstLight = new JLabel("Light #1 : green"); 
		panel.add(firstLight);
		frame.add(panel);
		*/
		
		//adding light threads.
		
		ArrayList<trafficLight> lightList = new ArrayList<trafficLight>();
		
		ArrayList<Car> carList = new ArrayList<Car>();
		
		lightList.add(new trafficLight("light 1", 50, 40, 150, 20, frame, "1000m")); 
		lightList.get(0).t.start();
		lightList.add(new trafficLight("light 2", 50, 70, 150, 20, frame, "2000m")); 
		lightList.get(1).t.start();
		lightList.add(new trafficLight("light 3", 50, 100, 150, 20, frame, "3000m")); 
		lightList.get(2).t.start();
		//JLabel firstLight = new JLabel("Light #1 : " + light1.current);
		//firstLight.setBounds(50,35,75,30);
		//frame.add(firstLight);
		//System.out.print(light1.current);
		
		
		
		addLight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int val = Integer.valueOf(numOfLights.getText());

				if(val == 5) {

				}else if(!simulationStart | simulationPause){
					
				}else {
					int lightDepth = 100 + ((val-2)*30);
					val++;
					numOfLights.setText(String.valueOf(val));
					lightList.add(new trafficLight("light " + val, 50, lightDepth, 150, 20, frame, val + "000m" ));
					lightList.get(lightList.size()-1).t.start();
					
					for(Car a:carList) {
						a.update(lightList);
					}
					
				}
				
			}
		});

		minusLight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int val = Integer.valueOf(numOfLights.getText());

				if(val == 3) {

				}else if(!simulationStart | simulationPause){
					
				}else {
					val--;
					lightList.get(lightList.size()-1).t.interrupt();
					lightList.remove(lightList.size()-1);
					numOfLights.setText(String.valueOf(val));
					for(Car a:carList) {
						a.update(lightList);
					}
					
				}
				frame.repaint();
				
			}
		});
		
		//Cars GUI setup
		JLabel carText = new JLabel("Cars: "); 
		carText.setBounds(375,10,150,30);
		frame.add(carText);
		
		JLabel ul2 = new JLabel("---------------------------------------------"); 
		ul2.setBounds(375,25,250,10);
		frame.add(ul2);
		
		JButton addCar = new JButton("+");
		JButton minusCar = new JButton("-"); 
		addCar.setBounds(450,10,45,20);
		minusCar.setBounds(500,10,45,20);
		frame.add(addCar);
		frame.add(minusCar);
		
		JLabel numOfCars = new JLabel("0"); 
		numOfCars.setBounds(420,15,40,20);
		frame.add(numOfCars);
		
		
		//ArrayList of car objects
		
		
		addCar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int val = Integer.valueOf(numOfCars.getText());

				if(val == 5) { //change this to top constraint.

				}else {
					val++;
					int carDepth = 40+(30*(val-1));
					numOfCars.setText(String.valueOf(val));
					carList.add(new Car("car " + val, 325, carDepth, 300, 20, frame, lightList));
					carList.get(carList.size()-1).t.start();
				}
			}
		});

		minusCar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int val = Integer.valueOf(numOfCars.getText());

				if(val == 0) { //change this to bottom constraint

				}else {
					val--;
					numOfCars.setText(String.valueOf(val));
					
					carList.get(carList.size()-1).t.interrupt(); 
					carList.remove(carList.size()-1);
					frame.repaint();
				}
			}
		});
		
		
		
		
		//Control buttons at bottom
		
		JButton start = new JButton("Start");
		JButton pause = new JButton("Pause");
		JButton stop = new JButton("Stop");
		JButton cont = new JButton("Continue");
		JLabel status = new JLabel("The simulation has started.");
		
		start.setBounds(75,250,100,20);
		frame.add(start);
		stop.setBounds(195,250,100,20);
		frame.add(stop);
		
		
		pause.setBounds(350,250,100,20);
		frame.add(pause);
		cont.setBounds(470,250,100,20);
		frame.add(cont);
		
		status.setBounds(250,275,200,30);
		frame.add(status);
		
		
		
		//action listeners for control buttons at bottom
		
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(simulationStart) {
					
				}else {
					simulationStart = true;
					lightList.add(new trafficLight("light 1", 50, 40, 150, 20, frame, "1000m")); 
					lightList.get(0).t.start();
					lightList.add(new trafficLight("light 2", 50, 70, 150, 20, frame, "2000m")); 
					lightList.get(1).t.start();
					lightList.add(new trafficLight("light 3", 50, 100, 150, 20, frame, "3000m")); 
					lightList.get(2).t.start();
					
					numOfLights.setText("3");
					
					status.setText("The simulation has started");
					
					stop.setEnabled(true);
					pause.setEnabled(true);
					cont.setEnabled(true);
					
					
					
					frame.repaint();
				}
			}
		});
		
		stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!simulationStart) {
					
				}else {
					simulationStart = false;
					while(lightList.size()!=0) {
						lightList.get(lightList.size()-1).t.interrupt();
						lightList.remove(lightList.size()-1);
					}
					
					while(carList.size()!=0) {
						carList.get(carList.size()-1).t.interrupt(); 
						carList.remove(carList.size()-1);
					}
					
					numOfLights.setText("0");
					numOfCars.setText("0");
					status.setText("The simulation has stopped.");
					
					pause.setEnabled(false); 
					cont.setEnabled(false);
					
					frame.repaint();
					
					
					
				}
			}
		});
		
		pause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!simulationPause) {
					simulationPause = true;
					
					for(trafficLight a:lightList) {
					
						a.pause = true;
						
					}
					
					for(Car a: carList) {
						a.pause = true;
					}
					
					status.setText("The simulation is paused."); 
					
					start.setEnabled(false);
					stop.setEnabled(false);
					
					frame.repaint();
					
				}
				else {
					
				}
			}
		});
		
		cont.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(simulationPause) {
					simulationPause = false;
					
					for(trafficLight a:lightList) {	
						a.pause = false;
					}
					
					for(Car a: carList) {
						a.pause = false;
					}
					
					status.setText("The simulation has continued.");
					
					start.setEnabled(true);
					stop.setEnabled(true);
					
					frame.repaint();
					
					
						
				}else {
					
				}
			}
		});
		
		
		frame.repaint();
		
	}
}

class TimerThread extends Thread {

    protected boolean isRunning;

    protected JLabel dateLabel;
    protected JLabel timeLabel;

    protected SimpleDateFormat timeFormat =
            new SimpleDateFormat("h:mm:ss a");

    public TimerThread(JLabel timeLabel) {
        
        this.timeLabel = timeLabel;
        this.isRunning = true;
    }

    @Override
    public void run() {
        while (isRunning) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    Calendar currentCalendar = Calendar.getInstance();
                    Date currentTime = currentCalendar.getTime();
                    timeLabel.setText(timeFormat.format(currentTime));
                }
            });

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }

    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

}


