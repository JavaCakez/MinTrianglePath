import java.util.*;
import java.io.*;

/**
 * <pre>
 * <p>
 * <h3>Class Description</h3>
 * MinTrianglePath reads a text-format triangle from standard input and outputs a minimal path to standard output.
 * </p>
 * <p>
 * <h3>Problem and Solution</h3>
 * The problem is solved with a top-to-bottom greedy approach which used <a href="http://en.wikipedia.org/wiki/Dijkstra's_algorithm">Dijkstra's algorithm </a> as inspiration.
 * The solution builds the triangle from top to bottom and as each node is built, along with that nodes value, the node also stores an aggregate value of the minimum path to that node, and the index of the parent node to that node in the minimum path.
 * This means, once the full triangle is built, we can simply find the node on the final row of the triangle with the smallest aggregate value, and traverse up the minimal path by following the parent indexes up to the apex (root) of the triangle.
 * <br/>
 * This is a divide-and-conquer approach. The problem could be very complex, especially for large triangles.
 * However, what we are doing is breaking the problem down into its smallest version. That is when we consider the smallest triangle of 3 nodes, an apex (root) and 2 children.
 * Hence, when we are building the triangle and we are adding a new row of nodes we do the following:
 * <br/>
 * <ol>
 * <li>Check if the new row is the first row in the triangle, if it is, simply set the aggregate value to its own value since this is the apex (root)</li>
 * <li>Otherwise, check if a node is a left-most node in that row. If it is, then it only has one parent, therefore set the aggregate value equal to the aggregate value of its only parent plus its own value</li>
 * <li>Similarly do the same for right-most nodes since they also only have one parent</li>
 * <li>For any other nodes (nodes in the middle of two parents), we then consider the simplest triangle as mentioned earlier, where the node in question we consider the apex, and the 2 parents we consider to be the 2 children. Then we can find the minimum aggregate of these two and set that to be the aggregate value and we select the correct parent index.</li>
 * </ol>
 * In this way, we are simplifying the problem down heavily to at most only having to ever consider 3 base-cases or the simplest triangle possible composed of 3 nodes.
 * This solution also solves the problem in a non-naive way. A naive solution would consider every possible path, and for large triangles would take infeasible amounts of time.
 * This solution will have a complexity of O(n) (where n is the number of nodes in the triangle) since we only make one pass over the triangle when we build it and then simply have to traverse the minimal path at the end.
 * </p>
 * <p>
 * <h3>Example</h3>
 * Consider the following Triangle:
 * <br/>
 * 7
 * 6 3
 * 6 8 2
 * 9 2 6 7
 * <br/>
 * We would then build this triangle using nodes which contain its value, its minimal aggregate value and its parent index for the minimum path.
 * I have written this as so: {value, aggregate, index}
 * The built triangle would then look like so:
 * <br/>
 * <br/>
 * index:_|____0____|____1____|____2____|____3____|
 *        |{7,7,0}  |         |         |         |
 *        |{6,13,0} |{3,10,0} |         |         |
 *        |{6,19,0} |{8,18,1} |{2,12,1} |         |
 *        |{9,28,0} |{2,20,1} |{6,18,2} |{7,19,2} |
 * <br/>
 * <br/>
 * Finally, we can then look at the bottom row, and see that the smallest aggregate value is 18.
 * We can then follow the parent indexes at each row up to follow the minimum path:
 * <strong>6 + 2 + 3 + 7 = 18</strong>
 * <br/>
 * Simply reversing this minimum path gives us the minimum path from top to bottom:
 * <strong>7 + 3 + 2 + 6 = 18</strong>
 * </p>
 * </pre>
 * 
 * @author Tom Hargrave
 * 
 */
public class MinTrianglePath
{
	private ArrayList<Node[]> triangle;
	
	/**
	 * Default MinTrianglePath Constructor, creating an empty triangle.
	 * 
	 * @throws IOException
	 */
	MinTrianglePath()
	{
		triangle = new ArrayList<Node[]>();	
	}
	
