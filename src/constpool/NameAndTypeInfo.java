package constpool;

import java.nio.*;

/**
 * A class that models the NameAndTypeInfo type.
 * This is for Methods, and returns the name of method and type signature.
 * @author Fleur
 */
public class NameAndTypeInfo implements ConstantType 
{
	/**
	 * The index into the ConstantPool of the name
	 */
	public final short nindex;
	/**
	 * The index into the ConstantPool of the description
	 */
	public final short dindex;
	/**
	 * The final value of the String, which is assigned in the getValueAsString method
	 */
	private String resolvedval = null;
	
	/**
	 * A simple constructor
	 * @param nameindex The index into the ConstantPool of the name
	 * @param descindex The index into the ConstantPool of the description
	 */
	public NameAndTypeInfo(short nameindex, short descindex)
	{
		nindex = nameindex;
		dindex = descindex;
	}
	/**
	 * Another simple constructor, with bytes
	 * @param b the name and description indices, in that order
	 */
	public NameAndTypeInfo(byte[] b)
	{
		byte[] p1 = {b[0],b[1]};
		byte[] p2 = {b[2],b[3]};
		ByteBuffer b1 = ByteBuffer.wrap(p1);
		ByteBuffer b2 = ByteBuffer.wrap(p2);
		nindex = b1.getShort();
		dindex = b2.getShort();
	}
	/**
	 * returns getClass().getName()
	 */
	@Override
	public String getType() 
	{
		return getClass().getName();
	}

	@Override
	/**
	 * This is the number of bytes needed for the Constructor
	 * @return the number of bytes needed
	 */
	public int getRequiredLength() 
	{
		return 4;
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
		return 4;
	}
	
	@Override
	/**
	 * Returns the unique identification number for this entry into the class pool
	 */
	public byte getTagNumber() 
	{
		return 0x0C;
	}

	@Override
	/**
	 * Returns the String representation of the value,
	 * which is the name String and the description String, in that order
	 */
	public String getValueAsString(ConstantPool pool) 
	{
		resolvedval   = ((UTF8Info)pool.get(nindex-1)).getValueAsString(pool)+":"+((UTF8Info)pool.get(dindex-1)).getValueAsString(pool);
		return resolvedval;
	}
	/**
	 * returns the String representation of this object,
	 * basically dependent upon the resolvedval variable.  If it is defined, it will print that.
	 * If not, it will print unresolved. To resolve it, call getValueAsString().
	 */
	public String toString()
	{
		String start = getClass().getSimpleName();
		String add = resolvedval == null ? ": Unresolved" : ": " + resolvedval;
		return start + add;
	}
}
