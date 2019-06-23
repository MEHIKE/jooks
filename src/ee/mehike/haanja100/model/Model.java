package ee.mehike.haanja100.model;

import android.util.Log;

public class Model {

	private String name;
	private String desc;
	private String time;
	private boolean selected;

	public Model() {
		Log.d("test", "model "+name);
		selected = false;
	}
	
	public Model(String name) {
		Log.d("test", "model "+name);
		this.name = name;
		selected = false;
	}

	public Model(String name, String desc) {
		Log.d("test", "model "+name+" desc "+desc);
		this.name = name;
		this.desc=desc;
		selected = false;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

} 
