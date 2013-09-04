package edu.georgetown.library.fileAnalyzer.filetest;

import gov.nara.nwts.ftapp.FTDriver;
import gov.nara.nwts.ftapp.filetest.DefaultFileTest;
import gov.nara.nwts.ftapp.filter.PdfFileTestFilter;
import gov.nara.nwts.ftapp.stats.Stats;
import gov.nara.nwts.ftapp.stats.StatsGenerator;
import gov.nara.nwts.ftapp.stats.StatsItem;
import gov.nara.nwts.ftapp.stats.StatsItemConfig;
import gov.nara.nwts.ftapp.stats.StatsItemEnum;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;

/**
 * Extract all metadata fields from a TIF or JPG using categorized tag defintions.
 * @author TBrady
 *
 */
class PageCount extends DefaultFileTest { 
	private static enum PagesStatsItems implements StatsItemEnum {
		Key(StatsItem.makeStringStatsItem("Path", 200)),
		Pages(StatsItem.makeIntStatsItem("Pages"))
		;
		
		StatsItem si;
		PagesStatsItems(StatsItem si) {this.si=si;}
		public StatsItem si() {return si;}
	}

	public static enum Generator implements StatsGenerator {
		INSTANCE;
		class PagesStats extends Stats {
			public PagesStats(String key) {
				super(details, key);
			}

		}
		public PagesStats create(String key) {return new PagesStats(key);}
	}
	public static StatsItemConfig details = StatsItemConfig.create(PagesStatsItems.class);

	long counter = 1000000;
	public PageCount(FTDriver dt) {
		super(dt);
	}

	public String toString() {
		return "Page Count";
	}
	public String getKey(File f) {
		return f.getPath();
	}
	
    public String getShortName(){return "Pg";}

    
	public Object fileTest(File f) {
		Stats s = getStats(f);
		int x = 0;
		try {
			FileInputStream fis = new FileInputStream(f);
			PDFParser pp = new PDFParser(fis);
			pp.parse();
			PDDocument pd = pp.getPDDocument();
			x = pd.getNumberOfPages();
			s.setVal(PagesStatsItems.Pages, x);
			pd.close();
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			//e.printStackTrace();
		}
		
		return x;
	}
    public Stats createStats(String key){ 
    	return Generator.INSTANCE.create(key);
    }
    public StatsItemConfig getStatsDetails() {
    	return details; 
    }

	public void initFilters() {
		filters.add(new PdfFileTestFilter());
	}

	public String getDescription() {
		return "";
	}

}
