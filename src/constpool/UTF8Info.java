package constpool;

/**
 * A class that represents UTF-8 (style) strings.
 * This is a literal, so ConstantPools are irrelevant.
 * Somewhat analogous to the String type in Java.
 * @author Fleur
 *
 */
public class UTF8Info implements ConstantType
{
	/**
	 * The amount of bytes needed.
	 * This is variable, based on the length of the String.
	 */
	public final short length;
	/**
	 * The bytes that represent a String
	 */
	public final byte[] values;
	/**
	 * A Simple Constructor.
	 * @param length the amount of bytes needed in the string.
	 * @param values the actual bytes
	 */
	public UTF8Info(short length, byte[] values)
	{
		if(values.length != length)
			throw new IllegalArgumentException("Values do not match");
		this.length = length;
		this.values = values;
	}
	@Override
	/**
	 * returns getClass().getName()
	 */
	public String getType() 
	{
		return getClass().getName();
	}

	@Override
	/**
	 * The required length of this object.
	 */
	public int getRequiredLength() 
	{
		return 2 + values.length;
	}
	/**
	 * This value is extremely hard to use, because it is variable length,
	 * and a constructor must be called in order to get the length.
	 * Here, it returns the first two bytes.
	 * @return An unhelpful number.
	 */
	public static int getLength()
	{
		return 2;
	}
	@Override
	/**
	 * Returns the unique identification number for this entry into the class pool
	 */
	public byte getTagNumber() 
	{
		return 1;
	}
	
	/**
	 * Constructs a String based on the binary values,
	 * using the String(byte[]) constructor.
	 * @return the String representation.
	 */
	public String asString()
	{
		String val = new String(values);
		return val;
	}
	@Override
	/**
	 * A method that just calls asString().
	 * The ConstantPool is irrelevant, so getValueAsString(null) is fine
	 * @see #asString()
	 */
	public String getValueAsString(ConstantPool pool) 
	{
		return asString();
	}
	/**
	 * Returns the String representation of the Object, which is the class name and the asString() function.
	 * @see #asString()
	 */
	public String toString()
	{
		return getClass().getSimpleName()+": "+asString();
	}
}
