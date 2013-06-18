package constpool;

/**
 * An interface, used to tie together the Classes in the constpool package.
 * It has four methods, all of which supply basic information.
 * <strong>All</strong> subclasses should implement the static method public static int getLength(),
 * as this is used in the Decompiler class very heavily, and nothing will work without it.
 * @author Fleur
 *
 */
public interface ConstantType 
{
	/**
	 * Returns the type, usually just getClass().getName()
	 */
	public String getType();
	/**
	 * Returns the required length of this entry into the ConstantPool.
	 * Should always return the same as getLength() in subclasses.
	 */
	public int getRequiredLength();
	/**
	 * This will return the unique byte used to indicate what type of entry this is.
	 */
	public byte getTagNumber();
	/**
	 * This will parse values, usually deciphering bytes into data.
	 * Most subclasses will assign the result to a variable automatically.
	 * @param pool the ConstantPool data is gotten from
	 */
	public String getValueAsString(ConstantPool pool);
}
