import java.util.Calendar;
import java.util.GregorianCalendar;

public class EventDate {
   private int month;
   private int year;
   private int date;
   
   public EventDate(int month, int date, int year) {
	   this.month=month;
	   this.year=year;
	   this.date=date;
   }
   
   public int getMonth() {
	   return this.month;
	   }
   
   public int getYear() {
	   return this.year;
   }
   
   public int getDate() {
	   return this.date;
   }


public static void main(String[] args) {
	GregorianCalendar gC=new GregorianCalendar();
	gC.add(Calendar.DATE, -1);
	System.out.println(gC.get(Calendar.DATE));
}
}