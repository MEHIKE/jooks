package ee.mehike.haanja100.model;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import ee.mehike.haanja100.data.HaanjaTable;
import ee.mehike.haanja100.util.Util;

public class Year extends ModelBase {//implements Comparable<Item>  {

	   //private String name;
	   //private long id;
	
	//aasta - id, võistlus_id, aasta,  best_ajad(eesmärgid) võivad iga aasta olla erinevad
	
	   private long race_id;
	   //private String map_points;
	   private long year;
	   private boolean delete;
	   private long best_tim1;
	   private long best_tim2;
	   private long best_tim3;
	   
	   //abiväljad igaks juhuks?
	   //private double km;
	   //private long lap_time;
	   
	   //private Set<Item> items;

	   // note, in the real-world making these model beans immutable would be a better approach
	   // (that is to say, not making them JavaBeans, but makign immutable model classes with Builder)
	   
	   public Year() {
	   }

	public long getRace_id() {
		return race_id;
	}

	public void setRace_id(long race_id) {
		this.race_id = race_id;
	}

	public long getYear() {
		return year;
	}

	public void setYear(long year) {
		this.year = year;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	public long getBest_tim1() {
		return best_tim1;
	}

	public void setBest_tim1(long best_tim1) {
		this.best_tim1 = best_tim1;
	}

	public long getBest_tim2() {
		return best_tim2;
	}

	public void setBest_tim2(long best_tim2) {
		this.best_tim2 = best_tim2;
	}

	public long getBest_tim3() {
		return best_tim3;
	}

	public void setBest_tim3(long best_tim3) {
		this.best_tim3 = best_tim3;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) (best_tim1 ^ (best_tim1 >>> 32));
		result = prime * result + (int) (best_tim2 ^ (best_tim2 >>> 32));
		result = prime * result + (int) (best_tim3 ^ (best_tim3 >>> 32));
		result = prime * result + (delete ? 1231 : 1237);
		result = prime * result + (int) (race_id ^ (race_id >>> 32));
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
		Year other = (Year) obj;
		if (best_tim1 != other.best_tim1)
			return false;
		if (best_tim2 != other.best_tim2)
			return false;
		if (best_tim3 != other.best_tim3)
			return false;
		if (delete != other.delete)
			return false;
		if (race_id != other.race_id)
			return false;
		if (year != other.year)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Year [race_id=" + race_id + ", year=" + year + ", delete="
				+ delete + ", best_tim1=" + best_tim1 + ", best_tim2="
				+ best_tim2 + ", best_tim3=" + best_tim3 + "]";
	}


 }
