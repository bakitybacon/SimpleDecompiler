package attributes;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Hashtable;

import constpool.ConstantPool;

/**
 * A class that models the LineNumberTableAttribute
 * Helpful for debugging, doesn't directly affect the semantics of the class file
 * @author Fleur
 *
 */
public class LineNumberTable implements Attribute
{
	/**
	 * The lines in byte form. Contains an opcode index and the corresponding line number.
	 */
	public final byte[] lines;
	/**
	 * The code array, for figuring out the opcodes.
	 */
	public Code code;
	/**
	 * The ConstantPool from which values will be parsed.
	 */
	public final ConstantPool cp;
	/**
	 * The oddly-named hash of opcode index and line number
	 */
	public Hashtable<Short, Short> bacon = new Hashtable<>();
	
	/**
	 * The Constructor that sets some values
	 * @param number the amount of line-opcode relations
	 * @param lines the bytes representing the lines
	 * @param cp the ConstantPool data is figured out from
	 */
	public LineNumberTable(short number,byte[] lines,ConstantPool cp)
	{
		this.lines = lines;
		this.cp = cp;
		parseValues(cp);
	}
	/**
	 * The convenience method, using only bytes and a ConstantPool
	 * @param b the number and line array, rolled into one
	 * @param cp the ConstantPool data is figured out from
	 */
	public LineNumberTable(byte[] b,ConstantPool cp)
	{
		this(ByteBuffer.wrap(new byte[]{b[0],b[1]}).getShort(),Arrays.copyOfRange(b, 2, b.length),cp);
	}
	/**
	 * A method that sets the code array opcodes.
	 * @param code
	 */
	public void setCode(Code code)
	{
		this.code = code;
	}
	@Override
	/**
	 * This just adds values to the bacon hash
	 */
	public void parseValues(ConstantPool cp) 
	{
		for(int i = 0; i < lines.length - 4;i+=4)
		{
			short startop = ByteBuffer.wrap(new byte[]{lines[i],lines[i+1]}).getShort();
			short linenum = ByteBuffer.wrap(new byte[]{lines[i+2],lines[i+3]}).getShort();
			bacon.put(startop, linenum);
		}
	}
	/**
	 * The String representation of this object, basically the class name and Line Hash
	 */
	public String toString()
	{
		return getClass().getName()+"[Line Hash:"+bacon+"]";
	}
}
