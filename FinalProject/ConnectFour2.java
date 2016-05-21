import java.util.*;

public class ConnectFour2{

    private String[][] board;
    private String[][] boardT;  //board tranposed
    private final int size = 5;
    private int emptyTilesCount = size * size;
    private int turn = 1;
    private Point oldMove;
    private Point compMove;
    private String[] markers = {"X", "0"};

    /**
     * Initialize the board filling every cells with spaces
     **/
    public ConnectFour2()
    {
	board = new String[size][size];
	boardT = new String[size][size];
	for(int i = 0; i < size; i++)
	    {
		for(int j = 0; j < size; j++)
		    {
			board[i][j] = " ";
			boardT[i][j] = " ";
		    }
	    }
    }

    /**
     * Add the marker to the specified column of the board
     *
     * @param col the column to add the marker to
     * @param marker the character representing the player
     * @return whether the operation was successful or not
     */
    public boolean add(int col, String marker)
    {
	String[] column = board[col];
	int i;
	for(i = 0; i < column.length && !(column[i].equals(" ")); i++);
	if (i >= column.length)
	    {
		return false;
	    }
	else
	    {
		column[i] = marker;
		board[col] = column;
		boardT[i][col] = marker;
		emptyTilesCount--;
		return true;
	    }
    }

    /**
     * Remove the highest marker at the specified column from the board
     *
     * @param col the column to remove a marker from
     */
    public void remove(int col)
    {
	String[] column = board[col];
	int i;
	for(i = size - 1; i >= 0 && column[i].equals(" "); i--);
	if (i >= 0)
	    {
		column[i] = " ";
		board[col] = column;
		boardT[i][col] = " ";
		emptyTilesCount++;
	    }
    }

    public String checkBoard()
    {
	for(int i = 0; i < size; i++)
	    {
		for(int j = 0; j < size; j++)
		    {
			String result = checkBoard(i, j);
			if (result.length() > 0)
			    {
				return result;
			    }
		    }
	    }
	return "";
    }

    /**
     * Check if the game is over: win, draw, or lose
     * 
     * @param col the column whose highest marker would be selected when testing for game over conditions
     * @return the marker of the winning player when one player has won
     * @return "draw" when no moves can be made
     * @return an empty string when the game is not over
     */
    private String checkBoard(int r, int c)
    {
	String[] column = board[c];
	if (column[r].equals(" ") || emptyTilesCount > (size * size - 8))
	    {
		return "";
	    }
	int count = 0;
	for(int j = r; j < size && column[r].equals(column[j]); j++, count++);
	for(int j = r; j > -1 && column[r].equals(column[j]); j--, count++);
	if (count > 4)
	    {
		return column[r];
	    }
	String[] row = boardT[r];
	count = 0;
	for(int j = r; j < size && row[c].equals(row[j]); j++, count++);
	for(int j = r; j > -1 && row[c].equals(row[j]); j--, count++);
	if (count > 4)
	    {
		return row[c];
	    }
	count = 0;
	String original = row[c];
	int copy1 = c;
	int copy2 = c;
	for(int j = r; j < size && copy1 < size && boardT[j][copy1].equals(original); j++, copy1++, count++);
	for(int j = r; j > -1 && copy2 > -1 && boardT[j][copy2].equals(original); j--, copy2--, count++);
	if (count > 4)
	    {
		return original;
	    }
	count = 0;
	copy1 = c;
	copy2 = c;
	for(int j = r; j < size && copy1 > -1 && boardT[j][copy1].equals(original); j++, copy1--, count++);
	for(int j = r; j > -1  && copy2 < size && boardT[j][copy2].equals(original); j--, copy2++, count++);
	if (count > 4)
	    {
		return original;
	    }
	if (emptyTilesCount == 0)
	    {
		return "draw";
	    }
	return "";
    }

    private ArrayList<Point> availableMoves()
    {
	ArrayList<Point> possibleMoves = new ArrayList<Point>();
	for(int i = 0; i < size; i++)
	    {
		int j;
		for(j = size - 1; j > -1 && board[i][j].equals(" "); j--);
		j++;
		if (j >= size)
		    {
			continue;
		    }
		Point move = new Point(i, j);
		possibleMoves.add(move);
	    }
	Collections.shuffle(possibleMoves);
	return possibleMoves;
    }

