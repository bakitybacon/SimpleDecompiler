package constpool;

import others.InfoParser;

/**
 * A class that models the FieldRefInfo in the class.
 * Most methods are in AbstractReference.
 * @author Fleur
 * @see AbstractReference
 */
public class FieldRefInfo extends AbstractReference 
{
	/**
	 * A constructor that just calls super.
	 * @param clindex the class index into the ConstantPool
	 * @param nindex the name index into the ConstantPool
	 */
	public FieldRefInfo(short clindex, short nindex)
	{
		super(clindex,nindex);
	}
	/**
	 * A constructor that just calls super.
	 * @param b the necessary values in byte form
	 */
	public FieldRefInfo(byte[] b)
	{
		super(b);
	}
	
	public String getVariableType(ConstantPool cpool)
	{
		String desc = getValueAsString(cpool);
		desc = desc.replaceAll(".+:","");
		return InfoParser.getVariableType(desc);
	}
	
	public String getName(ConstantPool cpool)
	{
		String name = getValueAsString(cpool);
		name = name.replaceAll(":.+","");
		return name.replaceAll("/",".");
	}


	/**
	 * the tag number, an unique indentification for this type
	 */
	@Override
	public byte getTagNumber() 
	{
		return 9;
	}
}
