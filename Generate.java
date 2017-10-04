
package coursegenerator;

import java.util.Arrays;
import java.util.LinkedList;

public class Generate {
    private class CourseSet {
        int[][] courses;
        private int rows;
        
        //default constructor
        private CourseSet()
        {
            courses = null;
        }
        
        //constructor
        private CourseSet(int row, int column)
        {
            courses = new int[row][column];
            rows = 0;
        }
        
        //if a courseset contains course
        private boolean contain(int course)
        {
            for (int i = 0; i < professors; i++)
                for (int j = 0; j < recieve; j++)
                    if (courses[i][j] == course)
                        return true;
            return false;
        }
       
        //insert new course at the first available spot
        private void insertLast(int course)
        {
            for (int i = 0; i < professors; i++)
            {
                for (int j = 0; j < recieve; j++)
                {
                    if (courses[i][j] == 0)
                    {
                        courses[i][j] = course;
                        return;
                    }
                }

            }   
        }
        
        private int lengthOfCurrentRow()
        {
            int count = 0;
            for (int i = 0; i < recieve; i++)
                    if (courses[rows][i] != 0)
                        count++;
            return count;
        }

    }
    
    private int professors; //number of prof
    private int submitted;  //number profs submitted
    private int recieve;    //number profs will recieve
    private int[][] lineup; //lineup of all the profs and submitted courses
    
    public Generate(int[][] lineup, int professors, int submitted, int recieve)
    {
        this.professors = professors;
        this.submitted = submitted;
        this.recieve = recieve;
        this.lineup = lineup;
    }
    
    public void solve() 
    {
	LinkedList<CourseSet> openList = new LinkedList<>(); //open linked list
	LinkedList<CourseSet> closedList = new LinkedList<>(); //closed linked list
        
	CourseSet courseSet = new CourseSet(professors, recieve); //initialize board
	openList.addFirst(courseSet); //add initial board to open list
        
	while (!openList.isEmpty())
	{
		courseSet = openList.removeFirst(); //remove current courseset from openList
		closedList.addLast(courseSet);  //add courseset to closed list

                if (courseSet.lengthOfCurrentRow() == recieve)
                    courseSet.rows++;
                
		if (complete(courseSet))    //if courseset is solution
		{
			displayCourseSet(courseSet); //display openlist (leafs of tree)                        
			return;
		}
		else
		{
			LinkedList<CourseSet> children = generate(courseSet); //get all children of courseset
                        for (int i = 0; i < children.size(); i++) //add children to openList
                        {
                            CourseSet child = children.get(i);  //for each child
                            if(!exists(openList, child))
                                openList.addFirst(child);   
                        }                             
                }
        }
	System.out.println("no solution");
    }
    
    
    //Method generates children of a board
    private LinkedList<CourseSet> generate(CourseSet courseSet)
    {
        LinkedList<CourseSet> newChildren = new LinkedList<>();      

        for (int i = 0; i < submitted; i++)
        {
            CourseSet newChild = copy(courseSet);
            if (!newChild.contain(lineup[newChild.rows][i])) //if newChild doesn't already has course
            {
                newChild.insertLast(lineup[newChild.rows][i]);
                
                if (check(newChild, newChild.rows)) //check to see if newChild has no conflicting
                    newChildren.addFirst(newChild); //course scheduling
            }
        }
        return newChildren; //return list of children
    }
    
    //Method checks if child has already been created 
    private boolean exists(LinkedList<CourseSet> openList, CourseSet child)
    {
        for (CourseSet courseSet : openList) //for each child in openlist
        {    
                if (rowContains(child.rows, child, courseSet)) //child is unieuq
                    return true;
        }
        return false;
    }
    
    //method checks if each row is unique in the child compared to whats in the openlist child
     private boolean rowContains(int row, CourseSet courseSet1, CourseSet courseSet2)
        {
            int[] arr1 = new int[recieve];
            int[] arr2 = new int[recieve];
            for (int i = 0; i < recieve; i++)
            {
                arr1[i] = courseSet1.courses[row][i];
                arr2[i] = courseSet2.courses[row][i];
            }

            if (arr1.length != arr2.length) {
                return false;
            }
            Arrays.sort(arr1);
            Arrays.sort(arr2);
            return Arrays.equals(arr1, arr2);
        }
    
    //Method checks whether baord is complete
    private boolean complete(CourseSet board) 
    {
        return (board.rows == professors);
    }
    
    //Method makes copy of board
    private CourseSet copy(CourseSet courseSet)
    {
        CourseSet result = new CourseSet(professors, recieve);
        
        for (int i = 0; i < professors; i++)
            for (int j = 0; j < recieve; j++)
                result.courses[i][j] = courseSet.courses[i][j];
            
        result.rows = courseSet.rows;
        
        return result;
    }
    
    //Method displays single board
    private void displayCourseSet(CourseSet courseSet) {

            for (int i = 0; i < professors; i++)
            {
                System.out.print(i + 1 +": ");

                for (int j = 0; j < recieve; j++)
                    System.out.print(courseSet.courses[i][j] + " ");
                
                System.out.println();
            }
       }
    
    //Method that checks if the course set is a valid solution where there 
    //is no professor with the same class or a single prof with the same two
    //classes
    private boolean check(CourseSet courses, int rowsInSet)
    {
        LinkedList<Integer> tempList = new LinkedList<Integer>();
        for (int l = 0; l < rowsInSet; l++)
        {
            for (int k = 0; k < recieve; k++)
            {
               if (tempList.contains(courses.courses[l][k]))
                   return false;
               tempList.addFirst(courses.courses[l][k]);
            }   
        }
        
        return true;
    }
}
