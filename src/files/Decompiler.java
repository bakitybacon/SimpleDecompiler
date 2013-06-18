package files;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.*;

import attributes.Attribute;
import attributes.Code;
import attributes.ConstantValue;
import attributes.DefaultAttribute;

import others.FieldInfo;
import others.MethodInfo;

import constpool.*;

/**
 * This class ties all the Attributes, Constants, Methods, and Fields together
 * Doesn't currently decompile into source code
 * Uses reflection
 * @author Fleur
 *
 */
public class Decompiler implements AccessFlags,ConstantTags
{
	/**
	 * An array of the bitmasks found in the ConstantTags class
	 */
	public static final short[] constantvals = new short[]{CONSTANT_Utf8,CONSTANT_Integer,CONSTANT_Float,
		CONSTANT_Long,CONSTANT_Double,CONSTANT_Class,CONSTANT_String,CONSTANT_Fieldref
		,CONSTANT_Methodref,CONSTANT_InterfaceMethodref,CONSTANT_NameAndType};
	
	public static final float version = 1.0f;
	
	/**
	 * A boolean value to determine if this class is Public
	 */
	static boolean isPublic;
	/**
	 * A boolean value to determine if this class is Final
	 */
	static boolean isFinal;
	/**
	 * A boolean value to determine if this class is Super
	 */
	static boolean isSuper;
	/**
	 * A boolean value to determine if this class is an Interface
	 */
	static boolean isInterface;
	/**
	 * A boolean value to determine if this class is Abstract
	 */
	static boolean isAbstract;
	
	/**
	 * The ConstantPool everything uses
	 */
	public static ConstantPool cpool;
	
	/**
	 * The array of fields, for later use
	 */
	static FieldInfo[] jolly;
	
	/**
	 * The array of methods, for later use
	 */
	static MethodInfo[] meths;
	
	/**
	 * The array of Attribute, for (maybe) later use
	 */
	static Attribute[] attrs;
	
	/**
	 * A string with the name of this class
	 */
	static String thclass;
	
	/**
	 * A string with the name of the super class
	 */
	static String suclass;
	
	static ArrayList<String> interfacearr = new ArrayList<>();
	
	/**
	 * A array of classes for some epic reflection
	 */
	@SuppressWarnings("rawtypes")
	public static final Class[] ad = new Class[]
	{
		UTF8Info.class, IntegerInfo.class,FloatInfo.class,LongInfo.class,DoubleInfo.class,ClassInfo.class,StringInfo.class,
		FieldRefInfo.class,MethodRefInfo.class,InterfaceMethodRefInfo.class,NameAndTypeInfo.class
	};
	
