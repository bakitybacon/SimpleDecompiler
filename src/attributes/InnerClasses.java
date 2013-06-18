package attributes;

import java.nio.ByteBuffer;
import java.util.Arrays;

import constpool.ClassInfo;
import constpool.ConstantPool;
import constpool.UTF8Info;
import files.AccessFlags;

/**
 * A class that models the InnerClasses Attribute.
 * Used in order to model the inner classes in Java.
 * Ironically contains an inner class.
 * @author Fleur
 */
public class InnerClasses implements Attribute,AccessFlags
{
	/**
	 * The byte array that will be parsed into InnerClasses
	 */
	public final byte[] classes;
	/**
	 * The ConstantPool from which the data will be parsed
	 */
	public final ConstantPool cp;

	/**
	 * A method that models individual InnerClasses.
	 * Helpful for parsing access flags, and actually parsing the information.
	 * @author Fleur
	 *
	 */
	class InnerClass
	{
		/**
		 * The index into the ConstantPool of the ClassInfo of the inner class.  This can be zero.
		 */
		short inner;
		/**
		 * The index of the ClassInfo of the outer class. Can be zero.
		 */
		short outer;
		/**
		 * The index into the ConstantPool of the name of the inner class. Can be zero, which would be an anonymous inner class.
		 */
		short inname;
		/**
		 * The bitmask used to denote permissions and other modifiers.
		 */
		short accflags;
		/**
		 * A boolean value to determine if this class is an Interface
		 */
		private boolean isInterface;
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
		 * A boolean value to determine if this class is Final (no subclasses)
		 */
		public boolean isFinal;
		/**
		 * A boolean value to determine if this class is Abstract
		 */
		public boolean isAbstract;
		
		/**
		 * A constructor that sets all the values
		 * @param inner the index of the ClassInfo of the inner class
		 * @param outer the index of the Class of the outer class
		 * @param inname the index of the name of the inner class
		 * @param accflags A bitmask denoting permissions and other modifiers
		 */
		public InnerClass(short inner,short outer,short inname, short accflags)
		{
			this.inner = inner;
			this.outer = outer;
			this.inname = inname;
			this.accflags = accflags;
			
		}
		/**
		 * A constructor that does the same thing as the other one, just with a byte array.
		 * @param b The inner and outer class info, the name and the access flags in one array.
		 */
		public InnerClass(byte[] b)
		{
			this(ByteBuffer.wrap(Arrays.copyOfRange(b, 0, 2)).getShort(),
					ByteBuffer.wrap(Arrays.copyOfRange(b, 2, 4)).getShort(),
					ByteBuffer.wrap(Arrays.copyOfRange(b, 4, 6)).getShort(),
					ByteBuffer.wrap(Arrays.copyOfRange(b, 6, 8)).getShort());
		}
		
		/**
		 * A method that simply sets the boolean values according to each access flag.
		 * All the flags are from the files.AccessFlags interface.
		 * To retrieve a String, @see parseAccessFlagsB()
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
			
			if((accflags & ACC_ABSTRACT) != 0)
				isAbstract = true;
			
			if((accflags & ACC_INTERFACE) != 0)
				isInterface = true;
		}
		
		/**
		 * Returns the String representation of the parseAccessFlags(), where the booleans are all listed.
		 * @return the representation
		 */
		public String parseAccessFlagsB()
		{
			parseAccessFlags();
			return "Public:"+isPublic+",Private:"+isPrivate+",Protected:"+isProtected+",Static:"+
					isStatic+",Final:"+isFinal+",Abstract:"+isAbstract+",Interface:"+isInterface;
		}
		
		
		/**
		 * A method to find the data using the ConstantPool.
		 * Most of these can actually be zero.
		 * @return the String representation of the class. Used in toString()
		 */
		public String parseValues()
		{
			String ininf = "";
			String oinf = "";
			String iname = "";
			if(inner == 0)
				//this is fine
				ininf = "{No data}";
			else
			{
				ClassInfo ininfo = (ClassInfo)cp.get(inner - 1);
				ininf = ininfo.getValueAsString(cp);
			}
			if(outer == 0)
				//this is fine
				oinf = "{No data}";
			else
			{
				ClassInfo oinfo = (ClassInfo)cp.get(outer - 1);
				oinf = oinfo.getValueAsString(cp);
			}
			if(inname == 0)
				//this is fine
				iname = "{Anonymous}";
			else
			{
				UTF8Info inname = (UTF8Info)cp.get(this.inname - 1);
				iname = inname.getValueAsString(null);
			}
			return "Inner Class: "
					+ininf+", Outer Class: "
					+oinf+", Name : "
					+iname+", Access Flags:{"+parseAccessFlagsB()+"}";
		}
		/**
		 * The toString method. Basically prints the class and the parseValues method.
		 * @see #parseValues()
		 */
		public String toString()
		{
			return getClass().getName() + "["+parseValues()+"]";
		}
	}
	/**
	 * The array of inner classes. This is used in the final print.
	 */
	public InnerClass[] bob;
	
	/**
	 * The Constructor that sets the values.
	 * @param number the amount of InnerClasses
	 * @param classes the byte representation of these classes
	 * @param cp the ConstantPool these values will be parsed from
	 */
	public InnerClasses(short number,byte[] classes,ConstantPool cp)
	{
		this.classes = classes;
		this.cp = cp;
		bob = new InnerClass[number];
		parseValues(cp);
	}
	/**
	 * This is a convenience method that just uses bytes.
	 * @param b the number and classes array, rolled into one
	 * @param cp the ConstantPool values will be parsed from
	 */
	public InnerClasses(byte[] b,ConstantPool cp)
	{
		this(ByteBuffer.wrap(new byte[]{b[0],b[1]}).getShort(),Arrays.copyOfRange(b, 2, b.length),cp);
	}
	@Override
	/**
	 * This methods uses a loop to create InnerClass objects and adds them to an array.
	 */
	public void parseValues(final ConstantPool cp) 
	{
		int pos = 0;
		for(int i = 0; i < bob.length;i++)
		{
			byte[] ate = Arrays.copyOfRange(classes, pos, pos+8);
			InnerClass tom = new InnerClass(ate);
			pos += 8;
			bob[i] = tom;
		}
	}
	
	/**
	 * Returns the String representation, which the class name and array of InnerClasses.
	 */
	public String toString()
	{
		return getClass().getName() +"[Inner Classes:"+Arrays.toString(bob)+"]";
	}
}


