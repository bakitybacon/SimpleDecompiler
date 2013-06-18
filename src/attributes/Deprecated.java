package attributes;

import constpool.ConstantPool;

/**
 * This is a relatively pointless class.
 * It models the Deprecated Attribute, which sadly doesn't really have anything to parse
 * @author Fleur
 */
public class Deprecated implements Attribute 
{
	/**
	 * Constructor function with no arguments.
	 * No point either
	 */
	public Deprecated()
	{
		//doesn't really take arguments
	}
	/**
	 * Used to conform to the DefaultAttribute method getAttribute, which requires a Constructor
	 *  to have a type (byte[],ConstantPool).
	 *  The arguments are completely irrelevant and are discarded. 
	 * @param b Meaningless value
	 * @param cp Meaningless value
	 */
	public Deprecated(byte []b,ConstantPool cp)
	{
		//convenience method. Pointless.
		this();
	}

	/**
	 * Another pointless method that merely conforms to the Attribute interface.
	 * @param cp Meaningless value
	 */
	@Override
	public void parseValues(ConstantPool cp) 
	{
		//nothing to do here
	}
	
	/**
	 * toString that just prints the class name, because nothing else exists.
	 */
	public String toString()
	{
		return getClass().getName();
	}
}
