package mat;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;

public class Matt implements Serializable {
	private static final long serialVersionUID = 5L;//must be changed when changing data fields
	static public String delimiter=",";
	static public final int minInWeek = 10080;
	static public final long mseñInWeek =86400000L;
	public Calendar currentWeek;

	MattData data;//parameters of MAT
	ArrayList<Boolean> slots;//false - available, true - not selected
	HashMap<Integer, SlotInfo> slotsInfo = null;//key - slot number(starts from 0)
	
	public MattData getData() {return data;}
	public void setData(MattData data) {this.data = data;}
	public ArrayList<Boolean> getSlots() {return slots;}
	public void setSlots(ArrayList<Boolean> slots) {this.slots = slots;}
	public HashMap<Integer, SlotInfo> getSlotsInfo() {return slotsInfo;}
	public void setSlotsInfo(HashMap<Integer, SlotInfo> slotsInfo) {this.slotsInfo = slotsInfo;}
	
	public void addSlotInfo(int numberDay, int numberSlotInDay, String nameGuest, String surnameGuest,
			String emailGuest, String phoneGuest, String noteGuest){
		int countSlotsInDay = Matt.minInWeek/7/this.data.getTimeSlot();
		int firstSlot = (int)((currentWeek.getTimeInMillis()-data.getStartDate().getTime())/Matt.mseñInWeek*7*countSlotsInDay);
		int numberSlot = firstSlot+numberDay*countSlotsInDay+numberSlotInDay;
		slotsInfo.put(numberSlot, new SlotInfo(nameGuest, surnameGuest, emailGuest, phoneGuest, noteGuest));
	}

//-----------------------------------old code----------------------
	public String mattToJSON() {
	  String res=data.mattDataToJSON();
	  if(slots!=null){
	   res=res+','+"[";
	  for(boolean it:slots){
	  res+=(it?1:0)+delimiter;}
	  res=res.substring(0,res.length()-1);
	  res="{"+res+"]"+"}";
	  }
	  return res;
	 }
	
	public String arrayList2JSON(){
	 String res=null;
	 if(slots!=null){
	   res="[";
	  for(boolean it:slots){
	  res+=(it?1:0)+delimiter;}
	  res=res.substring(0,res.length()-1);
	  res=res+"]";
	  }
	  return res;
	}
	
