package ee.mehike.haanja100.model;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import ee.mehike.haanja100.data.HaanjaTable;
import ee.mehike.haanja100.util.Util;

public class Haanja extends ModelBase {//implements Comparable<Item>  {

	   //private String name;
	   //private long id;
	   private long split;
	   //private String map_points;
	   private long time;
	   private boolean deleted;
	   private long start_time;
	   private long end_time;
	   private String avg_pace;
	   private String lap_pace;
	   private long year;
	   
	    public static final String[] PROJECTION = new String[] {
	        HaanjaTable.HaanjaColumns._ID,
	        HaanjaTable.HaanjaColumns.TIME,
	        HaanjaTable.HaanjaColumns.DELETED,
	        HaanjaTable.HaanjaColumns.START_TIME,
	        //TitleTable.TitleColumns.DESCRIPTION,
	        HaanjaTable.HaanjaColumns.END_TIME,
	        HaanjaTable.HaanjaColumns.AVG_PACE,
	        HaanjaTable.HaanjaColumns.LAP_PACE,
	        HaanjaTable.HaanjaColumns.YEAR,
	        HaanjaTable.HaanjaColumns.SPLIT,
	    }; 

	   
	   //abiväljad igaks juhuks?
	   //private double km;
	   //private long lap_time;
	   
	   //private Set<Item> items;

	   // note, in the real-world making these model beans immutable would be a better approach
	   // (that is to say, not making them JavaBeans, but makign immutable model classes with Builder)
	   
	   public Haanja() {
	   }

	public long getSplit() {
		return split;
	}

	public void setSplit(long split) {
		this.split = split;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((avg_pace == null) ? 0 : avg_pace.hashCode());
		result = prime * result + (deleted ? 1231 : 1237);
		result = prime * result + (int) (end_time ^ (end_time >>> 32));
		result = prime * result
				+ ((lap_pace == null) ? 0 : lap_pace.hashCode());
		result = prime * result + (int) (split ^ (split >>> 32));
		result = prime * result + (int) (start_time ^ (start_time >>> 32));
		result = prime * result + (int) (time ^ (time >>> 32));
		result = prime * result + (int) (year ^ (year >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Haanja other = (Haanja) obj;
		if (avg_pace == null) {
			if (other.avg_pace != null)
				return false;
		} else if (!avg_pace.equals(other.avg_pace))
			return false;
		if (deleted != other.deleted)
			return false;
		if (end_time != other.end_time)
			return false;
		if (lap_pace == null) {
			if (other.lap_pace != null)
				return false;
		} else if (!lap_pace.equals(other.lap_pace))
			return false;
		if (split != other.split)
			return false;
		if (start_time != other.start_time)
			return false;
		if (time != other.time)
			return false;
		if (year != other.year)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Haanja [split=" + split + ", time=" + time + ", deleted="
				+ deleted + ", start_time=" + start_time + ", end_time="
				+ end_time + ", avg_pace=" + avg_pace + ", lap_pace="
				+ lap_pace + ", year=" + year + "]";
	}

	public long getStart_time() {
		return start_time;
	}

	public void setStart_time(long start_time) {
		this.start_time = start_time;
	}

	public long getEnd_time() {
		return end_time;
	}

	public void setEnd_time(long end_time) {
		this.end_time = end_time;
	}

	public String getAvg_pace() {
		return avg_pace;
	}

	public void setAvg_pace(String avg_pace) {
		this.avg_pace = avg_pace;
	}

	public String getLap_pace() {
		return lap_pace;
	}

	public void setLap_pace(String lap_pace) {
		this.lap_pace = lap_pace;
	}

	public long getYear() {
		return year;
	}

	public void setYear(long year) {
		this.year = year;
	}


 }
