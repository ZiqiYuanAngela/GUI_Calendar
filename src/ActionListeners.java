import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class ActionListeners implements ActionListener {

	private int id;
	private JButton[] buttons;
	private MyPanel[] panels;
	private ArrayList<Event> events;
	private JLabel dayViewlabel;
	private GregorianCalendar gC;
	private boolean justClicked;
	private ActionListeners[] aLs;
	private ActionListeners next;
	private JTextField jt;
	private MyEvents e;
	private static final String[] daysOfWeek= {"Sunday", "Monday", "Tuesday", "Wednesday","Thursday","Friday","Saturday"};
	private static final String[] monthName= {"January","February","March","April","May", "June", "July","August", "September",
			"October","November","December"};
	
	public ActionListeners(int id, JButton[] buttons, MyPanel[] panels, JLabel label, GregorianCalendar gC,JTextField jt, MyEvents myE) {
		this.buttons=buttons;
		this.panels=panels;
		//this.events=events;
		this.id=id;
		dayViewlabel=label;
		this.gC=gC;
		justClicked=false;
		this.jt=jt;
        this.e=myE;
	}
	
	public int getID() {
		return this.id;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		boolean updateGC=false;
		GregorianCalendar updatedGC=null;
		for(ActionListeners aL: this.aLs) {
			if(aL.IfjustClicked()) {
				aL.ResetJustC();
				updateGC=true;
				updatedGC=aL.getGC();
			}
		}
		if(updateGC) {
		for(ActionListeners aL:this.aLs) {
			aL.setGC(updatedGC);
		}
		}
		
		    for(int i=0;i<buttons.length;i++) {
 			
            buttons[i].setBorder(BorderFactory.createEmptyBorder(i%7, i%6, 1, 1));
 			
 	
 		}
		this.justClicked=true;
		buttons[this.id].setBorder(new LineBorder(Color.BLACK));
		int today_day=gC.get(Calendar.DATE);
		int target_day=Integer.parseInt(buttons[this.id].getText());
		gC.add(Calendar.DATE, target_day-today_day);
		int month=gC.get(Calendar.MONTH)+1;
		int date=gC.get(Calendar.DATE);
	    dayViewlabel.setText("                           "+daysOfWeek[this.id%7]+"   "+month+"/"+date);
        dayViewlabel.setFont(new Font(("                           "+daysOfWeek[this.id%7]+"   "+month+"/"+date),Font.BOLD, 20));
		
        String month1=Integer.toString(gC.get(Calendar.MONTH)+1);
		String date1=Integer.toString(gC.get(Calendar.DATE));
		String year=Integer.toString(gC.get(Calendar.YEAR));
		if(gC.get(Calendar.MONTH)+1<10) {
			month1="0"+month1;
		}
		if(gC.get(Calendar.DATE)<10) {
			date1="0"+date1;
		}
		this.jt.setText(month1+"/"+date1+"/"+year);
		int monthI=gC.get(Calendar.MONTH)+1;
		int date1I=gC.get(Calendar.DATE);
		int yearI=gC.get(Calendar.YEAR);
		for(MyPanel p:panels) {
			p.clearText();
		}
		if(DateWithEvent(date1I,month1,yearI));{
			for(Event eve: this.e.getEvent()) {
				 String str=Integer.toString(eve.getDate());
				 if((Integer.parseInt(str.substring(0,4))==yearI && Integer.parseInt(str.substring(4,6))==monthI &&
						 Integer.parseInt(str.substring(6))==date1I)) {
					 int startT=eve.getStartT()/100;
					 if(startT<=12) {
						 panels[startT].setEvents(eve.getStartT()+"--"+eve.getEndT()+"   "+eve.getTitle());
					 }
					 if(startT==24) {
						 panels[0].setEvents(eve.getStartT()+"--"+eve.getEndT()+"   "+eve.getTitle());
					 }
					 if(startT>12) {
						 panels[12+startT%12].setEvents(eve.getStartT()+"--"+eve.getEndT()+"   "+eve.getTitle());
					 }
				 }
			}
		}
		
	}
		
		
		
		
		
		
	
	
	public void setGC(GregorianCalendar gC) {
		this.gC=gC;
	}
	
	public boolean IfjustClicked() {
		return this.justClicked;
	}
	
	public GregorianCalendar getGC() {
		return this.gC;
	}
	
	public void ResetJustC() {
		this.justClicked=false;
	}
	
	public void setALs(ActionListeners[] aLs) {
		this.aLs=aLs;
		
	}
	public boolean DateWithEvent(int date,String month, int year) {
		int index=0;
		for(int i=0;i<monthName.length;i++) {
			if(monthName[i].equals(month)) {
				index=i;
			}
		}	
		for(int i=0;i<e.getEventDate().size();i++) {
			if(e.getEventDate().get(i).getYear()==year) {
				 // System.out.println("same year");
				if(index+1==e.getEventDate().get(i).getMonth()) {
					  //System.out.println("same month");
				   if(e.getEventDate().get(i).getDate()==date) {
				        //System.out.println("same date");
				     return true;
			  }
		  }
		}
	}
		return false;
}

}