	public String matt2browser(){
	 /*[["Vasya"],["Mon","Tue","Wen","Thu"],["20.09","21.09","22.09","23.09"]
	 ,["10:00","10:30","11:00","11:30","12:00"]
	 ,[[0,1,0,0],[1,0,0,1],[0,0,1,1],[0,0,0,1]
	 ,[1,1,0,1]]]*/  //getting this view for browser.
	 
	 StringBuffer daysStr=null;
	 String name=data.getName();
	 Date startDate=data.getStartDate();
	 int nDays=data.getnDays();
	 int startHour=data.getStartHour();
	 int endHour=data.getEndHour();
	 int timeSlot=data.getTimeSlot();
	 int timeSlots;
	 int hours=0;
	 
	 if (!(startHour==endHour)) {
	  if (startHour < endHour) {
	   hours = (endHour - startHour) * 60;
	  } else {
	   hours = (24 - startHour + endHour) * 60;
	  }
	  timeSlots=hours/timeSlot;
	 }
	 else{
	  hours = (24 - startHour + endHour) * 60;
	  timeSlots=(hours/timeSlot)-1;
	 }
	 
	 Calendar cal = new GregorianCalendar(Locale.US);
	 cal.setTime(startDate);
	   
	 SimpleDateFormat dfDays=new SimpleDateFormat("EEE",Locale.US); 
	 daysStr = new StringBuffer();
	 daysStr.append("[[");
	 daysStr.append('"');
	 daysStr.append(name);
	 daysStr.append('"');
	 daysStr.append("],[");
	 daysStr.append('"');
	 daysStr.append(dfDays.format(cal.getTime()));
	 daysStr.append('"');
	 for(int i=1;i<nDays;i++){
	  daysStr.append(delimiter);
	  cal.add(Calendar.DAY_OF_WEEK, 1);
	  daysStr.append('"');
	  daysStr.append(dfDays.format(cal.getTime()));
	  daysStr.append('"');
	 } daysStr.append("],");
	 
	 cal.setTime(startDate);
	 dfDays.applyPattern("dd.MMM");
	 daysStr.append("[");
	 daysStr.append('"');
	 daysStr.append(dfDays.format(cal.getTime()));
	 daysStr.append('"');
	 for(int i=1;i<nDays;i++){
	  daysStr.append(delimiter);
	  cal.add(Calendar.DAY_OF_WEEK, 1);
	  daysStr.append('"');
	  daysStr.append(dfDays.format(cal.getTime()));
	  daysStr.append('"');
	 } daysStr.append("],");
	 
	 cal.setTime(startDate);
	 cal.set(Calendar.HOUR_OF_DAY,startHour);
	 cal.set(Calendar.MINUTE,0);
	 dfDays.applyPattern("HH:mm");
	 daysStr.append("[");
	 daysStr.append('"');
	 daysStr.append(dfDays.format(cal.getTime()));
	 daysStr.append('"');
	 for(int i=1;i<timeSlots;i++){
	  daysStr.append(delimiter);
	  cal.add(Calendar.MINUTE, timeSlot);
	  daysStr.append('"');
	  daysStr.append(dfDays.format(cal.getTime()));
	  daysStr.append('"');
	 } daysStr.append("],");
	 
	 if(slots!=null){
	  Object[] tmpArr=slots.toArray();
	   daysStr.append("[");
	   int count=0;
	  for(int i=0;i<timeSlots;i++){
	   daysStr.append("[");
	   count=i;
	   for(int j=0;j<nDays;j++){
	  if((boolean) tmpArr[count].equals(true)){
	  daysStr.append(1);}
	  else{
	  daysStr.append(0);}
	  daysStr.append(',');
	  count=count+timeSlots;
	   }
	   daysStr.insert(daysStr.length()-1, "]");
	   }
	  int t=daysStr.length();
	  daysStr.setLength(t-1);
	  daysStr.append("]]");
	  } 
	 return daysStr.toString();
	}
	 public static ArrayList<Boolean> fromBrowser2ArrayList(String newTablJSON){
	  /*[["vasya"],["Mon","Tue"],["13.10","14.10"],
	   * ["19:00","19:15","19:30","19:45","20:00","20:15","20:30","20:45"],
	   * [[0,1],[0,0],[1,0],[0,1],[0,0],[1,0],[0,1],[0,0]]]*/ //----parsing it to ArrayList----
	  String delimTmp="[[";
	  ArrayList<Boolean> newTabList=null;
	  if(!(newTablJSON==null)){
	  newTabList=new ArrayList<Boolean>();
	  int index1=newTablJSON.lastIndexOf(delimTmp);
	  String strTmp=newTablJSON.substring(index1+1, newTablJSON.length()-2);
	  String str4nDays=(String) strTmp.substring(strTmp.lastIndexOf("[")+1,strTmp.lastIndexOf("]"));
	  str4nDays=str4nDays.replaceAll(",", "");
	  int numDays=str4nDays.length();
	  strTmp=strTmp.replace('[', ' ');
	  strTmp=strTmp.replaceAll(",","").replaceAll("]", "").replaceAll(" ","");
	  char[]buf=strTmp.toCharArray();
	  Boolean e;
	  int n=0;
	  for(int t=0;t<numDays;++t){
	   n=t;
	   for(int m=0;m<(buf.length/numDays);m++){
		   e=(buf[n]=='0'?false:true);
	     newTabList.add(e);
	     n=n+numDays;
	   }
	  }
	 }
	return newTabList;}
//--------------------------------------------------------------------------------------	
	public void weekFromBrowser(String jsonWeek){
		ArrayList<Boolean> weekArrList = Matt.fromBrowser2ArrayList(jsonWeek);
		int countSlotsInDay = Matt.minInWeek/7/this.data.getTimeSlot();
		int firstSlot = (int)((currentWeek.getTimeInMillis()-data.getStartDate().getTime())/Matt.mseñInWeek*7*countSlotsInDay);
		for(int i=0; i<weekArrList.size(); i++)
			this.slots.set(i+firstSlot, weekArrList.get(i));
	}
	public String week2browser(Date currentDay){
		StringBuilder jsonWeek = new StringBuilder();
		Calendar calendar = new GregorianCalendar();
		Calendar matCalendar = new GregorianCalendar();
		matCalendar.setTime(this.data.getStartDate());
		calendar.setTime(currentDay);
		if(calendar.before(matCalendar))
			return null;
		matCalendar.add(Calendar.DATE, this.data.getnDays());
//		calendar.set(Calendar.DAY_OF_WEEK, 1);
		if(calendar.before(matCalendar))
			jsonWeek = getWeek(calendar);
		else {
			jsonWeek = getFreeWeek(calendar);
			addWeeks(calendar);
		}
		currentWeek = calendar;
		return jsonWeek.toString();
	}
	private void addWeeks(Calendar calendar) {
		long curentDate = this.data.getStartDate().getTime()+data.getnDays()/7*Matt.mseñInWeek;
		//how many weeks is it necessary to add
		int countAddingWeeks = (int) ((calendar.getTimeInMillis()-curentDate)/Matt.mseñInWeek)+1;
		ArrayList<Boolean> weekSlots = new ArrayList<Boolean>(Matt.minInWeek/this.data.getTimeSlot());
		for(int i=0; i<weekSlots.size(); i++)
			weekSlots.set(i, false);
		for(int i=0; i<countAddingWeeks; i++)
			this.slots.addAll(weekSlots);
		//changing nDays
		data.setnDays(data.getnDays()+countAddingWeeks);
	}
	
