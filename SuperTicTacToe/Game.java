import java.util.*;
import java.io.*;

public class Game{

    //    ArrayList<Character> board;
    //String board;
    String[] pos;
    ArrayList<Integer> noPut;
    public Game()
    {
	//	board = "";
	noPut = new ArrayList<Integer>();
	pos = new String[81];
	for(int i=0;i<81;i++)
	    {
		pos[i] = " ";
	    }
	//board = toString();
    }

    public boolean add(int outer,int inner,String marker)
    {
	if(pos[outer*9+inner]!="X"||pos[outer*9+inner]!="O")
	    {
		pos[outer*9+inner]=marker;
		return true;
	    }
	return false;
    }

    public String toString()
    {
	String board = "";
	try{
	    BufferedReader boardtxt = new BufferedReader(new FileReader("board.txt"));
	    for(int i = 0;i < 17;i++)
		{
		    String line = boardtxt.readLine();
		    for(int j=0;j<line.length();j++)
			{
			    if(j%2==0&&i%2==0)
				{
				    board+=pos[j/2];
				}
			    else
				{
				    board+=line.charAt(j);
				}
			}
		    board+="\n";
		    /*

		    */
		}
	}
	catch(Exception e)
	    {System.out.println(e);}

	return board;
    }
    
    public static void main(String[]args)
    {
	Game test = new Game();
	System.out.println(test);
    }
}
