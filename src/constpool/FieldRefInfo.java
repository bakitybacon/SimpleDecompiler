package constpool;

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


	/**
	 * the tag number, an unique indentification for this type
	 */
	@Override
	public byte getTagNumber() 
	{
		return 9;
	}
}