	private StringBuilder getFreeWeek(Calendar calendar) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(calendar.getTime());
		StringBuilder weekStr = new StringBuilder();
		SimpleDateFormat dfDays=new SimpleDateFormat("EEE",Locale.US); 
		dfDays.applyPattern("dd.MMM");
		weekStr.append("[[\""+data.getName()+"\"],[\"Mon\",\"Tue\",\"Wen\",\"Thu\",\"Fri\",\"Sat\",\"Sun\"],[\""+dfDays.format(calendar.getTime())+"\"");
		for(int i=0; i<6; i++){
			cal.add(Calendar.DAY_OF_YEAR, 1);
			weekStr.append(",\""+dfDays.format(cal.getTime())+"\"");
		}
		cal.setTime(calendar.getTime());
		weekStr.append("],[\"00:00\"");
		dfDays.applyPattern("HH:mm");
		int countSlotsInDay = Matt.minInWeek/7/this.data.getTimeSlot();
		for(int i=1; i<countSlotsInDay; i++){
			cal.add(Calendar.MINUTE, data.getTimeSlot());
			weekStr.append(",\""+dfDays.format(cal.getTime())+"\"");
		}
		weekStr.append("],[[1,1,1,1,1,1,1]");
		for(int i=1; i<countSlotsInDay; i++)
			weekStr.append(",[1,1,1,1,1,1,1]");
		weekStr.append("]]");
		return weekStr;
	}
	private StringBuilder getWeek(Calendar calendar) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(calendar.getTime());
		StringBuilder weekStr = new StringBuilder();
		SimpleDateFormat dfDays=new SimpleDateFormat("EEE",Locale.US); 
		dfDays.applyPattern("dd.MMM");
		weekStr.append("[[\""+data.getName()+"\"],[\"Mon\",\"Tue\",\"Wen\",\"Thu\",\"Fri\",\"Sat\",\"Sun\"],[\""+dfDays.format(calendar.getTime())+"\"");
		for(int i=0; i<6; i++){
			cal.add(Calendar.DAY_OF_YEAR, 1);
			weekStr.append(",\""+dfDays.format(cal.getTime())+"\"");
		}
		cal.setTime(calendar.getTime());
		weekStr.append("],[\"00:00\"");
		dfDays.applyPattern("HH:mm");
		int countSlotsInDay = Matt.minInWeek/7/this.data.getTimeSlot();
		for(int i=1; i<countSlotsInDay; i++){
			cal.add(Calendar.MINUTE, data.getTimeSlot());
			weekStr.append(",\""+dfDays.format(cal.getTime())+"\"");
		}
		weekStr.append("],[");
		if(slots!=null){
			int count;
			int firstSlot = (int)((calendar.getTimeInMillis()-data.getStartDate().getTime())/Matt.mseñInWeek*7*countSlotsInDay);
			for(int i=0;i<countSlotsInDay;i++){
			   weekStr.append("[");
			   count=firstSlot+i;
			   for(int j=0;j<7;j++){
				   if(slots.get(count))
					   weekStr.append("1");
				   else
					   weekStr.append("0");
				   if(j!=6) weekStr.append(",");
				   count=count+countSlotsInDay;
			   }
			   weekStr.append("],");
			}
		}
		weekStr.deleteCharAt(weekStr.length()-1);
		weekStr.append("]]");
		return weekStr;
	}
	
}