	/**
	 * Node class models individual nodes within a triangle.
	 * Each node consists of its own value, the aggregate value of the minimum path to that node and a parent index of its parent in the minimum path
	 * 
	 * @author Tom Hargrave
	 * 
	 */
	private class Node implements Comparable<Object>
	{
		private int value;
		private int aggregateValue;
		private int parentIndex;

		/**
		 * Node constructor
		 * 
		 * @param value - Node value
		 * @param aggregateValue - Aggregate value of the minimum path to that node
		 * @param parentIndex - Index of its parent in the minimum path
		 */
		Node(int value, int aggregateValue, int parentIndex)
		{
			this.value          = value;
			this.aggregateValue = aggregateValue;
			this.parentIndex    = parentIndex;
		}

		/**
		 * Node comparison - override default Object compareTo.
		 * Compares aggregate values of nodes.
		 * 
		 * @param other - Other Node to compare to.
		 */
		@Override
		public int compareTo(Object other)
		{
			int otherAggregateValue = ((Node) other).aggregateValue;
			if (this.aggregateValue > otherAggregateValue)
			{
				return 1;
			}
			else if (this.aggregateValue < otherAggregateValue)
			{
				return -1;
			}
			else
			{
				return 0;
			}
		}
	}
	
	/**
	 * Custom subclass of RuntimeException for any Input Errors
	 * 
	 * @author Tom Hargrave
	 *
	 */
	private class InputException extends RuntimeException
	{
		private static final long serialVersionUID = -5505559293900185367L;

		public InputException(String error)
		{
            super(error);
        }
    }
	
	
	/**
	 * @param argv - Program arguments
	 * @throws IOException
	 */
	public static void main (String[] argv)
	{
		long start = System.currentTimeMillis();
		MinTrianglePath program = new MinTrianglePath();
		program.run();
		System.out.println(System.currentTimeMillis() - start + " Milliseconds");
	}

	/**
	 * Runs the MinTrianglePath program
	 * 
	 * @throws IOException
	 */
	private void run()
	{
		try
		{
			buildTriangleFromConsole();
			System.out.println(minimumPathToString(getMinimumTrianglePath()));
		}
		catch (InputException ie)
		{
            System.out.println(ie.getMessage());
        }
	}

