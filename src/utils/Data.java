package utils;

public class Data<T> {

	private String nameInTable;
	private T data;

	public Data(String _nameInTable, T _data) {
		this.nameInTable = _nameInTable;
		this.data = _data;
	}

	public String getNameInTable() {
		return nameInTable;
	}

	public void setNameInTable(String nameInTable) {
		this.nameInTable = nameInTable;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
