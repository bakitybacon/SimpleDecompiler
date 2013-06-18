package others;

import java.util.Hashtable;

public class InfoParser 
{
	public static String getVariableType(String desc)
	{
		
		String[] first = new String[]
			{
				"B","C","D","F","I","J","S","Z"
			};
		String[] seconds = new String[]
			{
				"byte","char","double","float","int","long","short","boolean"
			};
		Hashtable<String,String> hm = new Hashtable<>();
		for(int i = 0; i < first.length;i++)
			hm.put(first[i], seconds[i]);
		
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
}