	/**
	 * Read a text-format triangle from standard input and build corresponding triangle
	 * 
	 * @throws IOException
	 */
	private void buildTriangleFromConsole()
	{
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
				
		// Continually read each line of input until EOF is reached
		try
		{
			int row = 0;
			String s;
			while ((s = stdin.readLine()) != null)
			{
				int[] intArray;
				try
				{
					intArray = convertStringToIntegerArray(s);
				}
				catch (NumberFormatException e)
				{
					throw new InputException("Error - Row " + row + " - Non-Integer detected.");
				}
				
				// Check for the correct number of inputs per line (note triangles have the property that each row should have rowIndex + 1 inputs)
				// i.e. the 0th Row has 1 input, 1st row has 2, etc.
				if (intArray.length == (triangleRows() + 1))
				{
					addRow(intArray);
					row++;
				}
				else if (intArray.length < (triangleRows() + 1))
				{
					throw new InputException("Error - Row " + row + " - Input line is too short. " + Integer.toString(triangleRows() + 1) + " integers expected. Only " + intArray.length + " integers found.");
				}
				else if (intArray.length > (triangleRows() + 1))
				{
					throw new InputException("Error - Row " + row + " - Input line is too long. " + Integer.toString(triangleRows() + 1) + " integers expected. " + intArray.length + " integers found.");
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				stdin.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}


	/**
	 * Populate an integer array with the values in the minimum path
	 * 
	 * The method first finds the endNode which is the node with the minimum aggregate on the final row
	 * It then creates an integer array which contains the paths values
	 * Next, working backwards (beginning with the endNode), the path values are added to the minimum path
	 * After each path value is added, we traverse one row up the triangle to the next node in the path by following the parentIndexes
	 * 
	 * @return minimumPath
	 */
	private int[] getMinimumTrianglePath()
	{
		Node endNode = finalRowMinAggregateNode();
		int[] minimumPath = new int[triangleRows()];
		
		minimumPath[triangleRows() - 1] = endNode.value;
		int currentParentIndex			= endNode.parentIndex;
		
		//Note -2 to account for 0 indexing and to ignore the row containing the endNode
		for (int row = triangleRows() - 2; row >= 0; row--)
		{
			minimumPath[row]	= triangle.get(row)[currentParentIndex].value;
			currentParentIndex	= triangle.get(row)[currentParentIndex].parentIndex;
		}

		return minimumPath;
	}

	/**
	 * Return a string of the minimum path, formatted in the specified way
	 */
	private String minimumPathToString(int[] minimumPath)
	{
		int aggregate = minimumPath[0];
		StringBuilder sb = new StringBuilder();
		
		sb.append("Minimal path is: ");
		sb.append(Integer.toString(minimumPath[0]));
		
		for (int i = 1; i < triangleRows(); i++)
		{
			sb.append(" + " + Integer.toString(minimumPath[i]));
			aggregate += minimumPath[i];
		}
		sb.append(" = " + Integer.toString(aggregate));
		
		return sb.toString();
	}


	/**
	 * Return the number of rows in the triangle.
	 * 
	 * @return
	 */
	private int triangleRows()
	{
		return triangle.size();
	}


	/**
	 * From an integer array, add the next row in the triangle
	 * 
	 * @param intArray
	 */
	private void addRow(int[] intArray)
	{
		assert(intArray.length == (triangleRows()));

		Node[] nodeArray = new Node[intArray.length];

		for (int i = 0; i < nodeArray.length; i++)
		{
			nodeArray[i] = createNode(i, intArray[i]);
		}

		triangle.add(nodeArray);
	}


	/**
	 * Return the node on the bottom row of the triangle which has the smallest aggregate value
	 * 
	 * @return
	 */
	private Node finalRowMinAggregateNode()
	{
		try
		{
			List<Node> lastRow = Arrays.asList(triangle.get(triangleRows()-1));
			return Collections.min(lastRow);
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			throw new InputException("Error - Input is empty and will result in an empty triangle and therefore no path.");
		}
	}


	/**
	 * Process input strings, splitting on spaces and read into an integer array
	 * 
	 * @param s
	 * @return
	 */
	private int[] convertStringToIntegerArray(String s)
	{
		//Allow for more than one space between values
		String[] strArray 	= s.split("[ ]+");
		int[] intArray   	= new int[strArray.length];

		for (int i = 0; i < intArray.length; i++)
		{
			intArray[i] = Integer.parseInt(strArray[i]);
		}
		return intArray;
	}


	/**
	 * Creates and returns a Node given the index and value of that node. Correctly chooses parent giving the minimum aggregate value
	 * Choosing the correct parent is done in a greedy manner, simply choosing the one with the minimum aggregate value (except in the corner cases) 
	 * The calling method will call this for every new node in a new row.
	 * 
	 * @param value
	 * @param index
	 * @return
	 */
	private Node createNode(int index, int value)
	{
		Node node;

		// For the apex (root) node
		if (triangleRows() == 0)
		{
			node = new Node(value, value, 0);
			return node;
		}

		Node[] previousRow = triangle.get(triangleRows() - 1);
		// Left-most nodes - only have a single parent at index 0
		if (index == 0)
		{
			node = new Node(value, previousRow[index].aggregateValue + value, index);
		}
		// Left-most nodes - only have a single parent at the end index (which for triangles of this format is the same as the 1-indexed row number)
		else if (index == triangleRows())
		{
			node = new Node(value, previousRow[index-1].aggregateValue + value, index-1);
		}
		// For all other entries in the middle of a row - choose parent giving minimum aggregate 
		else
		{
			if (previousRow[index-1].aggregateValue < previousRow[index].aggregateValue)
			{
				node = new Node(value, previousRow[index-1].aggregateValue + value, index-1);
			}
			else
			{
				node = new Node(value, previousRow[index].aggregateValue + value, index);
			}
		}
		return node;
	}
}