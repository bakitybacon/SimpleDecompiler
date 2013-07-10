package files;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;

public class OpCodeLength implements OpCodes
{

	@Override
	public int getLengthOf(short op,byte[] next,short pos) 
	{
		if((op >= 0x00 && op <= 0x0F) || (op >= 0x1A && op <= 0x35) || (op >= 0x3B && op <= 0x83)
				|| (op >= 0x85 && op <= 0x98) || (op >= 0xAC && op <= 0xB1) || op == 0xBE 
				|| op == 0xBF || op == 0xC2 || op == 0xc3)
					return 0;
		if(op == 0x10 || op == 0x12 || (op >= 0x15 && op <= 0x19) || (op >= 0x36 && op <= 0x3A) || op == 0xA9 || op == 0xBC)
			return 1;
		if(op == 0x13 || op == 0x11 || op == 0x14 || op == 0x84 || (op >= 0x99 && op <= 0xA8)
				||(op >= 0xB2 && op <= 0xB8) || op == 0xBB || op == 0xBD || op == 0xC0 || op == 0xC1 
				|| op == 0xC6 || op == 0xC7)
					return 2;
		if(op == 0xC5)
			return 3;
		if(op == 0xC8 || op == 0xC9 || op == 0xB9 || op == 0xBA)
			return 4;
		
		if(op == 0xC4)
		{
			if(next[0] == 0x84)
				return 5;
			else return 3;
		}
		
		if(op == 0xAA)
		{
			int padding = 4 - ((pos+1) % 4);
	
			if(padding == 4)
				padding = 0;
			
			byte[] values = Arrays.copyOfRange(next, padding + 1, 5 + padding);
			
			values = Arrays.copyOfRange(next, 5 + padding, 9 + padding);
			int low = ByteBuffer.wrap(values).getInt();
			
			values = Arrays.copyOfRange(next, 9 + padding, 13 + padding);
			int hgh = ByteBuffer.wrap(values).getInt();
			
			return 12 + padding + 4 * (hgh - low + 1);
		}
		if(op == 0xAB)
		{
			int padding = 4 - ((pos+1) % 4);
			if(padding == 4)
				padding = 0;
			byte[] values = Arrays.copyOfRange(next, 
					1 + padding,
					5 + padding);

			values = Arrays.copyOfRange(next, 5 + padding, 9 + padding);
			int number = ByteBuffer.wrap(values).getInt();
			return 8 * number + padding + 8;
		}
		//This shouldn't happen. Unless the opcode is wrong.
		return 0xDEADBEEF;
	}
	
	public static int getLength(short op,byte[] next,short pos)
	{
		return new OpCodeLength().getLengthOf(op,next,pos);
	}
	public static HashMap<Short,String> getHash()
	{
		return daftpunk;
	}
	public static HashMap<Short,String> daftpunk = new HashMap<>();
	static
	{
		for(int i = 0; i < codes.length;i++)
		{
			daftpunk.put(codes[i], names[i]);
		}
	}
	
	public static void main(String[]args)
	{
		OpCodeLength oc = new OpCodeLength();
		System.out.println(oc.getLengthOf((short)0xb2,new byte[]{},(short)0));
	}
}
