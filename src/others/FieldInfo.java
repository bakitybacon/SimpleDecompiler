package others;

import java.nio.ByteBuffer;
import java.util.Arrays;

import constpool.ConstantPool;
import constpool.UTF8Info;

import attributes.Attribute;
import attributes.DefaultAttribute;
import attributes.Synthetic;
import files.AccessFlags;

/**
 * A class that models Fields of Java Classes.
 * Very similar to the Method class.
 * @author Fleur
 *
 */
public class FieldInfo implements AccessFlags
{
	/**
	 * A bitmask used to figure out permissions and other properties
	 */
	public final short accflags;
	/**
	 * A index into the ConstantPool that corresponds to the name
	 */
	public final short nindex;
	/**
	 * A index into the ConstantPool that corresponds to the description
	 */
	public final short dindex;
	/**
	 * A byte representation of the attribute table that needs some parsing
	 */
	public final byte[] attrtable;
	/**
	 * This is where the values in attrtable will end up
	 * Stores all the attributes of this class
	 */
	public Attribute[] attribs;
	/**
	 * The ConstantPool everything will get information from
	 */
	public final ConstantPool cpool;
	/**
	 * The name of this Field
	 */
	public String name;
	
	/**
	 * A boolean value to determine if this class is Public
	 */
	public boolean isPublic;
	/**
	 * A boolean value to determine if this class is Private
	 */
	public boolean isPrivate;
	/**
	 * A boolean value to determine if this class is Protected
	 */
	public boolean isProtected;
	/**
	 * A boolean value to determine if this class is Static
	 */
	public boolean isStatic;
	/**
	 * A boolean value to determine if this class is Final
	 */
	public boolean isFinal;
	/**
	 * A boolean value to determine if this class is Volatile
	 */
	public boolean isVolatile;
	/**
	 * A boolean value to determine if this class is Transient
	 */
	public boolean isTransient;
	/**
	 * A boolean value to determine if this class is Synthetic
	 */
	public boolean isSynthetic;
	/**
	 * A boolean value to determine if this class is Deprecated
	 */
	public boolean isDeprecated;
	/**
	 * A boolean value to determine if this class is Constant
	 */
	public boolean isConstant;
	
	/**
	 * A simple constructor
	 * @param access the access flags
	 * @param nameindex the ConstantPool index for the name
	 * @param descindex the ConstantPool index for this class
	 * @param attrcount the number of attributes
	 * @param attrs the byte array of attributes
	 * @param cp the ConstantPool itself
	 */
	public FieldInfo(short access,short nameindex,short descindex,short attrcount,byte[] attrs,ConstantPool cp)
	{
		accflags = access;
		nindex = nameindex;
		dindex = descindex;
		attrtable = attrs;
		cpool = cp;
		attribs = new Attribute[attrcount];
	}
	
	/**
	 * A simple constructor
	 * @param access the access flags
	 * @param nameindex the ConstantPool index for the name
	 * @param descindex the ConstantPool index for this class
	 * @param attrcount the number of attributes
	 * @param attrs the actual attributes, not the byte array
	 * @param cp the ConstantPool itself
	 */
	public FieldInfo(short access,short nameindex,short descindex,short attrcount,Attribute[] attrs,ConstantPool cp)
	{
		accflags = access;
		nindex = nameindex;
		dindex = descindex;
		attrtable = new byte[]{};
		attribs = attrs;
		cpool = cp;
	}
	
	/**
	 * Another constructor, calls the first constructor
	 * @param values the bytes from which the values are parsed
	 * @param cp the ConstantPool itself
	 */
	public FieldInfo(byte[] values,ConstantPool cp)
	{
		byte[] reuser = {values[0],values[1]};
		ByteBuffer reused = ByteBuffer.wrap(reuser);
		accflags = reused.getShort();
		
		reuser[0] = values[2];
		reuser[1] = values[3];
		reused = ByteBuffer.wrap(reuser);
		nindex = reused.getShort();
		
		reuser[0] = values[4];
		reuser[1] = values[5];
		reused = ByteBuffer.wrap(reuser);
		dindex = reused.getShort();
		
		reuser[0] = values[6];
		reuser[1] = values[7];
		reused = ByteBuffer.wrap(reuser);
		short attrlength = reused.getShort();
		
		attrtable = Arrays.copyOfRange(values, 8, values.length);
		cpool = cp;
		attribs = new Attribute[attrlength];
	}
	/**
	 * A method that takes the access flags and sets the appropriate boolean condition
	 */
	public void parseAccessFlags()
	{
		if((accflags & ACC_PUBLIC) != 0)
			isPublic = true;
		else if((accflags & ACC_PRIVATE) != 0)
			isPrivate = true;
		else if((accflags & ACC_PROTECTED) != 0)
			isProtected = true;
		
		if((accflags & ACC_STATIC) != 0)
			isStatic = true;

		if((accflags & ACC_FINAL) != 0)
			isFinal = true;
		else if((accflags & ACC_VOLATILE) != 0)
			isVolatile = true;
		
		if((accflags & ACC_TRANSIENT) != 0)
			isTransient = true;
		
		if(attribs != null)
		{
			for(Attribute a : attribs)
			{
				if(a instanceof attributes.Deprecated)
					isDeprecated = true;
				if(a instanceof Synthetic)
					isSynthetic = true;
				if(a instanceof attributes.ConstantValue)
					isConstant = true;
			}
		}
	}
	/**
	 * A method that returns the String representation of the access flags
	 * @return the String version of the access flags
	 */
	public String parseAccessFlagsB()
	{
		parseAccessFlags();
		return "Public:"+isPublic+",Private:"+isPrivate+",Protected:"+isProtected+",Static:"
				+isStatic+",Final:"+isFinal+",Volatile:"+isVolatile+",Transient:"+isTransient
				+",Synthetic:"+isSynthetic+",Deprecated:"+isDeprecated+",Constant:"+isConstant;
	}
	
	/**
	 * Returns the name from the ConstantPool
	 * @return the name of the field
	 */
	public String getName()
	{
		return ((UTF8Info)cpool.get(nindex-1)).getValueAsString(null);
	}
	
	/**
	 * Returns the description from the ConstantPool
	 * @return the description of the field
	 */
	public String getDescriptor()
	{
		return ((UTF8Info)cpool.get(dindex-1)).getValueAsString(null);
	}
	
	/**
	 * A method that takes some binary values from attrtable and makes into Attributes.
	 * Uses the DefaultAttribute class.
	 */
	public void parseAttributes()
	{
		if(attrtable.length == 0)
			return;
		int j = 0;
		int pos = 0;
		while(j < attribs.length)
		{
			byte[] take6 = Arrays.copyOfRange(attrtable, pos, pos+6);
			short nind = ByteBuffer.wrap(Arrays.copyOfRange(take6, 0, 2)).getShort();
			int len = ByteBuffer.wrap(Arrays.copyOfRange(take6, 2, 6)).getInt();
			byte[] remainder = Arrays.copyOfRange(attrtable,pos+6,pos+6+len);
			DefaultAttribute da = new DefaultAttribute(nind,len,remainder,cpool);
			attribs[j] = da.getAttribute();
			pos = pos+6+len;
			j++;
		}
	}
	
	/**
	 * The toString method that returns the class name, access flags, name, descriptor, and attributes
	 */
	public String toString()
	{
		return getClass().getName()+"[Access Flags:{"+parseAccessFlagsB()+
				"},Name:"+getName()+",Descriptor:"+getDescriptor()+",Attributes:"+Arrays.toString(attribs)+"]";
	}
}
