package files;

/**
 * A bunch of tags used in the ConstantPool. Used to parse the ConstantPool
 * @author Fleur
 *
 */
public interface ConstantTags 
{
	public static final short CONSTANT_Utf8 = 0x01;
	public static final short CONSTANT_Integer = 0x03;
	public static final short CONSTANT_Float = 0x04;
	public static final short CONSTANT_Long = 0x05;
	public static final short CONSTANT_Double = 0x06;
	public static final short CONSTANT_Class = 0x07;
	public static final short CONSTANT_String = 0x08;
	public static final short CONSTANT_Fieldref = 	0x09;
	public static final short CONSTANT_Methodref = 0x0A;
	public static final short CONSTANT_InterfaceMethodref = 0x0B;
	public static final short CONSTANT_NameAndType = 0x0C;
}
