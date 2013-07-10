package constpool;

import others.InfoParser;

/**
 * A class that models the InterfaceMethodRefInfo in the class.
 * Most methods are in AbstractReference.
 * @author Fleur
 * @see AbstractReference
 */
public class MethodRefInfo extends AbstractReference 
{
	/**
	 * A constructor that just calls super.
	 * @param clindex the class index into the ConstantPool
	 * @param nindex the name index into the ConstantPool
	 */
	public MethodRefInfo(short clindex, short nindex)
	{
		super(clindex,nindex);
	}
	/**
	 * A constructor that just calls super.
	 * @param b the necessary values in byte form
	 */
	public MethodRefInfo(byte[] b)
	{
		super(b);
	}

	/**
	 * the tag number, an unique indentification for this type
	 */
	@Override
	public byte getTagNumber() 
	{
		return 10;
	}
	
	public String getVariableType(ConstantPool cpool)
	{
		String desc = getValueAsString(cpool);
		desc = desc.replaceAll(".+:", "");
		desc = desc.replaceAll("\\([^\\)]{0,}\\)", "");
		
		//That radical regex just removed everything in parentheses. Like a boss.
		if(desc.equals("V"))
			return "void";
		return InfoParser.getMultiVariableType(desc);
	}
	public int getArgumentNumber(ConstantPool cpool)
	{
		String desc = getValueAsString(cpool);
		desc = desc.replaceAll(".+:", "");
		desc = desc.replaceAll("\\([^\\)]{0,}\\)", "");
		
		//That radical regex just removed everything in parentheses. Like a boss.
		if(desc.equals("V"))
			return 0;
		return InfoParser.getArgumentLength(desc);
	}
	public static String getVariableType(String desc)
	{
		desc = desc.replaceAll(".+:", "");
		desc = desc.replaceAll("\\([^\\)]{0,}\\)", "");
		
		//That radical regex just removed everything in parentheses. Like a boss.
		if(desc.equals("V"))
			return "void";
		return InfoParser.getMultiVariableType(desc);
	}
	public String getFullMethodName(ConstantPool cpool)
	{
		String desc = getValueAsString(cpool);
		desc = desc.replaceAll(":.+","");
		return desc.replaceAll("/",".");
	}
	
	public String getMethodName(ConstantPool cpool)
	{
		String desc = getValueAsString(cpool);
		desc = desc.replaceAll(":.+","");
		desc = desc.replaceAll(".+\\.(.+)","$1");
		return desc;
	}
	public static void main(String[]args)
	{
		System.out.println(InfoParser.getMultiVariableType("[[BCJLjava/lang/String;"));
	}
}