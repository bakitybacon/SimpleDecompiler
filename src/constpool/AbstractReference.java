package constpool;

import java.nio.ByteBuffer;

/**
 * 
 * @author Fleur
 * Models the extremely similar  Field, Method, and InterfaceMethod info structures
 */
public abstract class AbstractReference implements ConstantType
{	
	/**
	 * The ConstantPool index of the class
	 */
	public final short classindex;
	/**
	 * The ConstantPool index of the name
	 */
	public final short nameindex;
	/**
	 * The resolved name, after the ConstantPool is used
	 */
	private String resolvedval = null;
	/**
	 * A basic constructor
	 * @param classindex the index of the class
	 * @param nameindex the index of the name
	 */
	public AbstractReference(short classindex, short nameindex)
	{
		this.classindex = classindex;
		this.nameindex  = nameindex;
	}
	/**
	 * A constructor in byte format
	 * @param b the bytes of the indices
	 */
	public AbstractReference(byte[] b)
	{
		byte[] p1 = {b[0],b[1]};
		byte[] p2 = {b[2],b[3]};
		ByteBuffer b1 = ByteBuffer.wrap(p1);
		ByteBuffer b2 = ByteBuffer.wrap(p2);
		classindex = b1.getShort();
		nameindex = b2.getShort();
	}
	@Override
	/**
	 * Returns getClass().getName()
	 */
	public String getType() 
	{
		return getClass().getName();
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
	/**
	 * This is the number of bytes needed for the Constructor
	 * @return the number of bytes needed
	 */
	@Override
	public int getRequiredLength() 
	{
		return 4;
	}
	@Override
	/**
	 * This basically parses the name from the ConstantPool, and assigns the name to the resolvedval variable
	 */
	public String getValueAsString(ConstantPool pool) 
	{
		resolvedval  = ((ClassInfo)pool.get(classindex-1)).getValueAsString(pool)+"."+((NameAndTypeInfo)pool.get(nameindex-1)).getValueAsString(pool);
		return resolvedval;
	}
	
	/**
	 * Returns the String representation of this object.  If resolvedval variable is defined,
	 * It will print that, or it will print "Unresolved".
	 */
	public String toString()
	{
		String start = getClass().getSimpleName();
		String add = resolvedval == null ? ": Unresolved" : ": " + resolvedval;
		return start + add;
	}
}
