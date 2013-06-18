package files;

/**
 * A interface made to provide Access Flags without messing with inheritance.
 * Show every bitmask there is.
 * Values are self-explanatory, I think.
 * To test the access flags, use the bitwise and function with the flags.
 * If the result is not zero, the class has that property.
 * @author Fleur
 */
public interface AccessFlags 
{
	//kind of a mix-in with access flags.
	//Super and synchronized are the same value.
	public static final short ACC_PUBLIC = 0x0001;
	public static final short ACC_PRIVATE = 0x0002;
	public static final short ACC_PROTECTED = 0x0004;
	public static final short ACC_STATIC = 0x0008;
	public static final short ACC_FINAL = 0x0010;
	public static final short ACC_SUPER = 0x0020;
	public static final short ACC_SYNCHRONIZED = 0x0020;
	public static final short ACC_VOLATILE = 0x0040;
	public static final short ACC_TRANSIENT = 0x0080;
	public static final short ACC_NATIVE = 0x0100;
	public static final short ACC_INTERFACE = 0x0200;
	public static final short ACC_ABSTRACT = 0x0400;
	public static final short ACC_STRICT = 0x0800;
}
