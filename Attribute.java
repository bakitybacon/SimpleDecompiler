package attributes;

import constpool.ConstantPool;

/**
 * 
 * @author Fleur
 * A interface mainly used to tie things together,
 * like arrays of Attributes and such.
 */
public interface Attribute 
{
  /**
	 * A method that messes with the results to get a meaningful answer
	 * @param cp the constant pool to use
	 */
	public void parseValues(ConstantPool cp);
}
