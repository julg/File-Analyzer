package gov.nara.nwts.ftapp.counterReport;

import gov.nara.nwts.ftapp.counter.CounterData;
import gov.nara.nwts.ftapp.counter.ListCounterCheck;
import gov.nara.nwts.ftapp.counter.REV;
import gov.nara.nwts.ftapp.counter.ReportType;

public class DatabaseReport3 extends ReportType {
	public static final String NAME = "Database Report 3";
	public DatabaseReport3(REV rev, String title) {
		super(NAME, rev, title);
	}
	public DatabaseReport3() {
		super(NAME, REV.R3, "Total Searches and Sessions by Month and Service.");
	}
	public static String[] COLS = {"","Platform",""};
	public String[] getCols() {return COLS;}
	
	@Override public void initCustom(CounterData data) {
		ListCounterCheck labels = new ListCounterCheck(DatabaseReport1.FIELDS);
		labels.addAlternative("searches", "Total searches run");
		labels.addAlternative("sessions", "Total sessions");
		addCheckRange(labels, getDataRow(), getFirstDataCol()-1, data.getLastRow(), getFirstDataCol()-1); 
		checkFieldData(data, getDataRow());
		this.checkColHeader(data);

		addCheckRange(ReportType.NB_PLATFORM, getDataRow(), 1, data.getLastRow(), 1); //Plat
	}
	
	public boolean isSupported() {
		return true;
	}
	public boolean hasTotalRow() {
		return false;
	}

}
