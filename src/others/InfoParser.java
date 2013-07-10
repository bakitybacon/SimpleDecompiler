package others;

import java.util.ArrayList;
import java.util.Hashtable;

public class InfoParser 
{
	static String[] first = new String[]
		{
		"B","C","D","F","I","J","S","Z"
		};

	static String[] seconds = new String[]
		{
			"byte","char","double","float","int","long","short","boolean"
		};
	static Hashtable<String,String> hm = new Hashtable<>();
	static
	{
		for(int i = 0; i < first.length;i++)
			hm.put(first[i], seconds[i]);		
	}
	public static String getVariableType(String desc)
	{
		String addend = "";
		
		while(desc.startsWith("["))
		{
			addend += "[]";
			desc = desc.substring(1);
		}
		
		if(hm.containsKey(desc))
			return hm.get(desc)+addend;
		else
		{
			if(!desc.startsWith("L") || !desc.endsWith(";"))
				return null;
			else return desc.substring(1, desc.length() - 1).replaceAll("/",".")+addend;
		}
	}
	public static String getMultiVariableType(String desc)
	{
		String result = "";
		String parsing = "";
		while(desc.length() > 0)
		{
			String jake = ""+desc.charAt(0);
			if(hm.containsKey(jake) && !jake.equals("L"))
			{
				parsing += desc.charAt(0);
				result += getVariableType(parsing)+",";
				parsing = "";
				desc = desc.substring(1);
			}
			else if (jake.equals("L"))
			{
				while(desc.charAt(0) != ';')
				{
					parsing += desc.charAt(0);
					desc = desc.substring(1);
				}
				parsing += ";";
				System.out.println("I parsed the object:"+parsing);
				result += getVariableType(parsing)+",";
				parsing = "";
				desc = desc.substring(1);
			}
			else
			{
				parsing += desc.charAt(0);
				desc = desc.substring(1);
			}
		}
		if(result.endsWith(","))
			result = result.substring(0,result.length()-1);
		return result;
	}
	public static String[] getMultiVariableTypeArray(String joseph)
	{
		ArrayList<String> result = new ArrayList<>();
		String parsing = "";
		while(joseph.length() > 0)
		{
			String jake = ""+joseph.charAt(0);
			if(hm.containsKey(jake) && !jake.equals("L"))
			{
				parsing += joseph.charAt(0);
				result.add(getVariableType(parsing));
				parsing = "";
				joseph = joseph.substring(1);
			}
			else if (jake.equals("L"))
			{
				while(joseph.charAt(0) != ';')
				{
					parsing += joseph.charAt(0);
					joseph = joseph.substring(1);
				}
				parsing += ";";
				System.out.println("I parsed the object:"+parsing);
				result.add(getVariableType(parsing));
				parsing = "";
				joseph = joseph.substring(1);
			}
			else
			{
				parsing += joseph.charAt(0);
				joseph = joseph.substring(1);
			}
		}
		String[] asdf = new String[result.size()];
		return result.toArray(asdf);
	}
	public static int getArgumentLength(String jake)
	{
		return getMultiVariableTypeArray(jake).length;	
	}
}
