package attributes;

import java.nio.ByteBuffer;
import java.util.Arrays;

import constpool.ClassInfo;
import constpool.ConstantPool;

/**
 * A Class that models the Exceptions Attribute.
 * This shows the Exception classes, as opposed to where they are thrown.
 * See Code class for the thrown portion.
 * @author Fleur
 */
public class Exceptions implements Attribute
{
	/**
	 * Length of exception table
	 */
	public final short exlength;
	/**
	 * The table, which actually has the length of exlength * 2
	 * Just the byte array, as opposed to real values
	 */
	public final byte[] table;
	/**
	 * The array of Exception-deriving classes.
	 * Basically the table
	 */
	public ClassInfo[] info;
	/**
	 * The table data is parsed from.
	 */
	public final ConstantPool cp;
	
	/**
	 * A basic constructor.
	 * @param exlength the length of the following table, divided by 2
	 * @param extable the table of values, currently bytes. Needs to be parsed
	 * @param cp The ConstantPool from which data is parsed
	 */
	public Exceptions(short exlength,byte[] extable,ConstantPool cp)
	{
		this.exlength = exlength;
		table = extable;
		info = new ClassInfo[exlength];
		this.cp = cp;
		parseValues(cp);
	}
	/**
	 * A convenience method to conform to the DefaultAttribute constructor
	 * @param b the exlength and extable, mashed into one
	 * @param cp The ConstantPool from which data is parsed
	 */
	public Exceptions(byte[] b,ConstantPool cp)
	{
		this(ByteBuffer.wrap(new byte[]{b[0],b[1]}).getShort(),Arrays.copyOfRange(b, 2, b.length),cp);
	}
	@Override
	/**
	 * This method figures out what Classes are being thrown
	 * Parses this from the ConstantPool
	 */
	public void parseValues(ConstantPool cp) 
	{
		System.out.println(Arrays.toString(table));
		int classy = 0;
		for(int i = 0; i < table.length;i+=2)
		{
			ByteBuffer bb = ByteBuffer.wrap(new byte[]{table[i],table[i+1]});
			ClassInfo ci = (ClassInfo)cp.get(bb.getShort()-1);
			info[classy] = ci;
			classy++;
		}
	}
	
	/**
	 * A toString() that just returns the exception table classes.
	 */
	public String toString()
	{
		return getClass().getName()+"[Exception Table:"+Arrays.toString(info)+"]";
	}
}
