import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;

public class MyEvents {
   private ArrayList<Event> events;
   private ArrayList<EventDate> eventDates;
   public MyEvents() {
	   events=new ArrayList<Event>();
	   eventDates=new ArrayList<EventDate>();
   }
   
   public ArrayList<Event> getEvent(){
	   return this.events;
   }
   
   public ArrayList<EventDate> getEventDate(){
	   return this.eventDates;
   }
   
//Sort events all by their starting time
   public void sortEvents(){
	   Collections.sort(this.events, new Comparator<Event>() {

		@Override
		public int compare(Event o1, Event o2) {
			
			return o1.getStartT()-o2.getStartT();
		}
		});	  
	   
   } 
	
   //Sort events by date, if equal, by starting time
   public void sortEventsByDate() {
	   Collections.sort(this.events, new Comparator<Event>() {

		@Override
		public int compare(Event o1, Event o2) {
		     if(o1.getDate()==o2.getDate()) {
		    	    if(o1.getStartT()<o2.getStartT()) {
		    	    	 return -1;
		    	    }
		    	    else if(o1.getStartT()>o2.getStartT()) {
		    	    	return 1;
		    	    }
		    	    else {return 0;}
		     }
		     else if(o1.getDate()>o2.getDate()) {
		    	 return 1;
		     }
		     else {
		    	 return -1;
		     }
		}
		   
	   });
   }
}
