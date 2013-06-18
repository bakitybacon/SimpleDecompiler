package constpool;

import java.nio.*;

/**
 * An Object that models the ClassInfo type.
 * This consists only of a UTF8Info structure,
 * representing the name of the Class.
 * @author Fleur
 *
 */
public class ClassInfo implements ConstantType
{
	/**
	 * the amount of bytes to be read in this class
	 */
	//reads two bytes in
	public static final int readlength = 2;
	/**
	 * the index into the ConstantPool of the name
	 */
	public final short index;
	/**
	 * The name in String format. After getValueAsString() is called, this will be set forever
	 */
	public String resolvedval = null;
	/**
	 * A simple constructor
	 * @param index the index into the ConstantPool of the name
	 */
	public ClassInfo(short index)
	{
		this.index = index;
	}
	
	/**
	 * A convience method that does the same thing as the other constructor
	 * @param b the index, in bytes
	 */
	public ClassInfo(byte[] b)
	{
		ByteBuffer bb = ByteBuffer.wrap(b);
		index = bb.getShort();
	}
	@Override
	/**
	 * A method that returns the name
	 */
	public String getType() 
	{
		return this.getClass().getName();
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
		return readlength;
	}
	
	/**
	 * This is the number of bytes needed for the Constructor
	 * @return the number of bytes needed
	 */
	@Override
	public int getRequiredLength() 
	{
		return readlength;
	}
	
	/**
	 * This is the byte in class files that specifies what the information is.
	 * This is unique to each class
	 */
	@Override
	public byte getTagNumber() 
	{
		return 7;
	}
	@Override
	/**
	 * This parses the name from the ConstantPool, which is then assigned
	 *  to the resolvedval variable and returned
	 */
	public String getValueAsString(ConstantPool pool) 
	{
		resolvedval = ((UTF8Info)pool.get(index-1)).getValueAsString(pool);
		return ((UTF8Info)pool.get(index-1)).getValueAsString(pool);
	}
	/**
	 * Returns the String representation of this object. Depends on the resolvedval variable.
	 * If getValueAsString() has not been called, it will return Unresolved.
	 * Otherwise, it will return the actual name
	 */
	public String toString()
	{
		String start =  getClass().getSimpleName();
		String add = resolvedval == null ? ": Unresolved" : ": "+resolvedval;
		return start + add;
	}
}