    private boolean isValidSpot(int row, int col, String marker)
    {
	//http://codereview.stackexchange.com/questions/82647/evaluation-function-for-connect-four
	return row > -1 && col > - 1 && col < size && row < size && board[row][col].equals(marker);
    }

    private int minimax(int turncopy, int depth, int alpha, int beta)
    {
	if (depth > 0)
	    {
		String status = checkBoard(oldMove.getY(), oldMove.getX());
		if (status == markers[turn])
		    {
			return (size * size) - depth;
		    }
		if (status.length() == 1)
		    {
			return depth - (size * size);
		    }
		if (status.equals("draw"))
		    {
			return 0;
		    }
	    }
	depth++;
	ArrayList<Integer> scores = new ArrayList<Integer>();
	ArrayList<Point> moves = new ArrayList<Point>();
	for(Point move: availableMoves())
	    {
		oldMove = new Point(move.getX(), move.getY());
		add(move.getX(), markers[turncopy]);
		scores.add(minimax(1 - turncopy, depth, alpha, beta));
		moves.add(move);
		if (depth == 1)
		    {
			System.out.println("Reached depth 1");
		    }
		//System.out.println("\u001b[2J\u001b[H");
		//System.out.println(this);
		System.out.flush();
		if (turncopy == 1)
		    {
			int maxScoreIndex = scores.indexOf(Collections.max(scores));
			compMove = moves.get(maxScoreIndex);
			int score = scores.get(maxScoreIndex);
			if (score > alpha)
			    {
				alpha = score;
			    }
		    }
		else
		    {
			int minScoreIndex = scores.indexOf(Collections.min(scores));
			int score = scores.get(minScoreIndex);
			if (score < beta)
			    {
				beta = score;
			    }
		    }
		remove(move.getX());
		if (alpha >= beta && turncopy == 1)
		    {
			return alpha;
		    }
		if (alpha >= beta && turncopy == 0)
		    {
			return beta;
		    }
	    }
	if (turncopy == 1)
	    {
		return alpha;
	    }
	else
	    {
		return beta;
	    }
    }

    public void getBestMove()
    {
	int score = minimax(turn, 0, -100000, 100000);
	add(compMove.getX(), markers[turn]);
	//turn = 1 - turn;
    }
    
    /**
     * Read in user input from the terminal and validate the user input
     * 
     * @params possibleInputs an array of acceptable user inputs
     */
    public static String getUserInput(String[] possibleInputs)
    {
	String ret = "";
	Scanner s = new Scanner(System.in);
	int i =0;
	if(s.next()=="exit")
	    {
		return "Player forfeits";
	    }
	while(s.hasNext())
	    {
		ret = s.next(possibleInputs[i]);
		i++;
	    }
	if(ret=="")
	    {
		System.out.println("Not a valid input fool");
		return getUserInput(possibleInputs);
	    } 
	else
	    {
		return ret;
	    }

    }
    
    /**
     * The main game engine that takes user input, update the board, and display the results
     */
    //this is the main game engine
    //call getUserInput to figure who goes first
    //call getUserInput to figure out the marker for player 1
    //call getUserInput to figure out the marker for player 2
    //main while loop, loop while not gameover
    //display board
    //call getUserInput to recieve user move
    //check if valid move and update board
    //if valid move call getUserInput for second player
    //otherwise repeat until move is valid
    public static void main(String[]args)
    {
	ConnectFour2 connectFour = new ConnectFour2();
	String[] possibleInputs = {"1","2","3","4"}; 
	if(true)//Math.random()*2==0)
	    {
		System.out.println("You start!");
		String input  = getUserInput(possibleInputs);
		connectFour.add(Integer.parseInt(input) - 1, "X");
	    }
	else
	    {
		System.out.println("Computer starts!");
	    }
    }

    /**
     * Return the string representation of the board
     *
     * @return the string representation of the board
     */
    public String toString(){
	String ans = "|";
	for(int i = size - 1; i >= 0; i--)
	    {
		for(int j = 0; j < size; j++)
		    {
			ans += boardT[i][j];
			ans += "|";
		    }
		ans += "\n";
		if (i > 0)
		    {
			ans += "|";
		    }
	    }
	return ans;
    }
}
