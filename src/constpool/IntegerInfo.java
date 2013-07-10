package constpool;

import java.nio.*;
/**
 * A class that represents the Double structure in Java.
 * This is a primitive data type, so getValueAsString() will not 
 * depend on any ConstantPool
 * @author Fleur
 *
 */
public class IntegerInfo implements ConstantType, NumInfo
{
	/**
	 * The amount of bytes this class needs
	 */
	public static final int LENGTH = 4;
	/**
	 * The integer this value corresponds to
	 */
	public final int VALUE;
	
	/**
	 * The Constructor which is just an int.
	 * Similar to java.lang.Integer
	 */
	public IntegerInfo(int f)
	{
		VALUE = f;
	}
	/**
	 * Uses the epic java.nio.ByteBuffer class to figure out the double,
	 * based on the binary data.
	 * @param b
	 */
	public IntegerInfo(byte[] b)
	{
		ByteBuffer bb = ByteBuffer.wrap(b);
		VALUE = bb.getInt();
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
	 * Needs four bytes, because that is the size of an integer.
	 */
	public int getRequiredLength() 
	{
		return LENGTH;
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
		return LENGTH;
	}

	/**
	 * Returns the unique identification number for this entry into the class pool
	 */
	@Override
	public byte getTagNumber() 
	{
		return 3;
	}

	@Override
	/**
	 * Returns the String representation of the value,
	 * basically just the integer in String form.
	 * ConstantPool is not used here.
	 */
	public String getValueAsString(ConstantPool pool) 
	{
		return VALUE + "";
	}
	/**
	 * Returns the String representation of this object. It returns the Class name and the integer's value.
	 */
	public String toString()
	{
		return getClass().getSimpleName() + ": "+getValueAsString(null);
	}
	@Override
	public String getString() 
	{
		return getValueAsString(null);
	}
}