	/**
	 * The main method. Just calls readFile()
	 * @param args command line arguments
	 */
	public static void main(String[] args) throws Exception
	{
		//FIXME another one
		if(args.length == 0)
		{
			System.out.println("usage: Decompiler [file] [options]");
			decompile(new File("res/EmeraldRandomizerApp.class"));
			System.exit(0);
		}
		String arg1 = args[0];
		try
		{
			if(args.length == 1)
				decompile(new File(System.getProperty("user.dir")+"/"+arg1));
			System.exit(0);
		}
		catch(IOException e)
		{
			System.out.println("File "+System.getProperty("user.dir")+"/"+arg1+" not found.");
		}
		for(int i = 1; i < args.length;i++)
		{
			if(!args[i].startsWith("-"))
				continue;
			String op = args[i].substring(1);
			if(op.equals("v") || op.equals("-version"))
			{
				System.out.println("Java Decompiler Version "+version);
				System.exit(0);
			}
			else if(op.equals("h") || op.equals("-help"))
			{
				System.out.println("usage: Decompiler [file] [options]");
				System.exit(0);
			}
			else
				System.out.println("Unrecognized Option: -"+op);
		}
		try
		{
			decompile(new File(System.getProperty("user.dir")+"/"+arg1));
		}
		catch(IOException e)
		{
			System.out.println("File "+System.getProperty("user.dir")+"/"+arg1+" not found.");
		}
	}
	
	
	/**
	 * The method that does all the actual reading.
	 * Uses some awesome reflection.
	 * @param f the file to decompile
	 */
	@SuppressWarnings("unchecked")
	public static void readFile(File f) throws Exception
	{
		final HashMap<Short,Class<ConstantType>> classvals = new HashMap<>();
		Short sas = 1;
		for(short s : constantvals)
		{
			if(s == 1)
			{
				classvals.put(sas, ad[0]);
				continue;
			}
			classvals.put(s, ad[s-2]);
		}
		
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
		byte[] steve = new byte[4];
		bis.read(steve);
		String jake = "0x" +bytesToHex(steve);
		if(!jake.equals("0xCAFEBABE"))
		{
			System.out.println("Likely not a Java class file. Aborting...");
			bis.close();
			return;
		}
		else
			System.out.println("This is a Java class file.");

		
		byte[] minor = new byte[2];
		bis.read(minor);
		byte[] major = new byte[2];
		bis.read(major);
		
		System.out.println("The version of the file format is "+major[0]+major[1]+"."+minor[0]+minor[1]);
		
		byte[] constant_pool_length = new byte[2];
		bis.read(constant_pool_length);
		int constpoollength = Integer.parseInt(
				bytesToHex(constant_pool_length),16);
		System.out.println("Constant Pool Length: "+constpoollength);
		
		//length is one less because of internal array being used.
		ConstantPool pool = new ConstantPool(constpoollength - 1);
		int amount = 1;
		while(amount < constpoollength)
		{
			short a = (short)bis.read();
			for(short sh : constantvals)
			{
				if(a == sh)
				{
					Class<?> current = classvals.get(a);
					if(current.equals(UTF8Info.class))
					{
						byte[] asdf = new byte[2];
						bis.read(asdf);
						ByteBuffer bb = ByteBuffer.wrap(asdf);
						short first = bb.getShort();
						byte[] sadf = new byte[first];
						bis.read(sadf);
						pool.add(new UTF8Info(first,sadf));
					}
					else
					{
						Method m = current.getMethod("getLength",(Class<?>[])null);
						int x = (Integer)
								m.invoke(current, 
									new Object[]{});
						
						byte[] bogus = new byte[x];
						bis.read(bogus);
						
						Constructor<?> cs = current.getConstructor(byte[].class);
						ConstantType epic = (ConstantType)cs.newInstance(bogus);
						pool.add(epic);
						if(current.equals(DoubleInfo.class) | current.equals(LongInfo.class))
						{
							//this is because double and long both use 2 indices.
							pool.add(new UTF8Info((short) 4,new byte[]{0x6e,0x75,0x6c,0x6c}));
							amount++;
						}
					}
					amount++;
				}
			}
			cpool = pool;
		}
		for(ConstantType cs : cpool)
		{
			cs.getValueAsString(cpool);
		}
		System.out.println(cpool);
		
		byte[] accessflags = new byte[2];
		bis.read(accessflags);

		ByteBuffer le = ByteBuffer.wrap(accessflags);
		short acc = le.getShort();
		
		if((acc & ACC_PUBLIC) != 0)
			isPublic = true;
		
		if((acc & ACC_FINAL) != 0)
			isFinal = true;
		
		if((acc & ACC_SUPER) != 0)
			isSuper = true;
		
		if((acc & ACC_INTERFACE) != 0)
			isInterface = true;
		
		if((acc & ACC_ABSTRACT) != 0)
			isAbstract = true;
		
		System.out.print("Access flags: ");
		
		String accstring = "";
		
		if(isPublic)
			accstring += "public,";
		
		if(isFinal)
			accstring += "final,";

		if(isSuper)
			accstring += "super,";

		if(isAbstract)
			accstring += "abstract,";
		
		if(isInterface)
			accstring += "interface,";
		System.out.println(accstring.substring(0,accstring.length()-1));
		
		byte[] array = new byte[2];
		bis.read(array);
		ByteBuffer thiscl = ByteBuffer.wrap(array);
		short tscl = thiscl.getShort();
		System.out.print("This class is: ");
		System.out.println(cpool.get(tscl-1).getValueAsString(cpool).replaceAll("/", "."));
		thclass = cpool.get(tscl-1).getValueAsString(cpool).replaceAll("/", ".");
		
		bis.read(array);
		ByteBuffer supercl = ByteBuffer.wrap(array);
		short scl = supercl.getShort();
		System.out.print("Super class is: ");
		if(scl == 0)
			suclass = "";
		else
		{
			System.out.println(cpool.get(scl-1).getValueAsString(cpool).replaceAll("/", "."));
			suclass = cpool.get(scl-1).getValueAsString(cpool).replaceAll("/", ".");
		}
		bis.read(array);
		ByteBuffer interfaces = ByteBuffer.wrap(array);
		short inter = interfaces.getShort();
		System.out.println("There are "+inter+" interface(s)");
		
		byte[] interarray = new byte[inter*2];
		for(int i = 0; i < interarray.length - 1;i+=2)
		{
			byte[] terfes = {interarray[i],interarray[1+i]};
			bis.read(terfes);
			ByteBuffer leint = ByteBuffer.wrap(terfes);
			short charles = leint.getShort();
			
			System.out.println("Interface: "+cpool.get(charles - 1).getValueAsString(cpool));
			interfacearr.add(cpool.get(charles - 1).getValueAsString(cpool));
			//TODO a marker
		}
		
		bis.read(array);
		ByteBuffer fieldsc = ByteBuffer.wrap(array);
		short fieldscount = fieldsc.getShort();
		System.out.println("There are "+fieldscount+" field(s).");
		jolly = new FieldInfo[fieldscount];
		
		for(int i = 0; i < fieldscount;)
		{
			
			byte[] firsttake = new byte[8];
			bis.read(firsttake);
			ByteBuffer leint = ByteBuffer.wrap(Arrays.copyOfRange(firsttake, 0, 2));
			
			short accflags = leint.getShort();
			
			leint = ByteBuffer.wrap(Arrays.copyOfRange(firsttake, 2, 4));
			
			short nindex = leint.getShort();
			
			leint = ByteBuffer.wrap(Arrays.copyOfRange(firsttake, 4, 6));
			
			short dindex = leint.getShort();
			
			leint = ByteBuffer.wrap(Arrays.copyOfRange(firsttake, 6, 8));
			
			short attrs = leint.getShort();
			
			Attribute[] att = new Attribute[attrs];
			
			for(int j = 0; j < attrs;)
			{
				byte[] take6 = new byte[6];
				bis.read(take6);
				short nind = ByteBuffer.wrap(Arrays.copyOfRange(take6, 0, 2)).getShort();
				int len = ByteBuffer.wrap(Arrays.copyOfRange(take6, 2, 6)).getInt();
				byte[] remainder = new byte[len];
				bis.read(remainder);
				DefaultAttribute da = new DefaultAttribute(nind,len,remainder,cpool);
				att[j] = da.getAttribute();
				j++;
			}
			
			FieldInfo fi = new FieldInfo(accflags, nindex, dindex, attrs, att,cpool);
			jolly[i] = fi;
			i++;
		}
		System.out.println(Arrays.toString(jolly));
		
		byte[] methods = new byte[2];
		bis.read(methods);
		short methcount = ByteBuffer.wrap(methods).getShort();
		System.out.println("There are "+methcount+" method(s)");
		
		meths = new MethodInfo[methcount];
		
		for(int i = 0; i < methcount;)
		{
			
			byte[] firsttake = new byte[8];
			bis.read(firsttake);
			ByteBuffer leint = ByteBuffer.wrap(Arrays.copyOfRange(firsttake, 0, 2));
			
			short accflags = leint.getShort();
			
			leint = ByteBuffer.wrap(Arrays.copyOfRange(firsttake, 2, 4));
			
			short nindex = leint.getShort();
			
			leint = ByteBuffer.wrap(Arrays.copyOfRange(firsttake, 4, 6));
			
			short dindex = leint.getShort();
			
			leint = ByteBuffer.wrap(Arrays.copyOfRange(firsttake, 6, 8));
			
			short attrs = leint.getShort();
			
			Attribute[] att = new Attribute[attrs];
			
			for(int j = 0; j < attrs;)
			{
				byte[] take6 = new byte[6];
				bis.read(take6);
				short nind = ByteBuffer.wrap(Arrays.copyOfRange(take6, 0, 2)).getShort();
				int len = ByteBuffer.wrap(Arrays.copyOfRange(take6, 2, 6)).getInt();
				byte[] remainder = new byte[len];
				bis.read(remainder);
				DefaultAttribute da = new DefaultAttribute(nind,len,remainder,cpool);
				att[j] = da.getAttribute();
				j++;
			}
			
			MethodInfo fi = new MethodInfo(accflags, nindex, dindex, attrs, att,cpool);
			meths[i] = fi;
			i++;
		}
		System.out.println(Arrays.toString(meths));
		
		byte[] attribs = new byte[2];
		bis.read(attribs);
		short attrlen = ByteBuffer.wrap(attribs).getShort();
		System.out.println("There are "+attrlen+" attribute(s).");
		attrs = new Attribute[attrlen];
		for(int j = 0; j < attrs.length;)
		{
			byte[] take6 = new byte[6];
			bis.read(take6);
			short nind = ByteBuffer.wrap(Arrays.copyOfRange(take6, 0, 2)).getShort();
			int len = ByteBuffer.wrap(Arrays.copyOfRange(take6, 2, 6)).getInt();
			byte[] remainder = new byte[len];
			bis.read(remainder);
			DefaultAttribute da = new DefaultAttribute(nind,len,remainder,cpool);
			attrs[j] = da.getAttribute();
			j++;
		}
		System.out.println(Arrays.toString(attrs));
		
		bis.close();
	}
	
	
	public static void decompile(File m) throws Exception
	{
		//XXX another marker
		readFile(m);
		
		for(int i = 0 ; i < 5 ; i++)
			System.out.println();
		
		String fullstuff = "";
		
		String pubstring = isPublic ? "public" : "";
		String clastring = isInterface ? "interface" : "class";
		String pack = thclass.replaceAll("([^\\.]+)\\..+","$1");
		thclass = thclass.replaceAll("[^\\.]+\\.(.+)","$1");
		String interfacestring = "";
		if(pack.length() != 0)
			fullstuff += "package "+pack+";\n";
		if(interfacearr.size() != 0)
		{
			interfacestring += "implements ";
			
			for(int i = 0; i < interfacearr.size();i++)
			{
				interfacestring += interfacearr.get(i);
				if(i+1 < interfacearr.size())
					interfacestring += ",";
			}
		}
		
		String first = suclass.equals("java.lang.Object") ? pubstring+" "+clastring+" "+thclass : pubstring+" "+clastring+" "+thclass+" extends "+suclass;
		first += " "+interfacestring;
		first = first.replaceAll("\\s+", " ");
		first = first.replaceAll("/",".");
		first = first.trim();
		fullstuff += first + "\n";
		
		fullstuff += "{\n";
		{
			for(FieldInfo fi : jolly)
			{
				fullstuff += "\t"+fi.getAccess() + " "+fi.getVariableType()+" "+fi.getName();
				for(Attribute at : fi.attribs)
				{
					if(at.getClass().getSimpleName().equals("ConstantValue") && fi.isStatic)
					{
						//This field must be static, because initializers should deal with non static fields.
						//however, ConstantValue is randomly given to non-static fields. IDK why.
						ConstantValue dat = (ConstantValue)at;
						fullstuff += " = "+dat.getValue();
					}
					fullstuff += ";\n";
				}
			}
			for(MethodInfo mi : meths)
			{
				if(mi.getName().equals("<init>"))
				{
					fullstuff += "\t"+(mi.getAccess()+" "+thclass+"("+mi.getParameters()+");").trim()+"\n";
					for(Attribute at : mi.attrs)
					{
						if(at.getClass().getSimpleName().equals("Code"))
						{
							Code cd = (Code)at;
							String op = cd.getOpCodes();
							Scanner sc = new Scanner(op);
							sc.useDelimiter("\n");
							Stack<Object> st = new Stack<>();
							ArrayList<Object> localvars = new ArrayList<>();

							while(sc.hasNext())
							{
								String stuff = sc.next();
								if(stuff.equals("nop"))
									continue;
								if(stuff.matches("const"))
								{
									if(stuff.startsWith("a"))
										st.push(null);
									else
									{
										String barley = stuff.replaceAll("const_(.+)", "$1");
										if(barley.equals("m1"))
											st.push(-1);
										else
											st.push(Integer.parseInt(barley));
									}
									continue;
								}
								if(stuff.matches("load"))
								{
									String ty = stuff.replaceAll("(.+)load","$1");
									Class<?> cakers = null;
									switch (ty)
									{
										case "i": cakers = int.class;
										case "l": cakers = long.class;
										case "f": cakers = float.class;
										case "d": cakers = double.class;
										case "a": cakers = Object.class;
									}
									String var = stuff.replaceAll("load(.+)", "$1");
									if(var.equals(""))
									{
										int index = (int)st.pop();
										st.push(cakers.cast(localvars.get(index)));
									}
									else
									{
										int index = Integer.parseInt(var);
										st.push(cakers.cast(localvars.get(index)));
									}
									continue;
								}
								if(stuff.matches("store"))
								{
									String var = stuff.replaceAll("store(.+)", "$1");
									if(var.equals(""))
									{
										String ls = stuff.replaceAll("\\((.+)\\)", "$1");
										localvars.add(Integer.parseInt(ls), st.pop());
									}
									else
									{
										int index = Integer.parseInt(var);
										localvars.add(index, st.pop());
									}
									continue;
								}
							}
							sc.close();
						}
					}
				}
				else
					fullstuff += "\t"+mi.getAccess() + " "+mi.getReturnType()+ " " +mi.getName()+"("+mi.getParameters()+")"+";\n";
			}
		}
		fullstuff += "}\n";
		
		fullstuff = fullstuff.replaceAll(pack+"\\.", "");
		fullstuff = fullstuff.replaceAll("java.lang.", "");
		System.out.println(fullstuff);
	}
	
	/**
	 * Interesting method from http://stackoverflow.com/questions/9655181/convert-from-byte-array-to-hex-string-in-java
	 * This will probably need to be rewritten by me, but it's the best one to use
	 * @see <a href=http://stackoverflow.com/questions/9655181/convert-from-byte-array-to-hex-string-in-java>http://stackoverflow.com/questions/9655181/convert-from-byte-array-to-hex-string-in-java</a>
	 * @param bytes
	 * @return the String, in hex, representation of this Object
	 */
	public static String bytesToHex(byte[] bytes) 
	{
	    final char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
	    char[] hexChars = new char[bytes.length * 2];
	    int v;
	    for ( int j = 0; j < bytes.length; j++ ) {
	        v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
}
