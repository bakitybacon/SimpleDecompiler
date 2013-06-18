package attributes;

import java.nio.ByteBuffer;

import constpool.ConstantPool;
import constpool.UTF8Info;

/**
 * Models the SourceFile attribute
 * Helpful for figuring out what file this came from
 * @author Fleur
 */
public class SourceFile implements Attribute 
{
	/**
	 * The resolvedval, figured out by parseValues
	 */
	public String resolvedval = null;
	/**
	 * The name index into the ConstantPool
	 */
	public final short index;
	/**
	 * The ConstantPool itself
	 */
	public final ConstantPool cp;
	/**
	 * A constructor of the index
	 * @param index the index of the name into the ConstantPool
	 * @param cp the actual ConstantPool from which data is parsed
	 */
	public SourceFile(short index,ConstantPool cp)
	{
		this.index = index;
		this.cp = cp;
		parseValues(cp);
	}
	/**
	 * A convenience method
	 * @param b the index of the name in the ConstantPool, in byte form
	 * @param cp the actual ConstantPool from which data is parsed
	 */
	public SourceFile(byte[] b,ConstantPool cp)
	{
		this(ByteBuffer.wrap(b).getShort(),cp);
	}
	@Override
	/**
	 * A method that assigns a value to the name variable
	 * Gets a String representation, using the ConstantPool
	 */
	public void parseValues(ConstantPool cp) 
	{
		UTF8Info utf = (UTF8Info)cp.get(index-1);
		resolvedval = utf.getValueAsString(null);
	}
	/**
	 * returns the String representation of this object.  Shows the class name and the value of the Attribute.
	 */
	public String toString()
	{
		return getClass().getName() + "[Value:"+resolvedval+"]";
	}
}
