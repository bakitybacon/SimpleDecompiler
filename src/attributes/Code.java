package attributes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

import java.util.Arrays;

import constpool.ConstantPool;

/**
 * A representation of the Code Attribute in Java Class files.
 * @author Fleur
 * @see <a href=http://docs.oracle.com/javase/6/docs/api/java/util/AbstractCollection.html#toString%28%29>http://docs.oracle.com/javase/specs/jvms/se5.0/html/ClassFile.doc.html</a>
 */
public class Code implements Attribute 
{
	/**
	 * The highest amount of stack a method calling this will use
	 */
	public final short maxstack;
	
	/**
	 * The highest amount of variables that will be allocated at once
	 */
	public final short maxlocals;
	
	/**
	 * The array of opcodes.  Currently needs opcode support
	 */
	//TODO get some opcodes
	public final byte[] codetable;
	/**
	 * An array of exceptions that can be thrown, using the code array
	 */
	public final byte[] extable;
	
	/**
	 * An array of bytes corresponding to the Attributes this class has.  Usually LineNumberTable and LocalVariableTable.
	 */
	public final byte[] attrtable;
	
	/**
	 * The actual array of Attributes for this class.
	 */
	public Attribute[] attributes;
	
	/**
	 * The ConstantPool the values will be gotten from.
	 */
	public final ConstantPool cp;
	
	/**
	 * A simple constructor.
	 * @param maxstack Highest stack amount
	 * @param maxlocals Most local variables
	 * @param codelength length of code array
	 * @param code array of opcodes
	 * @param extablelength length of exception table
	 * @param extable exception table
	 * @param attr length of attribute table
	 * @param attrs attribute table
	 * @param cp the ConstantPool
	 */
	public Code(short maxstack,short maxlocals,int codelength,byte[] code,short extablelength,byte[] extable,short attr,byte[] attrs,ConstantPool cp)
	{
		this.maxstack = maxstack;
		this.maxlocals = maxlocals;
		codetable = code;
		this.extable = extable;
		attrtable = attrs;
		attributes = new Attribute[extablelength];
		this.cp = cp;
		parseValues(cp);
	}
	
	/**
	 * A constructor that parses all the values from bytes.
	 * @param all the entire byte array, converted to values.
	 * @param cp the ConstantPool
	 */
	public Code(byte[] all,ConstantPool cp)
	{
		ByteBuffer bb = ByteBuffer.wrap(Arrays.copyOfRange(all, 0, 2));
		maxstack = bb.getShort();
		
		bb = ByteBuffer.wrap(Arrays.copyOfRange(all, 2, 4));
		maxlocals = bb.getShort();
		
		bb = ByteBuffer.wrap(Arrays.copyOfRange(all, 4, 8));
		int length = bb.getInt();
		codetable = Arrays.copyOfRange(all, 8, 8 + length);
		
		bb = ByteBuffer.wrap(Arrays.copyOfRange(all, 8 + length, 10 + length));
		short exlength = bb.getShort();
		
		exlength *= 8; //all exceptions take up 8 bytes
		extable = Arrays.copyOfRange(all, 10 + length, 10 + length + exlength);
		
		bb = ByteBuffer.wrap(Arrays.copyOfRange(all, 10 + length + exlength,12 + length + exlength));
		short atlength = bb.getShort();
		
		attributes = new Attribute[atlength];
		
		attrtable = Arrays.copyOfRange(all, 12 + length + exlength, all.length);
		this.cp = cp;
		parseValues(cp);
	}
	/**
	 * toString method that qualifies all tables, and writes the stack and locals.
	 */
	public String toString()
	{
		return getClass().getName() + "[Max Stack: "+maxstack+", Max Locals: "+maxlocals+", Code Table: "+Arrays.toString(codetable)+
				", Exception Table: "+Arrays.toString(extable)+", Attribute Table: " + Arrays.toString(attributes)+"]";
	}
	
	/**
	 * Returns the opcodes used in the code array
	 * @return the code array
	 */
	public byte[] getCodeArray()
	{
		return codetable;
	}
	
	/**
	 * Basically the method that gets all the necessary information from the bytes.
	 */
	@Override
	public void parseValues(ConstantPool cp) 
	{
		if(attrtable.length == 0)
			return;
		int pos = 0;
		for(int i = 0; i < attributes.length;i++)
		{
			byte[] fr = Arrays.copyOfRange(attrtable,pos,pos+2);
			short ninde = ByteBuffer.wrap(fr).getShort();
			fr = Arrays.copyOfRange(attrtable,pos+2,pos+6);
			int len = ByteBuffer.wrap(fr).getInt();

			Attribute at = new DefaultAttribute(ninde,len,Arrays.copyOfRange(attrtable, pos+6, pos+6+len),cp).getAttribute();
			pos += 6 + len;
			
			try 
			{
				if(at == null)
					break;
				Method[] cake = at.getClass().getMethods();
				for(Method m : cake)
				{
					if(m.getName().equals("setCode"))
					{
						m.invoke(at, this);
					}
				}
			} 
			catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
			{
				e.printStackTrace();
			}

			attributes[i] = at;
		}
	}
}