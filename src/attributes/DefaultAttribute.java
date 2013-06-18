package attributes;

import java.lang.reflect.Constructor;
import java.util.HashMap;

import constpool.ConstantPool;
import constpool.UTF8Info;

/**
 * A convenience class that receives some bytes and delegates it
 * to the correct Attribute.
 * Uses reflection.
 * @author Fleur
 */
public class DefaultAttribute
{
	/**
	 * The index into the ConstantPool that corresponds to the name
	 */
	short nameindex;
	
	/**
	 * The name itself, which must be resolved
	 */
	String name;
	
	/**
	 * The length of the attribute
	 * Not generally very helpful
	 */
	int length;
	
	/**
	 * The byte array that the Attribute lies in
	 */
	byte[] remains;
	
	/**
	 * The ConstantPool which information is parsed from
	 */
	public final ConstantPool cp;
	
	/**
	 * The String array that corresponds to the classnames variable.
	 * Simply lists the supported values of the name variable
	 * If an attribute name is not in here, it will not be supported
	 * One part of the map hash.
	 */
	public static final String[] attrnames = new String[]
		{
			"Code","ConstantValue","Deprecated","Exceptions","InnerClasses",
			"LineNumberTable","LocalVariableTable","SourceFile","Synthetic"
		};
	
	/**
	 * The classes which correspond to the attrnames variable
	 * This is used for reflection. They all have to have the constructor (byte[],ConstantPool).
	 * One part of the map hash.
	 */
	@SuppressWarnings("rawtypes")
	public static final Class[] classnames = new Class[]
		{
			Code.class,ConstantValue.class,Deprecated.class,Exceptions.class,InnerClasses.class,
			LineNumberTable.class,LocalVariableTable.class,SourceFile.class,Synthetic.class
		};
	
	/**
	 * The Hash that binds together the attrnames and classnames variables.
	 * Used in order to make reflection possible.
	 */
	public final HashMap<String,Class<? extends Attribute>> map = new HashMap<>();
	
	/**
	 * Simple constructor.
	 * @param nindex the index into the ConstantPool of the Attribute name
	 * @param length the length of this attribute
	 * @param remainder the byte array that information will come from
	 * @param cp the ConstantPool
	 */
	public DefaultAttribute(short nindex,int length,byte[] remainder,ConstantPool cp)
	{
		name = parseName(nindex,cp);
		this.length = length;
		remains = remainder;
		nameindex = nindex;
		initHash();
		this.cp = cp;
	}
	
	/**
	 * A method that maps the Strings of attrnames and the Classes of classnames
	 * The result is put into the variable map, and not returned.
	 */
	@SuppressWarnings("unchecked")
	private void initHash()
	{
		int i = 0;
		for(String s : attrnames)
		{
			map.put(s, classnames[i]);
			i++;
		}
	}
	
	/**
	 * A method that returns the name from the nindex variable
	 * @param ind the name index
	 * @param cp the ConstantPool
	 * @return the String representation of an Attribute
	 */
	private String parseName(int ind,ConstantPool cp)
	{
		UTF8Info name = (UTF8Info)cp.get(ind-1);
		return name.getValueAsString(null);
	}
	
	/**
	 * Returns the Class from a String, which aids in reflection
	 * @param name the name to parse. If this doesn't exist, I think java.lang.Object shows up.
	 * @return the class
	 */
	public Class<? extends Attribute> getNameClass(String name)
	{
		return map.get(name);
	}
	
	/**
	 * Actual implementation of reflection involving the attrnames, classnames, and map variables.
	 * If the name doesn't exist, null will be returned.
	 * Uses a constructor with the type (byte[],ConstantPool)
	 * @return the Attribute corresponding to the name
	 */
	public Attribute getAttribute()
	{
		Class<? extends Attribute> c = getNameClass(name);
		if(c == null)
			return null;
		Constructor<? extends Attribute> con = null;
		try 
		{
			con = c.getConstructor(byte[].class,ConstantPool.class);
			Attribute steve = (Attribute)(con.newInstance(remains,cp));
			return steve;
		} 
		catch (ReflectiveOperationException | SecurityException | IllegalArgumentException e) 
		{
			e.printStackTrace();
			return null;
		}
	}
}
