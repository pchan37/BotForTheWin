import java.util.*;
import java.io.*;

public class Game{

    //    ArrayList<Character> board;
    //String board;
    String[] pos;
    String[][] wins;
    ArrayList<Integer> noPut;
    public Game()
    {
	//	board = "";
	noPut = new ArrayList<Integer>();
	pos = new String[81];
	wins = new String[3][3];
	for(int i=0;i<81;i++)
	    {
		pos[i]=" ";
	    }
	for(int i=0;i<3;i++)
	    {
		for(int j=0;j<3;j++)
		    {
			wins[i][j]="";
		    }
	    }
	//board = toString();
    }

    public boolean add(int outer,int inner,String marker)
    {
	if(pos[(outer/3)*27+(inner/3)*9+(inner%3)+(outer%3)*3]!="X"||pos[(outer/3)*27+(inner/3)*9+(inner%3)+(outer%3)*3]!="O")
	    {
		pos[(outer/3)*27+(inner/3)*9+(inner%3)+(outer%3)*3]=marker;
		System.out.println(toString());
		if(checkWinInner(outer,marker))
		    {
			for(int i=0;i<9;i++)
			    {
				pos[(outer/3)*27+(i/3)*9+(i%3)+(outer%3)*3]=marker;
			    }
			System.out.println(toString());
		
		    }
		return true;
	    }
	return false;
    }

    public boolean remove(int outer,int inner)
    {
	pos[(outer/3)*27+(inner/3)*9+(inner%3)+(outer%3)*3]=" ";
	return true;
    }

    public String getUserInput(String[] possibleInputs)
    {
	//        System.out.println("Your turn!");
        Scanner s = new Scanner(System.in);

        String ret = "";
        if (s.hasNext())
            {
                ret = s.next();
                if (ret.equals("q"))
                    {
                        System.out.println("Player forfeits.  Bot wins!");
                        return "q";
                    }
                for(int i = 0; i < possibleInputs.length; i++)
                    {
                        if(ret.equals(possibleInputs[i]))
                            {
                                return ret;
                            }
                    }
                System.out.println("Not a valid input, fool!");
                return getUserInput(possibleInputs);
            }
        return "";
    }

    public String checkWin()
    {
	if(wins[0][0]==wins[1][1] && wins[0][0]==wins[2][2])
	    {
		return wins[0][0];
	    }
	if(wins[0][2]==wins[1][1] && wins[0][0]==wins[2][0])
	    {
		return wins[0][2];
	    }	
	for(int i=0;i<3;i++)
	    {
		if(wins[0][i]==wins[1][i] && wins[0][i]==wins[2][i])
		    {
			return wins[0][i];
		    }
	    }
	for(int i=0;i<3;i++)
	    {
		if(wins[i][0]==wins[i][1] && wins[i][0]==wins[i][2])
		    {
			return wins[i][0];
		    }
	    }
	return "";
    }
    
    public boolean checkWinInner(int outer,String marker)
    {
	int inner = 0;
	if(pos[(outer/3)*27+(inner/3)*9+(inner%3)+(outer%3)*3]==pos[(outer/3)*27+((inner+4)/3)*9+((inner+4)%3)+(outer%3)*3]&&pos[(outer/3)*27+(inner/3)*9+(inner%3)+(outer%3)*3]==pos[(outer/3)*27+((inner+8)/3)*9+((inner+8)%3)+(outer%3)*3]&&pos[(outer/3)*27+(inner/3)*9+(inner%3)+(outer%3)*3]==marker)
	    {
		wins[outer/3][outer%3]=marker;
		return true;
	    }
	inner = 2;
	if(pos[(outer/3)*27+(inner/3)*9+(inner%3)+(outer%3)*3] == pos[(outer/3)*27+((inner+2)/3)*9+((inner+2)%3)+(outer%3)*3] && pos[(outer/3)*27+(inner/3)*9+(inner%3)+(outer%3)*3] == pos[(outer/3)*27+((inner+4)/3)*9+((inner+4)%3)+(outer%3)*3] && pos[(outer/3)*27+(inner/3)*9+(inner%3)+(outer%3)*3] == marker)
	    {
		wins[outer/3][outer%3]=marker;
		return true;
	    }
	
	for(inner=0;inner<3;inner++)
	    {
		if(pos[(outer/3)*27+(inner/3)*9+(inner%3)+(outer%3)*3] == pos[(outer/3)*27+((inner+3)/3)*9+((inner+3)%3)+(outer%3)*3] && pos[(outer/3)*27+(inner/3)*9+(inner%3)+(outer%3)*3] == pos[(outer/3)*27+((inner+6)/3)*9+((inner+6)%3)+(outer%3)*3] && pos[(outer/3)*27+(inner/3)*9+(inner%3)+(outer%3)*3] == marker)
		    {
			wins[outer/3][outer%3]=marker;
			return true;
		    }
	    }
	for(inner=0;inner<7;inner+=3)
	    {
				if(pos[(outer/3)*27+(inner/3)*9+(inner%3)+(outer%3)*3] == pos[(outer/3)*27+((inner+1)/3)*9+((inner+1)%3)+(outer%3)*3] && pos[(outer/3)*27+(inner/3)*9+(inner%3)+(outer%3)*3] == pos[(outer/3)*27+((inner+2)/3)*9+((inner+2)%3)+(outer%3)*3] && pos[(outer/3)*27+(inner/3)*9+(inner%3)+(outer%3)*3] == marker)
				    {
					wins[outer/3][outer%3]=marker;
					return true;
				    }
	    }
	return false;
	
    }

    public String toString()
    {
	String board = "\n";
	try{
	    BufferedReader boardtxt = new BufferedReader(new FileReader("board.txt"));
	    int k=0;
	    for(int i = 0;i < 17;i++)
		{
		    String line = boardtxt.readLine();
		    for(int j=0;j<line.length();j++)
			{
			    // System.out.println(k);
			    if((j-1)%4==0&&i%2==0)
				{
				    board+=pos[k];
				    k++;
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
	String[] possibleInputs = {"1","2","3","4","5","6","7","8","9"};
	Game test = new Game();
	System.out.println(test);

	while(test.checkWin()=="")
	    {
		System.out.println("You start!");
		int outer,inner;
		System.out.println("Select Outer");	
		outer = Integer.parseInt(test.getUserInput(possibleInputs))-1;
		System.out.println("Select Inner");	
		inner = Integer.parseInt(test.getUserInput(possibleInputs))-1;
		test.add(outer,inner,"X");
		//		System.out.println(test);	    
	    }
	
	
	//test.add(1,4,"X");
	/*
	for(int i=0;i<9;i++)
	    {
		for(int j=0;j<9;j++)
		    {
			System.out.println(i+" "+j);
			test.add(i,j,"X");
			System.out.println(test);
		    }
	    }

	for(int i=0;i<9;i++)
	    {
		for(int j=0;j<9;j++)
		    {
			System.out.println(i+" "+j);
			test.remove(i,j);
			System.out.println(test);
		    }
	    }
	*/
	//	System.out.println(test);
	
	
    }
}
