package attributes;

import java.nio.ByteBuffer;
import constpool.ConstantPool;
import constpool.UTF8Info;

public class ConstantValue implements Attribute
{
	/**
	 * The amount needed for this attribute
	 */
	public static final int length = 2;
	/**
	 * The index into the ConstantPool this will use
	 */
	public final short constindex;
	/**
	 * The ConstantPool used to parse information
	 */
	public final ConstantPool cp;
	/**
	 * A simple constructor
	 * @param constindex The index into the ConstantPool
	 * @param cp the ConstantPool
	 */
	public ConstantValue(short constindex,ConstantPool cp)
	{
		this.constindex = constindex;
		this.cp = cp;
	}
	/**
	 * A byte array version of the other constructor
	 * @param b the bytes, which will be transformed into constindex
	 * @param cp the ConstantPool
	 */
	public ConstantValue(byte[]b,ConstantPool cp)
	{
		this(ByteBuffer.wrap(b).getShort(),cp);
	}
	/**
	 * The method used to parse values from the ConstantPool
	 * This parses the value from the ConstantPool
	 * The method used is getName().
	 * @see #getName()
	 */
	@Override
	public void parseValues(ConstantPool cp) 
	{
		getName();
	}
	
	/**
	 * A method to return the name of the constant
	 * Uses the ConstantPool 
	 * @return the name of this Object
	 */
	public String getName()
	{
		return ((UTF8Info)cp.get(constindex)).getValueAsString(cp);
	}
	
	/**
	 * A toString() method that returns
	 *  
	 */
	public String toString()
	{
		return getClass().getName()+"[Value:"+getName()+"]";
	}
}
