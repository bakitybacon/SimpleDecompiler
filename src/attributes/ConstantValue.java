package attributes;

import java.nio.ByteBuffer;
import constpool.ConstantPool;

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
		getValue();
	}
	
	/**
	 * A method to return the type of this object
	 * @return the name of this Object
	 */
	public String getValue()
	{
		return cp.get(constindex-1).getValueAsString(cp);
	}
	
	/**
	 * A toString() method that returns
	 *  
	 */
	public String toString()
	{
		return getClass().getName()+"[Value:"+getValue()+"]";
	}
}
