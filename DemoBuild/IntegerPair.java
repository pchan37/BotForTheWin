public class IntegerPair
{
    private int first;
    private int second;
    
    public IntegerPair(int first, int second)
    {
	this.first = first;
	this.second = second;
    }

    public void setFirst(int first)
    {
	this.first = first;
    }

    public void setSecond(int second)
    {
	this.second = second;
    }

    public void set(int first, int second)
    {
	setFirst(first);
	setSecond(second);
    }
    
    public int first()
    {
	return first;
    }

    public int second()
    {
	return second;
    }
}
