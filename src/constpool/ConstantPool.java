package constpool;

import java.util.*;

/**
 * A ConstantPool that basically holds all the information,
 * more or less the foundation for the entire file.
 * This is a implementation of the ArrayList class,
 * with ConstantType arguments.  Otherwise, it blocks all calls to add
 * after it is at maximum size.  It is essentially a fixed size ArrayList.
 * The methods addAll are completely unused.
 * @author Fleur
 */
public class ConstantPool extends ArrayList<ConstantType>
{
	/**
	 * A generated value for use in serialization
	 */
	private static final long serialVersionUID = 7113705299203289046L;
	/**
	 * The maximum amount of ConstantType objects that will be used
	 */
	final int maxsize;
	//a type that basically acts as a storage device for the constant pool
	public ConstantPool(int x)
	{
		super(x);
		ensureCapacity(x);
		maxsize = x;
	}
	
	/**
	 * A method that stops execution of a method because
	 * of the add and addAll methods.
	 */
	public void block()
	{
		throw new IllegalArgumentException("That method is unavailable.");
	}
	@Override
	/**
	 * This method will call super.add() if the maximum size is less than the
	 * current size of the value.  Otherwise, it will call block() and exit without changes.
	 */
	public boolean add(ConstantType x)
	{
		if(maxsize == Integer.MIN_VALUE || size() < maxsize)
		{
			super.add(x);
			return true;
		}
		block();
		return false;
	}
	/**
	 * A method similar to add(Object), except with an index. Calls super.add()
	 *  if the size is less than the maximum size. If not, block() is called and 
	 *  no changes will occur. 
	 */
	@Override
	public void add(int index, ConstantType x)
	{
		if(maxsize == Integer.MIN_VALUE || size() < maxsize)
		{
			super.add(index,x);
			return;
		}
		block();
		return;
	}
	
	/**
	 * A method that only throws an Exception, because you can't add much here.
	 * Do not use this method.  It is only there to override the super type method.
	 */
	@Override
	public boolean addAll(Collection<? extends ConstantType> c)
	{
		block();
		return false;
	}
	@Override
	/**
	 * A method that only throws an Exception, because you can't add much here.
	 * Do not use this method.  It is only there to override the super type method.
	 */
	public boolean addAll(int index, Collection<? extends ConstantType> c)
	{
		block();
		return false;
	}
	
	/**
	 * This returns the class name and calls the ArrayList toString method.
	 * This, in turn, calls the method in the AbstractCollection class.
	 * @see <a href=http://docs.oracle.com/javase/6/docs/api/java/util/AbstractCollection.html#toString%28%29>http://docs.oracle.com/javase/6/docs/api/java/util/AbstractCollection.html#toString</a>
	 */
	public String toString()
	{
		return getClass().getSimpleName() +": "+super.toString();
	}
}
