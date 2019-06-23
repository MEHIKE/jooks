package ee.mehike.haanja100.model;

public class ModelBase {

	   protected long id;
	   protected long remote_id;
	   protected boolean synced;
	   protected long last_sync_time;

	   public long getId() {
	      return this.id;
	   }

	   public void setId(long id) {
	      this.id = id;
	   }

	   @Override
	public String toString() {
		return "ModelBase [id=" + id + ", remote_id=" + remote_id + ", synced="
				+ synced + ", last_sync_time=" + last_sync_time + "]";
	}

	   @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result
				+ (int) (last_sync_time ^ (last_sync_time >>> 32));
		result = prime * result + (int) (remote_id ^ (remote_id >>> 32));
		result = prime * result + (synced ? 1231 : 1237);
		return result;
	}

	   @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ModelBase other = (ModelBase) obj;
		if (id != other.id)
			return false;
		if (last_sync_time != other.last_sync_time)
			return false;
		if (remote_id != other.remote_id)
			return false;
		if (synced != other.synced)
			return false;
		return true;
	}

	public long getRemote_id() {
		return remote_id;
	}

	public void setRemote_id(long remote_id) {
		this.remote_id = remote_id;
	}

	public boolean isSynced() {
		return synced;
	}

	public void setSynced(boolean synced) {
		this.synced = synced;
	}

	public long getLast_sync_time() {
		return last_sync_time;
	}

	public void setLast_sync_time(long last_sync_time) {
		this.last_sync_time = last_sync_time;
	} 
}
