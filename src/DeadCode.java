
public class DeadCode 
{
	/*
	 * 
	 * 
	 * Decompiler.decompile()
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
	*/
}
