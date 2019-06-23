package ee.mehike.haanja100.model;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import ee.mehike.haanja100.data.HaanjaTable;
import ee.mehike.haanja100.util.Util;

public class Race extends ModelBase {//implements Comparable<Item>  {

	   //private String name;
	   //private long id;
	
 	//võistlus - id, nimi, ringid, pikkus, delete, description
	
	   private String name;
	   //private String map_points;
	   private String description;
	   private boolean deleted;
	   private long splits;
	   private double race_km;
	   
	   
	   //abiväljad igaks juhuks?
	   //private double km;
	   //private long lap_time;
	   
	   //private Set<Item> items;

	   // note, in the real-world making these model beans immutable would be a better approach
	   // (that is to say, not making them JavaBeans, but makign immutable model classes with Builder)
	   
	   public Race() {
	   }


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public long getSplits() {
		return splits;
	}


	public void setSplits(long splits) {
		this.splits = splits;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (deleted ? 1231 : 1237);
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		long temp;
		temp = Double.doubleToLongBits(race_km);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (splits ^ (splits >>> 32));
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
		Race other = (Race) obj;
		if (deleted != other.deleted)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Double.doubleToLongBits(race_km) != Double
				.doubleToLongBits(other.race_km))
			return false;
		if (splits != other.splits)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Race [name=" + name + ", description=" + description
				+ ", deleted=" + deleted + ", splits=" + splits + ", race_km="
				+ race_km + "]";
	}


	public void setRace_km(double race_km) {
		this.race_km = race_km;
	}


	public boolean isDeleted() {
		return deleted;
	}


	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}


	public double getRace_km() {
		return race_km;
	}


 }
