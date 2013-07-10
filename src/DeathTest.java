import java.nio.ByteBuffer;

public class DeathTest 
{
	public static int toStuff(byte a, byte b)
	{
		return (short) ((a << 8) + b);
	}
	public static short byters(byte a, byte b)
	{
		return ByteBuffer.wrap(new byte[]{a,b}).getShort();
	}
	
	public static void main(String[]args)
	{
		System.out.println("getstatic(".matches("^getstatic.+$"));
		byte a = 5;
		byte b = 12;
		
		System.out.println(toStuff(a,b));
		System.out.println(byters(a,b));
	}
}
