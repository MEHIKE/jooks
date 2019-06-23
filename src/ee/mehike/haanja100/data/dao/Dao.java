package ee.mehike.haanja100.data.dao;

import java.util.List;

public interface Dao<T> {
	long save(T type);
	void update(T type);
	void delete(T type);
	T get(long id);
	List<T> getAll();
	List<T> getAll(long year);
}
