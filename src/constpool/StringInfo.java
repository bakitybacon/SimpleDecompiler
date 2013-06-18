package constpool;

import java.nio.*;
/**
 * A class that models the String type in the ConstantPool.
 * It doesn't actually contain raw data. For that, see UTF8Info.
 * 
 * @author Fleur
 *
 */
public class StringInfo implements ConstantType 
{
	/**
	 * The index into the ConstantPool of the value.
	 */
	public final int index;
	/**
	 * The number of bytes to be read.
	 */
	public static final int LENGTH = 2;
	/**
	 * The resolved value of the String, set in getValueAsString.
	 */
	public String resolvedval;
	
	/**
	 * A constructor that takes one index.
	 * @param sh the index
	 */
	public StringInfo(short sh)
	{
		index = sh;
	}
	/**
	 * A constructor using just bytes.
	 * Uses java.nio.ByteBuffer to parse the index.
	 * @param b
	 */
	public StringInfo(byte[] b)
	{
		ByteBuffer bb = ByteBuffer.wrap(b);
		index = bb.getShort();
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
	 * Needs two bytes, because one index is two bytes.
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

	@Override
	/**
	 * Returns the unique identification number for this entry into the class pool
	 */
	public byte getTagNumber() 
	{
		return 8;
	}

	@Override
	/**
	 * Returns the String representation of the value,
	 * which is just the result of following the index.
	 */
	public String getValueAsString(ConstantPool pool) 
	{
		resolvedval = ((UTF8Info)pool.get(index-1)).getValueAsString(pool);
		return ((UTF8Info)pool.get(index-1)).getValueAsString(pool);
	}
	/**
	 * Returns the String representation of this object. It returns the Class name and the String's value, if resolvedval has been set.
	 * If it hasn't, it just returns unresolved.
	 */
	public String toString()
	{
		String start = getClass().getSimpleName();
		String add = resolvedval == null ? ": Unresolved" : ": "+resolvedval;
		return start + add;
	}
}
