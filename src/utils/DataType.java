package utils;

/**
 * A structure representing a database data type
 */
public class DataType {

	private String name, type;
	private long id;

	/**
	 * Constructor
	 * 
	 * @param _name  The name for the lookup table when added to
	 * 				 {@link DataTypes}.
	 * @param _type  What to convert the data to latter.
	 */
	public DataType(String _name, Class<?> _type) {
		setName(_name);
		this.setType(_type);
	}

	/**
	 * 
	 * @return  The name of the DataType
	 */
	public String getName() {
		return name;
	}
	
	public void setName(String _name) {
		this.name = _name;
	}

	public String getType() {
		return type;
	}

	public void setType(Class<?> _type) {
		this.type = _type.getSimpleName();
	}
	
	public void setId(long _id) {
		this.id = _id;
	}
	
	public long getId() {
		return id;
	}

}