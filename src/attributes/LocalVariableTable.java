package attributes;

import java.nio.ByteBuffer;
import java.util.Arrays;

import others.InfoParser;

import constpool.ConstantPool;
import constpool.UTF8Info;

/**
 * Debugging information from the Code Attribute
 * Generally helpful for analyzing variables in the middle of execution
 * @author Fleur
 *
 */
public class LocalVariableTable implements Attribute
{
	public LocalVariableTable(){vars=null;variables=null;cp=null;}
	/**
	 * The opcodes
	 */
	public Code code;
	/**
	 * The byte represenation the variables
	 */
	public final byte[] vars;
	/**
	 * The ConstantPool from which data shall be parsed
	 */
	public final ConstantPool cp;
	/**
	 * The inner class all these things map to
	 */
	public final LocalVariable[] variables;
	
	/**
	 * A class that just models individual variables.
	 * More of a convenience class
	 * @author Fleur
	 */
	public class LocalVariable
	{
		/**
		 * The index into the ConstantPool of the name
		 */
		short nind;
		/**
		 * The index into the ConstantPool of the description
		 */
		short dind;
		/**
		 * I'm unsure what this is for, but it seems to be an index into the Local Variables.
		 */
		short ind;
		/**
		 * The position in the Code array where this variable is defined
		 */
		short start;
		/**
		 * The opcodes this is defined for
		 */
		byte[] codearr;
		/**
		 * The length of opcodes this variable is in scope
		 */
		short length;
		/**
		 * Simple constructor
		 * @param start the index into the code array where the variable is defined
		 * @param length the length this variable exists
		 * @param nind the name index 
		 * @param dind the description index
		 * @param ind some cool value
		 */
		public LocalVariable(short start, short length,short nind, short dind,short ind)
		{
			this.start = start;
			this.length = length;
			this.nind = nind;
			this.dind = dind;
			this.ind = ind;
		}
		/**
		 * A convenience method that calls the other constructor
		 * @param b the other values, mashed up into one array
		 */
		public LocalVariable(byte[] b)
		{
			this(ByteBuffer.wrap(Arrays.copyOfRange(b, 0, 2)).getShort(),
					ByteBuffer.wrap(Arrays.copyOfRange(b, 2, 4)).getShort(),
					ByteBuffer.wrap(Arrays.copyOfRange(b, 4, 6)).getShort(),
					ByteBuffer.wrap(Arrays.copyOfRange(b, 6, 8)).getShort(),
					ByteBuffer.wrap(Arrays.copyOfRange(b, 8, 10)).getShort());
		}
		/**
		 * Figures out the values given in the constructor
		 * Main component of toString
		 * @return the unique values to this class
		 */
		public String parseValues()
		{
			String codea = "Undefined";
			if(code != null)
			{
				codearr = Arrays.copyOfRange(code.getCodeArray(), start, start+length);
				codea = Arrays.toString(codearr);
			}
			UTF8Info ninfo = (UTF8Info)cp.get(nind-1);
			UTF8Info dinfo = (UTF8Info)cp.get(dind-1);
			return "Code Array: "+codea+", Name: "
					+ninfo.getValueAsString(null)+", Description: "
					+dinfo.getValueAsString(null)+", Index: "+ind;
		}
		/**
		 * The String representation of this object, returning the class and the result of parseValues()
		 * @see #parseValues()
		 */
		public String toString()
		{
			return getClass().getName() + "[" + parseValues() + "]";
		}
		public String getLocalVariableName()
		{
			return ((UTF8Info)cp.get(nind-1)).getValueAsString(null);
		}
		public String getVariableType() 
		{
			return InfoParser.getVariableType(((UTF8Info)cp.get(dind-1)).getValueAsString(null));
		}
	}
	
	/**
	 * The constructor
	 * @param number the number of local variables
	 * @param vars the bytes modeling the variables
	 * @param cp the ConstantPool data will be taken from
	 */
	public LocalVariableTable(short number,byte[] vars,ConstantPool cp)
	{
		this.vars = vars;
		this.cp = cp;
		variables = new LocalVariable[number];
		parseValues(cp);
	}
	/**
	 * Convenience method that calls the other constructor
	 * @param b the number and bytes of local variables
	 * @param cp the ConstantPool data will be taken from
	 */
	public LocalVariableTable(byte[] b,ConstantPool cp)
	{
		this(ByteBuffer.wrap(new byte[]{b[0],b[1]}).getShort(),Arrays.copyOfRange(b, 2, b.length),cp);
	}
	
	/**
	 * This defines the Code so opcodes can be figured out
	 * @param code the code that will be used
	 */
	public void setCode(Code code)
	{
		this.code = code;
	}
	
	/**
	 * This figures out the values by making LocalVariable objects
	 */
	public void parseValues(final ConstantPool cp)
	{
		int pos = 0;
		for(int i = 0; i < variables.length;i++)
		{
			byte[] ten = Arrays.copyOfRange(vars, pos, pos+10);
			LocalVariable tom = new LocalVariable(ten);
			variables[i] = tom;
			pos += 10;
		}
	}
	
	public LocalVariable[] getVariables()
	{
		return variables;
	}
	
	/**
	 * Returns the String representation. Here, it is the class name and the array of LocalVariable objects.
	 */
	public String toString()
	{
		return getClass().getName()+"[Local Variables:"+Arrays.toString(variables)+"]";
	}
}
