package others;

import java.nio.ByteBuffer;
import java.util.Arrays;

import attributes.Attribute;
import attributes.DefaultAttribute;
import attributes.Synthetic;
import constpool.ConstantPool;
import constpool.UTF8Info;
import files.AccessFlags;

public class MethodInfo implements AccessFlags
{
	public final short accflags;
	public final short nindex;
	public final short dindex;
	public final byte[] attrtable;
	public final ConstantPool cpool;
	public Attribute[] attrs;
	public String name;
	
	public boolean isPublic;
	public boolean isPrivate;
	public boolean isProtected;
	public boolean isStatic;
	public boolean isFinal;
	public boolean isSynchronized;
	public boolean isNative;
	public boolean isAbstract;
	public boolean isStrict;
	public boolean isSynthetic;
	public boolean isDeprecated;
	
	/**
	 * A simple constructor
	 * @param access the access flags
	 * @param nameindex the index into the ConstantPool of the name
	 * @param descindex the index into the ConstantPool of the description
	 * @param attrcount the number of attributes
	 * @param attrs the bytes representing the attributes
	 * @param cp the ConstantPool the values will come from
	 */
	public MethodInfo(short access,short nameindex,short descindex,short attrcount,byte[] attrs,ConstantPool cp)
	{
		accflags = access;
		nindex = nameindex;
		dindex = descindex;
		attrtable = attrs;
		cpool = cp;
	}
	
	/**
	 * A simple constructor
	 * @param access the access flags
	 * @param nameindex the index into the ConstantPool of the name
	 * @param descindex the index into the ConstantPool of the description
	 * @param attrcount the number of attributes
	 * @param attrs the actual Attributes
	 * @param cp the ConstantPool the values will come from
	 */
	public MethodInfo(short access,short nameindex,short descindex,short attrcount,Attribute[] attrs,ConstantPool cp)
	{
		accflags = access;
		nindex = nameindex;
		dindex = descindex;
		attrtable = new byte[]{};
		this.attrs = attrs;
		cpool = cp;
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
		
		if((accflags & ACC_SYNCHRONIZED) != 0)
			isSynchronized = true;
		
		if((accflags & ACC_NATIVE) != 0)
			isNative = true;
		
		if((accflags & ACC_ABSTRACT) != 0)
			isAbstract = true;
		
		if((accflags & ACC_STRICT) != 0)
			isStrict = true;
		
		if(attrs != null)
		{
			for(Attribute a : attrs)
			{
				if(a instanceof attributes.Deprecated)
					isDeprecated = true;
				if(a instanceof Synthetic)
					isSynthetic = true;
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
		return "Public:"+isPublic+",Private:"+isPrivate+",Protected:"+isProtected+",Static:"+
				isStatic+",Final:"+isFinal+",Synchronized:"+isSynchronized+
						",Native:"+isNative+",Abstract:"+isAbstract+",StrictFP:"+isStrict+
						",Synthetic:"+isSynthetic+",Deprecated:"+isDeprecated;
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
		while(j < attrs.length)
		{
			byte[] take6 = Arrays.copyOfRange(attrtable, pos, pos+6);
			short nind = ByteBuffer.wrap(Arrays.copyOfRange(take6, 0, 2)).getShort();
			int len = ByteBuffer.wrap(Arrays.copyOfRange(take6, 2, 6)).getInt();
			byte[] remainder = Arrays.copyOfRange(attrtable,pos+6,pos+6+len);
			DefaultAttribute da = new DefaultAttribute(nind,len,remainder,cpool);
			attrs[j] = da.getAttribute();
			pos = pos+6+len;
			j++;
		}
	}
	
	public String getReturnType()
	{
		String desc = getDescriptor();
		desc = desc.replaceAll("\\([^\\)]{0,}\\)", "");
		//That radical regex just removed everything in parentheses. Like a boss.
		if(desc.equals("V"))
			return "void";
		return InfoParser.getVariableType(desc);
	}
	
	public String getParameters()
	{
		String desc = getDescriptor();
		desc = desc.replaceAll("\\(([^\\)]{0,})\\).+", "$1");
		//That radical regex just replaced the string with whatever was in parentheses. Like a boss.
		if(desc.equals(""))
			return desc;
		
		String fulldesc = "";
		String toadd = "";
		
		int pos = 0;
		
		
		for(;pos < desc.toCharArray().length;pos++)
		{
			char c = desc.toCharArray()[pos];
			if(c == '[')
			{
				toadd += "[]";
				continue;
			}
			if(c != 'L')
			{
				fulldesc += InfoParser.getVariableType(c + "");
				fulldesc += toadd;
				toadd = "";
				fulldesc += ", ";
				continue;
			}
			String charly = "";
			pos++;
			//this char is now L, indicating a class
			for(; pos < desc.toCharArray().length;pos++)
			{
				if(desc.toCharArray()[pos] == ';')
					break;
				charly += desc.toCharArray()[pos]; 
			}
			
			fulldesc += charly + toadd;
			fulldesc += ", ";
			toadd = "";
			pos++;
		}
		
		fulldesc = fulldesc.trim();
		if(fulldesc.endsWith(","))
			fulldesc = fulldesc.substring(0, fulldesc.length() - 1);
		return fulldesc.replaceAll("/", ".");
	}
	
	public String getAccess()
	{
		String stuff = "";
		parseAccessFlags();
		if(isPublic)
			stuff += " public ";
		else if(isPrivate)
			stuff += " private ";
		else if(isProtected)
			stuff += " protected ";
		if(isStatic)
			stuff += " static ";
		if(isFinal)
			stuff += " final ";
		if(isSynchronized)
			stuff += " synchronized ";
		if(isNative)
			stuff += " native ";
		if(isAbstract)
			stuff += " abstract ";
		
		stuff = stuff.replaceAll("\\s+", " ");
			
		return stuff.trim();
			
	}
	
	/**
	 * The toString method that returns the class name, access flags, name, descriptor, and attributes
	 */
	public String toString()
	{
		return getClass().getName()+"[Access Flags:{"+parseAccessFlagsB()+
				"},Name:"+getName()+",Descriptor:"+getDescriptor()+",Attributes:"+Arrays.toString(attrs)+"]";
	}
}
