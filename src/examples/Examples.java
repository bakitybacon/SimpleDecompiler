package examples;
import java.lang.reflect.Constructor;

import constpool.*;
public class Examples 
{
	public static void main(String[]args) throws Exception
	{
		ConstantType f = new UTF8Info((short)3, new byte[]{50,51,53});
		UTF8Info s = (UTF8Info)f;
		System.out.println(s);
		
		Class<Examples> carass = Examples.class;
		Examples as = (Examples)carass.newInstance();
		as.mange();
		
		Examples asd;
		Constructor<? extends Examples> steve = as.getClass().getDeclaredConstructor(int.class);
		
		asd = (Examples)steve.newInstance(new Object[]{3});
		asd.mange();
	}
	public void mange()
	{
		System.out.println("J'AI MANGER DU PETIT CHAT LELELE");
	}
	public Examples(){}
	public Examples(int x){}
}
