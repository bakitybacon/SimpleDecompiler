package attributes;

import constpool.ConstantPool;

/**
 * Another almost meaningless class
 * Tells that this was created by a compiler
 * @author Fleur
 *
 */
public class Synthetic implements Attribute 
{
	/**
	 * Pointless constructor
	 */
	public Synthetic()
	{
		//doesn't really take arguments
	}
	/**
	 * Returns a default constructor
	 * @param b Meaningless value
	 * @param cp Meaningless value
	 */
	public Synthetic(byte[]b,ConstantPool cp)
	{
		//just there for convenience
		this();
	}
	/**
	 * returns the String representation of this object, in this case just the class name
	 */
	public String toString()
	{
		return getClass().getName();
	}
	@Override
	/**
	 * A pointless method that only fulfills the Attribute contract
	 */
	public void parseValues(ConstantPool cp) 
	{
		//nothing to do here
		return;	
	}
}
