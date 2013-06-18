package constpool;

import java.nio.*;
/**
 * A class that represents the Double structure in Java.
 * This is a primitive data type, so getValueAsString() will not 
 * depend on any ConstantPool
 * @author Fleur
 *
 */
public class DoubleInfo implements ConstantType 
{

	/**
	 * The double this value corresponds to
	 */
	public final double VALUE;
	/**
	 * The Constructor which is just a double.
	 * Similar to java.lang.Double
	 */
	public DoubleInfo(double d)
	{
		VALUE = d;
	}
	/**
	 * Uses the epic java.nio.ByteBuffer class to figure out the double,
	 * based on the binary data.
	 * @param b
	 */
	public DoubleInfo(byte[] b)
	{
		ByteBuffer bb = ByteBuffer.wrap(b);
		VALUE = bb.getDouble();
	}
	
	/**
	 * returns getClass().getName()
	 */
	@Override
	public String getType() 
	{
		return getClass().getName();
	}

	/**
	 * Needs eight bytes, because that is the size of a double.
	 */
	@Override
	public int getRequiredLength() 
	{
		return 8;
	}

	/**
	 * A static method to aid in reflection.
	 * This is helpful because you can figure out the length 
	 * of the byte array needed for the Constructor without actually calling the Constructor,
	 * because this is a static method.
	 * Basically the same as getRequiredLength(), just static
	 * @return the number of bytes needed for this class
	 */
	public static int getLength()
	{
		return 8;
	}
	
	/**
	 * Returns the unique identification number for this entry into the class pool
	 */
	@Override
	public byte getTagNumber() 
	{
		return 6;
	}

	/**
	 * Returns the String representation of the value,
	 * basically just the double in String form.
	 * ConstantPool is not used here.
	 */
	@Override
	public String getValueAsString(ConstantPool pool) 
	{
		return VALUE+"";
	}
	/**
	 * Returns the String representation of this object. It returns the Class name and the double's value.
	 */
	public String toString()
	{
		return getClass().getSimpleName() + ": "+getValueAsString(null);
	}
}
