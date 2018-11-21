import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.border.LineBorder;


public class SimpleCalendar {

	private static GregorianCalendar gC;
	private static final String[] monthName= {"January","February","March","April","May", "June", "July","August", "September",
				"October","November","December"};
	private static ActionListeners[] aLs;
	private static String[] daysInWeek= {"Sunday", "Monday", "Tuesday", "Wednesday","Thurday","Friday","Saturday"};
	private static int change_Date;
	private static int change_Month;
	private static int year;
	private static int monthI;
	private static int today_Day;
	private static int daysInMonth;
	private static ArrayList<Event> events;
	private static ArrayList<EventDate> eventDates;
	private static MyEvents myEvents;
	private static final String[] days= {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
	
	public static void main(String[] args) throws NumberFormatException, IOException, FileNotFoundException {
		
		myEvents=new MyEvents();
		 gC=new GregorianCalendar();
		 change_Date=0;
	     change_Month=0;
	     events=myEvents.getEvent();
	     eventDates=myEvents.getEventDate();
	     monthI=0;
		
		
		//Load events
		File file=new File("/Users/angelayuan/eclipse-workspace/GUI_Calendar/src/event.txt.rtf");
		BufferedReader bR=new BufferedReader(new FileReader(file));
		   Scanner sc=new Scanner(file);
		   if(bR.readLine()==null) {//Check if the file is empty
			   System.out.println("This is your first run, no events found.");
			  
		   }
		   else {
			   System.out.println("Events found!");
			   while(sc.hasNextLine()) {
			   String str=sc.nextLine();
			   String title=str.substring(36);
			   int date=Integer.parseInt(str.substring(6,10))*10000+Integer.parseInt(str.substring(0,2))*100+Integer.parseInt(str.substring(3,5));
			   int startT=Integer.parseInt(str.substring(23,25))*100+Integer.parseInt(str.substring(26,28));
			   int endT=Integer.parseInt(str.substring(29,31))*100+Integer.parseInt(str.substring(32,34));
			   events.add(new Event(title,date,startT,endT));
			   eventDates.add(new EventDate(Integer.parseInt(str.substring(0,2)), Integer.parseInt(str.substring(3,5)), Integer.parseInt(str.substring(6,10))));
				 /*for(int a=0;a<myEventDates.size();a++) {
			    	  System.out.println(myEventDates.get(a).getYear());
			    }*/
			   
		   }
		   }
 
		
		
		
		
		
	
       
		/////////////////Below is the GUI part\\\\\\\\\\\\\\\\
		
		JFrame frame=new JFrame();
		//Use BorderLayout
		frame.setLayout(new BorderLayout());
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setSize(800, 600);
	   
	    JPanel buttonP=new JPanel();// This panel contains 3 buttons ( "<" ">" & "Quit")
	    //Maybe buttonP needs to setBoundary() or setSize()
	    buttonP.setLayout(new FlowLayout()); // contains eventInfo JPanel and buttons JPanel
	    JPanel eventInfo=new JPanel();
	    eventInfo.setVisible(false);
	    JPanel buttons=new JPanel();
	    buttonP.add(eventInfo);
	    buttonP.add(buttons);
	    JButton previous= new JButton("<");
	    JButton next=new JButton(">");
	    JButton quit=new JButton("QUIT");
	    
	    quit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				myEvents.sortEventsByDate();
				
				 
				
	    	
			
						try {
							System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream("/Users/angelayuan/eclipse-workspace/GUI_Calendar/src/event.txt.rtf"))));
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				
		    	   /* for(int a=0;a<myEvents.size();a++) {
		    	    	System.out.println(myEvents.get(a).getStartT());
		    	    }*/
	    	       gC=new GregorianCalendar();
		    	   for(int i=0;i<events.size();i++) {
		    	    	   String date=Integer.toString(events.get(i).getDate());
		    	    	   String monthS=date.substring(4,6);
		    	    	   int monthI=Integer.parseInt(monthS);
		    	    	   String dateS=date.substring(6);
		    	    	   int dateI=Integer.parseInt(dateS);
		    	    	   String yearS=date.substring(0,4);
		    	    	   int yearI=Integer.parseInt(yearS);
		    	    	   int today_month=gC.get(Calendar.MONTH)+1;
		  	    	   int today_date=gC.get(Calendar.DAY_OF_MONTH);
		  	    	   int today_year=gC.get(Calendar.YEAR);
		  	    	   gC.add(Calendar.DATE, dateI-today_date);
		  	    	   gC.add(Calendar.YEAR, yearI-today_year);
		  	    	   gC.add(Calendar.MONTH, monthI-today_month);
		  	    	   String time=gC.getTime().toString();
		  	    	   String target_day=null;
		  	    	   for(int j=0;j<days.length;j++){
		  	    		        if(days[j].substring(0,3).equals(time.substring(0,3))){
		  	    		           target_day=days[j];
		  	    		      } 
		    	    	   
		  	    	   }
		  	    	   String start_T=null;
		  	    	   String end_T=null;
		  	    	   if(events.get(i).getStartT()>=1000) {
		  	    	   start_T=Integer.toString(events.get(i).getStartT());}
		  	    	   else {start_T="0"+Integer.toString(events.get(i).getStartT());}
		  	    	   if(events.get(i).getEndT()>=1000) {
		  	    	   end_T=Integer.toString(events.get(i).getEndT());}
		  	    	   else {end_T="0"+Integer.toString(events.get(i).getEndT());}
		  	    	   if(target_day.length()==6) {
		  	    	   System.out.println(monthS+"/"+dateS+"/"+yearS+"  "+target_day+"     "+start_T.substring(0,2)+":"+start_T.substring(2)+"-"
		  	    			  + end_T.substring(0,2)+":"+end_T.substring(2)+"  "+events.get(i).getTitle() );}
		  	    	   else if(target_day.length()==7) {
		  	    		 System.out.println(monthS+"/"+dateS+"/"+yearS+"  "+target_day+"    "+start_T.substring(0,2)+":"+start_T.substring(2)+"-"
			  	    			  + end_T.substring(0,2)+":"+end_T.substring(2)+"  "+events.get(i).getTitle() );
		  	    	   }
		  	    	   else if(target_day.length()==8) {
		  	    		 System.out.println(monthS+"/"+dateS+"/"+yearS+"  "+target_day+"   "+start_T.substring(0,2)+":"+start_T.substring(2)+"-"
			  	    			  + end_T.substring(0,2)+":"+end_T.substring(2)+"  "+events.get(i).getTitle() );
		  	    	   }
		  	    	   else {
		  	    		 System.out.println(monthS+"/"+dateS+"/"+yearS+"  "+target_day+"  "+start_T.substring(0,2)+":"+start_T.substring(2)+"-"
			  	    			  + end_T.substring(0,2)+":"+end_T.substring(2)+"  "+events.get(i).getTitle() );
		  	    	   }
				
				
				
				
				
				
		    	   }
		    	   System.exit(0); 
			}
	    	
	    });
	    
	    
	    
	    buttons.add(previous);
	    buttons.add(next);
	    buttons.add(quit);
	    eventInfo.setLayout(new BorderLayout());
	    JTextField eventT=new JTextField();
	    eventT.setText("Untitled Event(Use 24hour format 00:00,plz)");
	    JPanel eventTime=new JPanel();
	    eventInfo.add(eventT, BorderLayout.NORTH);
	    eventInfo.add(eventTime, BorderLayout.CENTER);
	    JTextField date=new JTextField(7);
	    date.setText("04/28/2018");
	    date.setEditable(false);
	    JTextField startT=new JTextField("TimeStart",3);
	    JTextField endT=new JTextField("TimeEnd",3);
	    JButton save=new JButton("SAVE");
	    
	    
	    
	    eventTime.add(date);
	    eventTime.add(startT);
	    eventTime.add(new JLabel("TO"));
	    eventTime.add(endT);
	    eventTime.add(save);
	    frame.add(buttonP, BorderLayout.NORTH);
	    
	    
	    JPanel viewP=new JPanel();//This panel contains monthV and dayView
	    //Maybe viewP needs to setBoundary() or setSize()
	    viewP.setLayout(new BorderLayout());
	    JPanel monthV=new JPanel();
	    JPanel dayV=new JPanel();
	    viewP.add(monthV, BorderLayout.WEST);// month View on the left of viewP
	    viewP.add(dayV,BorderLayout.CENTER);// day view on the right of viewP
	    monthV.setLayout(new BorderLayout());// MonthV contains one JButton and one CalendarIcon inside a JLabel
	
	    dayV.setLayout(new BorderLayout());//dayV contains one JLabel with 'day' info and one JLabel and one TextArea
	 
	    JButton createB=new JButton("CREATE");
	    createB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				eventInfo.setVisible(true);
				eventT.setText("Untitled Event(Use 24hour format 00:00,plz)");
				startT.setText("TimeStart");
				endT.setText("TimeEnd");
			
				for(ActionListeners aL:aLs) {
					if(aL.IfjustClicked()) {
						gC=aL.getGC();
						
					}
					
				}
				String month=Integer.toString(gC.get(Calendar.MONTH)+1);
				String date1=Integer.toString(gC.get(Calendar.DATE));
				String year=Integer.toString(gC.get(Calendar.YEAR));
				if(month.length()==1) {
					month="0"+month;
				}
				if(date1.length()==1) {
					date1="0"+date1;
				}
				date.setText(month+"/"+date1+"/"+year);
			}
	    	
	    });
	    
	    monthV.add(createB, BorderLayout.NORTH);
	    JPanel calendar=new JPanel();
	    monthV.add(calendar, BorderLayout.CENTER);
	    BoxLayout boxL=new BoxLayout(calendar, BoxLayout.Y_AXIS);
	    calendar.setLayout(boxL);
	    JLabel monthY=new JLabel("MonthY");
	    //JLabel weeks=new JLabel("weeks");
	    JPanel dates=new JPanel();
	    dates.setLayout(new GridLayout(7,7));
	    dates.add(new JLabel("   S   "));
	    dates.add(new JLabel("   M   "));
	    dates.add(new JLabel("   T   "));
	    dates.add(new JLabel("   W   "));
	    dates.add(new JLabel("   T   "));
	    dates.add(new JLabel("   F   "));
	    dates.add(new JLabel("   S"));
	 
	   
	    
	    
	    
	    calendar.add(monthY);
	    //calendar.add(weeks);
	    calendar.add(dates);
	    JPanel jPanel=new JPanel();
	    jPanel.setPreferredSize(new Dimension(100,170));
	    calendar.add(jPanel);
	    JLabel dayInfo=new JLabel("dayInfo",10);//Monday 04/22

	   
	    
	    JPanel eventList=new JPanel();
	    
	    JScrollPane scrollP=new JScrollPane(eventList);
	    scrollP.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	    scrollP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	   

	    
	    BoxLayout bL=new BoxLayout(eventList, BoxLayout.Y_AXIS);
	    eventList.setLayout(bL);
	    MyPanel[] mypanels=new MyPanel[24];
	    for(int i=0;i<=11;i++) {
	    	mypanels[i]=new MyPanel(i+"am");
	    	eventList.add(mypanels[i]);
	    }
	    mypanels[12]=new MyPanel(12+"pm");
	    eventList.add(mypanels[12]);
	    for(int i=1;i<=11;i++) {
	    	mypanels[i+12]=new MyPanel(i+"pm");
	    	eventList.add(mypanels[i+12]);
	    }

	    dayV.add(dayInfo,BorderLayout.NORTH);
	    dayV.add(scrollP,BorderLayout.CENTER);
	    frame.add(viewP,BorderLayout.CENTER);
	    
	    aLs=new ActionListeners[42];
	       
	    
        JButton[] dateButtons=new JButton[42];
     		for(int i=0;i<dateButtons.length;i++) {
     			dateButtons[i]=new JButton();
     			aLs[i]=new ActionListeners(i,dateButtons,mypanels, dayInfo, gC,date,myEvents);
     			dateButtons[i].addActionListener(aLs[i]);
     			
                dateButtons[i].setBorder(BorderFactory.createEmptyBorder(i%7, i%6, 1, 1));
     			
     	        
     		}
     		
     		
         for(ActionListeners aL:aLs) {
        	 aL.setALs(aLs);
         }
    
     	
	   
	    
	    for(int i=0;i<dateButtons.length;i++) {
	    	dateButtons[i].setPreferredSize(new Dimension(1,1));
	    	dates.add(dateButtons[i]);
	    }
	    
	    frame.setVisible(true);
	    
	    
	    
	    ////////////////////////Below is the GregorianCalendar data\\\\\\\\\\\\\\\\
	    
	    
	   
	    
	      gC=new GregorianCalendar();
		  monthI=gC.get(Calendar.MONTH);//-----------------------> 
          String month=monthName[gC.get(Calendar.MONTH)];
      
          today_Day=gC.get(Calendar.DATE);//-------------------------------->
		  //System.out.println(month);
          year=gC.get(Calendar.YEAR);//------------------------------->
		  monthY.setText(month+"    "+ year);
		  monthY.setFont(new Font((month+"    "+year), Font.BOLD,20));
		  gC.set(Calendar.DAY_OF_MONTH,gC.getActualMinimum(Calendar.DAY_OF_MONTH));
		  //System.out.println(gC.getTime().toString());
		  String[] daysOfWeek= {"Su", "Mo", "Tu", "We","Th","Fr","Sa"};
		  String first_day_of_Month=gC.getTime().toString().substring(0, 2);
			int start_Index=0;
			for(int i=0;i<daysOfWeek.length;i++) {
				if(first_day_of_Month.equals(daysOfWeek[i])) {
					start_Index=i;
				}
			}
			
			daysInMonth=0;//----------------------------------------->
			if(month=="February") {
				if(year%4==0) {
					daysInMonth=29;
				}
				else{daysInMonth=28;}
			}
			else if(month=="January" || month=="March" || month=="May" || month=="July" || month=="August" ||
					month=="October" || month=="December") {
				daysInMonth=31;
			}
			else {
				daysInMonth=30;
			}
			//Create a 2D integer[][] array storing all dates
			int[][] dates1=new int[6][7];
			int count=1;
			
			//Fill the first row of calendar
			for(int j=start_Index;j<=6;j++) {
				dates1[0][j]=count;
				count++;
			}
			//fill the rest of rows in the calendar
			for(int i=1;i<=5;i++) {
				for(int j=0;j<=6;j++) {
					if(count<=daysInMonth) {
					dates1[i][j]=count;
					}
					count++;
				}
			}
		  
			int counter=0;
			for(int i=0;i<=5;i++) {
				   for(int j=0;j<=6;j++) {
					   if(dates1[i][j]==0) {
						   dateButtons[counter].setText("");
					   }
					   else {
						   if(dates1[i][j]==today_Day) {
							   dateButtons[counter].setBorder(new LineBorder(Color.BLACK));
							   dayInfo.setText("                       "+daysInWeek[counter%7]+"   "+(gC.get(Calendar.MONTH)+1)+"/"+today_Day);
							   dayInfo.setFont(new Font(("                       "+daysInWeek[counter%7]+"   "+(gC.get(Calendar.MONTH)+1)+"/"+today_Day), Font.BOLD,20));
							  
						   }
						   dateButtons[counter].setText(Integer.toString(dates1[i][j]));
						   
					   }
					   counter++;
					   
				   }
				   }
			
			String month2=Integer.toString(gC.get(Calendar.MONTH)+1);
			String date1=Integer.toString(gC.get(Calendar.DATE));
			String year2=Integer.toString(gC.get(Calendar.YEAR));
			if(month2.length()==1) {
				month2="0"+month2;
			}
			if(date1.length()==1) {
				date1="0"+date1;
			}
			date.setText(month2+"/"+date1+"/"+year2);
			
			
			int monthII=gC.get(Calendar.MONTH)+1;
			int date1I=gC.get(Calendar.DATE);
			int yearI=gC.get(Calendar.YEAR);
			for(MyPanel p:mypanels) {
				p.clearText();
			}
			if(DateWithEvent(date1I,month,yearI));{
				for(Event eve: events) {
					 String str=Integer.toString(eve.getDate());
					 if((Integer.parseInt(str.substring(0,4))==yearI && Integer.parseInt(str.substring(4,6))==monthII &&
							 Integer.parseInt(str.substring(6))==date1I)) {
						 int startTm=eve.getStartT()/100;
						 if(startTm<=12) {
							 mypanels[startTm].setEvents(eve.getStartT()+"--"+eve.getEndT()+"   "+eve.getTitle());
						 }
						 if(startTm==24) {
							 mypanels[0].setEvents(eve.getStartT()+"--"+eve.getEndT()+"   "+eve.getTitle());
						 }
						 if(startTm>12) {
							 mypanels[12+startTm%12].setEvents(eve.getStartT()+"--"+eve.getEndT()+"   "+eve.getTitle());
						 }
					 }
				}
			}
			
		
			
			
			
			
			
		  
			gC=new GregorianCalendar();
	       
			previous.addActionListener(new ActionListener() {

			     
				 
				public void actionPerformed(ActionEvent e) {
					boolean firstTime=true;
				     
					for(ActionListeners aL: aLs) {
						if(aL.IfjustClicked()) {
							gC=aL.getGC();
							aL.ResetJustC();
						}
					}
					
				    change_Date=-1;
					gC.add(Calendar.DATE, change_Date);
					GregorianCalendar GC2=gC;
					int dateofGC=gC.get(Calendar.DATE);
					//System.out.println(dateofGC);
					for(ActionListeners aL:aLs) {
						aL.setGC(gC);
					}
					int guard=gC.get(Calendar.MONTH);
					if(guard<monthI) {
						 firstTime=false;
						 System.out.println(gC.get(Calendar.MONTH));
						 System.out.println(monthI);
						 monthI=gC.get(Calendar.MONTH);
						//Repaint the calendar, highlight the last day of previous month
				          String month=monthName[gC.get(Calendar.MONTH)];
				      
				          today_Day=gC.get(Calendar.DATE);//-------------------------------->
						  //System.out.println(today_Day);
				          year=gC.get(Calendar.YEAR);//------------------------------->
						  monthY.setText(month+"    "+ year);
						  monthY.setFont(new Font((month+"    "+year), Font.BOLD,20));
						  GC2.set(Calendar.DAY_OF_MONTH,GC2.getActualMinimum(Calendar.DAY_OF_MONTH));
						  //System.out.println(gC.getTime().toString());
						  String[] daysOfWeek= {"Su", "Mo", "Tu", "We","Th","Fr","Sa"};
						  String first_day_of_Month=GC2.getTime().toString().substring(0, 2);
							int start_Index=0;
							for(int i=0;i<daysOfWeek.length;i++) {
								if(first_day_of_Month.equals(daysOfWeek[i])) {
									start_Index=i;
								}
							}
							
							daysInMonth=0;//----------------------------------------->
							if(month=="February") {
								if(year%4==0) {
									daysInMonth=29;
								}
								else{daysInMonth=28;}
							}
							else if(month=="January" || month=="March" || month=="May" || month=="July" || month=="August"||
									month=="October" || month=="December") {
								daysInMonth=31;
							}
							else {
								daysInMonth=30;
							}
							//Create a 2D integer[][] array storing all dates
							int[][] dates1=new int[6][7];
							int count=1;
							
							//Fill the first row of calendar
							for(int j=start_Index;j<=6;j++) {
								dates1[0][j]=count;
								count++;
							}
							//fill the rest of rows in the calendar
							for(int i=1;i<=5;i++) {
								for(int j=0;j<=6;j++) {
									if(count<=daysInMonth) {
									dates1[i][j]=count;
									}
									count++;
								}
							}
							
							for(int i=0;i<dateButtons.length;i++) {
					 			
					            dateButtons[i].setBorder(BorderFactory.createEmptyBorder(i%7, i%6, 1, 1));
					 			
					 	
					 		}
						  
							int counter=0;
							for(int i=0;i<=5;i++) {
								   for(int j=0;j<=6;j++) {
									   if(dates1[i][j]==0) {
										   dateButtons[counter].setText("");
									   }
									   else {
										   if(dates1[i][j]==today_Day) {
											   dateButtons[counter].setBorder(new LineBorder(Color.BLACK));
											   dayInfo.setText("                       "+daysInWeek[counter%7]+"   "+(gC.get(Calendar.MONTH)+1)+"/"+today_Day);
											   dayInfo.setFont(new Font(("                       "+daysInWeek[counter%7]+"   "+(gC.get(Calendar.MONTH)+1)+"/"+today_Day), Font.BOLD,20));
											  
										   }
										   dateButtons[counter].setText(Integer.toString(dates1[i][j]));
										   
									   }
									   counter++;
									   
								   }
								   }
						
							  // gC=GC2;
						
						
						
						
						
						
						
						
						
						
						
						
					}
					else {
						  for(int i=0;i<dateButtons.length;i++) {
					 		   
					            dateButtons[i].setBorder(BorderFactory.createEmptyBorder(i%7, i%6, 1, 1));
					 			if(dateButtons[i].getText().equals(Integer.toString(dateofGC))) {
					 				
					 				dateButtons[i].setBorder(new LineBorder(Color.BLACK));
					 			}
					 	
					 		}
						String Day_of_Week=daysInWeek[gC.get(Calendar.DAY_OF_WEEK)-1];
						int month=gC.get(Calendar.MONTH)+1;
						int date=gC.get(Calendar.DATE);
						  dayInfo.setText("                           "+Day_of_Week+"   "+month+"/"+date);
					     dayInfo.setFont(new Font(("                           "+Day_of_Week+"   "+month+"/"+date),Font.BOLD, 20));
						  
						
					  
					}///else ends here
					
					
					//System.out.println(gC.get(Calendar.MONTH)+1);
					//System.out.println(gC.get(Calendar.DATE));
					String month=Integer.toString(gC.get(Calendar.MONTH)+1);
					String date1=Integer.toString(gC.get(Calendar.DATE));
					String year=Integer.toString(gC.get(Calendar.YEAR));
					
					if(gC.get(Calendar.MONTH)+1<10) {
						month="0"+month;
					}
					if(gC.get(Calendar.DATE)<10) {
						date1="0"+date1;
					}
					date.setText(month+"/"+date1+"/"+year);
					
					int monthI=gC.get(Calendar.MONTH)+1;
					int date1I=gC.get(Calendar.DATE);
					int yearI=gC.get(Calendar.YEAR);
					for(MyPanel p:mypanels) {
						p.clearText();
					}
					if(DateWithEvent(date1I,month,yearI));{
						for(Event eve: events) {
							 String str=Integer.toString(eve.getDate());
							 if((Integer.parseInt(str.substring(0,4))==yearI && Integer.parseInt(str.substring(4,6))==monthI &&
									 Integer.parseInt(str.substring(6))==date1I)) {
								 int startT=eve.getStartT()/100;
								 if(startT<=12) {
									 mypanels[startT].setEvents(eve.getStartT()+"--"+eve.getEndT()+"   "+eve.getTitle());
								 }
								 if(startT==24) {
									 mypanels[0].setEvents(eve.getStartT()+"--"+eve.getEndT()+"   "+eve.getTitle());
								 }
								 if(startT>12) {
									 mypanels[12+startT%12].setEvents(eve.getStartT()+"--"+eve.getEndT()+"   "+eve.getTitle());
								 }
							 }
						}
					}
					
				}
				
				
				
			});
			
			next.addActionListener(new ActionListener() {

				
				  private int newM=monthI;
				public void actionPerformed(ActionEvent e) {
					for(ActionListeners aL: aLs) {
						if(aL.IfjustClicked()) {
							gC=aL.getGC();
							aL.ResetJustC();
						}
					}
					
				    
					gC.add(Calendar.DATE, 1);
					GregorianCalendar GC2=gC;
					int dateofGC=gC.get(Calendar.DATE);
					//System.out.println(dateofGC);
					for(ActionListeners aL:aLs) {
						aL.setGC(gC);
					}
					int guard=gC.get(Calendar.MONTH);
					
					if(guard>monthI) {
						 //firstTime=false;
						 System.out.println(gC.get(Calendar.MONTH));
						 System.out.println(monthI);
						//Repaint the calendar, highlight the last day of previous month
						  monthI=gC.get(Calendar.MONTH);//-----------------------> 
				          String month=monthName[gC.get(Calendar.MONTH)];
				      
				          today_Day=gC.get(Calendar.DATE);//-------------------------------->
						  //System.out.println(today_Day);
				          year=gC.get(Calendar.YEAR);//------------------------------->
						  monthY.setText(month+"    "+ year);
						  monthY.setFont(new Font((month+"    "+year), Font.BOLD,20));
						  GC2.set(Calendar.DAY_OF_MONTH,GC2.getActualMinimum(Calendar.DAY_OF_MONTH));
						  //System.out.println(gC.getTime().toString());
						  String[] daysOfWeek= {"Su", "Mo", "Tu", "We","Th","Fr","Sa"};
						  String first_day_of_Month=GC2.getTime().toString().substring(0, 2);
							int start_Index=0;
							for(int i=0;i<daysOfWeek.length;i++) {
								if(first_day_of_Month.equals(daysOfWeek[i])) {
									start_Index=i;
								}
							}
							
							daysInMonth=0;//----------------------------------------->
							if(month=="February") {
								if(year%4==0) {
									daysInMonth=29;
								}
								else{daysInMonth=28;}
							}
							else if(month=="January" || month=="March" || month=="May" || month=="July" || month=="August"||
									month=="October" || month=="December") {
								daysInMonth=31;
							}
							else {
								daysInMonth=30;
							}
							//Create a 2D integer[][] array storing all dates
							int[][] dates1=new int[6][7];
							int count=1;
							
							//Fill the first row of calendar
							for(int j=start_Index;j<=6;j++) {
								dates1[0][j]=count;
								count++;
							}
							//fill the rest of rows in the calendar
							for(int i=1;i<=5;i++) {
								for(int j=0;j<=6;j++) {
									if(count<=daysInMonth) {
									dates1[i][j]=count;
									}
									count++;
								}
							}
							
							for(int i=0;i<dateButtons.length;i++) {
					 			
					            dateButtons[i].setBorder(BorderFactory.createEmptyBorder(i%7, i%6, 1, 1));
					 			
					 	
					 		}
						  
							int counter=0;
							for(int i=0;i<=5;i++) {
								   for(int j=0;j<=6;j++) {
									   if(dates1[i][j]==0) {
										   dateButtons[counter].setText("");
									   }
									   else {
										   if(dates1[i][j]==today_Day) {
											   dateButtons[counter].setBorder(new LineBorder(Color.BLACK));
											   dayInfo.setText("                       "+daysInWeek[counter%7]+"   "+(gC.get(Calendar.MONTH)+1)+"/"+today_Day);
											   dayInfo.setFont(new Font(("                       "+daysInWeek[counter%7]+"   "+(gC.get(Calendar.MONTH)+1)+"/"+today_Day), Font.BOLD,20));
											  
										   }
										   dateButtons[counter].setText(Integer.toString(dates1[i][j]));
										   
									   }
									   counter++;
									   
								   }
								   }
						
							  // gC=GC2;
						
						
						
						
						
						
						
						
						
						
						
						
					}
					else {
						  for(int i=0;i<dateButtons.length;i++) {
					 		   
					            dateButtons[i].setBorder(BorderFactory.createEmptyBorder(i%7, i%6, 1, 1));
					 			if(dateButtons[i].getText().equals(Integer.toString(dateofGC))) {
					 				
					 				dateButtons[i].setBorder(new LineBorder(Color.BLACK));
					 			}
					 	
					 		}
						String Day_of_Week=daysInWeek[gC.get(Calendar.DAY_OF_WEEK)-1];
						int month=gC.get(Calendar.MONTH)+1;
						int date=gC.get(Calendar.DATE);
						  dayInfo.setText("                           "+Day_of_Week+"   "+month+"/"+date);
					     dayInfo.setFont(new Font(("                           "+Day_of_Week+"   "+month+"/"+date),Font.BOLD, 20));
						  
						
					  
					}///else ends here
					String month=Integer.toString(gC.get(Calendar.MONTH)+1);
					String date1=Integer.toString(gC.get(Calendar.DATE));
					String year=Integer.toString(gC.get(Calendar.YEAR));
					if(month.length()==1) {
						month="0"+month;
						
					}
					if(date1.length()==1) {
						date1="0"+date1;
					}
					date.setText(month+"/"+date1+"/"+year);
					
					int monthI=gC.get(Calendar.MONTH)+1;
					int date1I=gC.get(Calendar.DATE);
					int yearI=gC.get(Calendar.YEAR);
					for(MyPanel p:mypanels) {
						p.clearText();
					}
					if(DateWithEvent(date1I,month,yearI));{
						for(Event eve: events) {
							 String str=Integer.toString(eve.getDate());
							 if((Integer.parseInt(str.substring(0,4))==yearI && Integer.parseInt(str.substring(4,6))==monthI &&
									 Integer.parseInt(str.substring(6))==date1I)) {
								 int startT=eve.getStartT()/100;
								 if(startT<=12) {
									 mypanels[startT].setEvents(eve.getStartT()+"--"+eve.getEndT()+"   "+eve.getTitle());
								 }
								 if(startT==24) {
									 mypanels[0].setEvents(eve.getStartT()+"--"+eve.getEndT()+"   "+eve.getTitle());
								 }
								 if(startT>12) {
									 mypanels[12+startT%12].setEvents(eve.getStartT()+"--"+eve.getEndT()+"   "+eve.getTitle());
								 }
							 }
						}
					}
					
				}
					
					
					
					
					
					
					//System.out.println(gC.get(Calendar.MONTH)+1);
					//System.out.println(gC.get(Calendar.DATE));
					
					
				
				//actionPerformed ends here
				
			});
			
			
			save.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					String eventTitle=eventT.getText();
					String startTime=startT.getText();
					String endTime=endT.getText();
					String dateS=date.getText();
		        	int date_event=Integer.parseInt(dateS.substring(6,10))*10000+Integer.parseInt(dateS.substring(0,2))*100+Integer.parseInt(dateS.substring(3,5));
		        	int start_time_event=Integer.parseInt(startTime.substring(0, 2))*100+Integer.parseInt(startTime.substring(3,5));
		        	int end_time_event=Integer.parseInt(endTime.substring(0, 2))*100+Integer.parseInt(endTime.substring(3,5));
		        	int i=0;
		        	boolean finish=false;
		        	boolean add=true;
		        	 while(i<events.size() && !finish) {
		        		  if(date_event==events.get(i).getDate()) {
		        			  if(!(end_time_event<=events.get(i).getStartT()) && !(start_time_event>=events.get(i).getEndT())) {
		        				  eventT.setText("Time conflict, please re-enter.");
		        				  add=false;
		        				  finish=true;
		        			  }
		        			
		        		  }
		        		  i++;
		        	  }
		        	 if(add) {
					events.add(new Event(eventTitle,date_event, start_time_event, end_time_event));
					eventDates.add(new EventDate(Integer.parseInt(dateS.substring(0,2)),Integer.parseInt(dateS.substring(3,5)),Integer.parseInt(dateS.substring(6,10))));
					eventInfo.setVisible(false);}
					
		        	 
		        	 int monthII=gC.get(Calendar.MONTH)+1;
		 			int date1I=gC.get(Calendar.DATE);
		 			int yearI=gC.get(Calendar.YEAR);
		 			for(MyPanel p:mypanels) {
		 				p.clearText();
		 			}
		 			if(DateWithEvent(date1I,month,yearI));{
		 				for(Event eve: events) {
		 					 String str=Integer.toString(eve.getDate());
		 					 if((Integer.parseInt(str.substring(0,4))==yearI && Integer.parseInt(str.substring(4,6))==monthII &&
		 							 Integer.parseInt(str.substring(6))==date1I)) {
		 						 int startTm=eve.getStartT()/100;
		 						 if(startTm<=12) {
		 							 mypanels[startTm].setEvents(eve.getStartT()+"--"+eve.getEndT()+"   "+eve.getTitle());
		 						 }
		 						 if(startTm==24) {
		 							 mypanels[0].setEvents(eve.getStartT()+"--"+eve.getEndT()+"   "+eve.getTitle());
		 						 }
		 						 if(startTm>12) {
		 							 mypanels[12+startTm%12].setEvents(eve.getStartT()+"--"+eve.getEndT()+"   "+eve.getTitle());
		 						 }
		 					 }
		 				}
		 			}
				}
		    	
		    });
		  
	}//main method ends here
	
	public static boolean DateWithEvent(int date,String month, int year) {
		int index=0;
		for(int i=0;i<monthName.length;i++) {
			if(monthName[i].equals(month)) {
				index=i;
			}
		}	
		for(int i=0;i<eventDates.size();i++) {
			if(eventDates.get(i).getYear()==year) {
				 // System.out.println("same year");
				if(index+1==eventDates.get(i).getMonth()) {
					  //System.out.println("same month");
				   if(eventDates.get(i).getDate()==date) {
				        //System.out.println("same date");
				     return true;
			  }
		  }
		}
	}
		return false;
}
	

	}
