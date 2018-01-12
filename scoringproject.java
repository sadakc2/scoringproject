/*
NAME: Christina Sadak
PROGRAM: Red Ventures Scoring Project
PURPOSE: goes through html source code and calculates a score based on the html tags found. Stores data in database and allows users to access data in the DB
  based on choices made through a switch statement.
NOTES: does not work completely. DOES score the html and retrieve the date and time that it was scored, but I could not connect to mysql using java. Therefore, in comments is how I would have input data into the database
  and retrieved the desired info for the user if the connection had worked...
       All code that is commented out is code that I would have used to import data into my database or to retrieve data from the database for the user
*/

import java.io.*;
import java.net.*;
import java.util.*;
//import java.sql.*;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;//included to properly format date
import java.text.SimpleDateFormat;//included to properly format date
import java.util.Scanner;//included to allow user to enter data

public class scoringproject
{
   public static void main(String[] args) throws IOException
   {

     /*

     How I tried to connect to the database is as followed.
     The error I kept getting is that the proper driver did not exist

     System.out.print("Please enter the desired database URL: ");
     stdin = new InputStreamReader(System.in);
     keyboard = new BufferedReader(stdin);
     String DBurl = keyboard.readLine();

     System.out.print("Please enter your username: ");
     stdin = new InputStreamReader(System.in);
     keyboard = new BufferedReader(stdin);
     String DBusername = keyboard.readLine();

     System.out.print("Please enter your password: ");
     stdin = new InputStreamReader(System.in);
     keyboard = new BufferedReader(stdin);
     String DBpassword = keyboard.readLine();

     //connect to mySQL
     try
     {
       System.out.println("Connecting to the database...");
       Class.forName("com.mysql.jdbc.Driver").newInstance();
       Conection conn = DriverManager.getConnection(DBurl, DBusername, DBpassword);
       Statement stmnt = conn.createStatement();

       System.out.println("Yay! Successful database connection.");
     }
     catch(Exception e)
     {
      System.out.println("Bummer... couldn't connect to the database. Exiting.")
      //System.out.println(e);
      System.exit(1);
     }

     */

    String fileName;//stores file name entered in by user
    String dataline;
    BufferedReader inputFile;
    FileReader freader;

    int score = 0;

    //intro
    System.out.println("");
    System.out.println("Welcome to Christina's Scoring Project!");
    System.out.println("");
    System.out.print("Please enter the file you would like to score: ");
    //allows user to enter the filename of the html to read
    InputStreamReader stdin = new InputStreamReader(System.in);
    BufferedReader keyboard = new BufferedReader(stdin);
    fileName = keyboard.readLine();
      System.out.println("");

    //tries to open the file name, if it fails, it moves to the catch block
    try
    {
      freader = new FileReader(fileName);
      inputFile = new BufferedReader(freader);
    }
    catch(IOException e)
    {
      //if there is an exception thrown and the file won't open, this outputs an error message and exits mains
      System.out.println("Error: Unable to open file: "+ fileName);
      System.out.println("Cannot recover from error. Exiting.");
      return;
    }


    //read html in the file line by line and look for each tag to Score
    dataline = inputFile.readLine();//prime read
    while( dataline != null)//reads until the end of the file
    {
      //each checks with < in front of the desired tag to keep from counting closing tags also, because that would result in the score being double what it should be

      if(dataline.contains("<div"))
      {
        score = score + 3;
      }
      if(dataline.contains("<p"))
      {
        score = score + 1;
      }
      if(dataline.contains("<h1"))
      {
        score = score + 3;
      }
      if(dataline.contains("<h2"))
      {
        score = score + 2;
      }
      if(dataline.contains("<html"))
      {
        score = score + 5;
      }
      if(dataline.contains("<body"))
      {
        score = score + 5;
      }
      if(dataline.contains("<header"))
      {
        score = score + 10;
      }
      if(dataline.contains("<footer"))
      {
        score = score + 10;
      }
      if(dataline.contains("<font"))
      {
        score = score - 1;
      }
      if(dataline.contains("<center"))
      {
        score = score - 2;
      }
      if(dataline.contains("<big"))
      {
        score = score - 2;
      }
      if(dataline.contains("<strike"))
      {
        score = score - 1;
      }
      if(dataline.contains("<tt"))
      {
        score = score - 2;
      }
      if(dataline.contains("<frameset"))
      {
        score = score - 5;
      }
      if(dataline.contains("<frame"))
      {
        score = score - 5;
      }

     dataline = inputFile.readLine();//reads the next line and continues in the while loop until the end of the file
   }

    System.out.println("Score: " + score);

    //get datestamp
    DateFormat dfDay = new SimpleDateFormat("dd/MM/yy");
    Date dateobjDay = new Date();
    System.out.println("Date scored: " + dfDay.format(dateobjDay));

    //get timestamp
    DateFormat dfTime = new SimpleDateFormat("HH:mm:ss");
    Date dateobjTime = new Date();
    System.out.println("Time scored: " + dfTime.format(dateobjTime));

    /*put info in database

    //How I would have entered data into the database had I gotten the connection to work...

    //DB name is my_sadakc2_scoringProjectDB
    //table name is scoreInfo
    stmnt.executeUpdate("INSERT INTO scoringInfo " + "VALUES('dfTime', 'dfDay', 'fileName', score)");

    */

    int choice1 = 1;

    //this do-while loop allows the user to continuously choose what they would like to do with the information stored in the database
    do{

      //Menu
      System.out.println("");
      System.out.println("What would you like to do next?");
      System.out.println("1: Retrieve all scores for a particular file.");
      System.out.println("2: Retrieve all scores in the system for a custom date range.");
      System.out.println("3: Retrieve highest score for a particular file.");
      System.out.println("4: Retrieve lowest score for a particular file.");
      System.out.println("5: Exit.");
      System.out.println("");

      System.out.print("Choice: ");
      Scanner reader = new Scanner(System.in);  // Reading from System.in
      int choice2 = reader.nextInt();

      switch(choice2)//switch statement allows user to choose from a menu of options
      {
        case 1:
        {
          //takes input from the user to determine which file to retrieve scores for
          System.out.print("What file would you like to retrieve all scores for? ");
          stdin = new InputStreamReader(System.in);
          keyboard = new BufferedReader(stdin);
          fileName = keyboard.readLine();

          //SELECT * FROM scoringInfo WHERE scoreFile = fileName;
          //store each into an arrayList
          //ArrayList scoresArray = new ArrayList();
          //for(int i=0; i<scoresArray.size(); i++)
          //{
          //  System.out.println(scoresArray.get(i));
          //}

          break;
        }
        case 2:
        {
          System.out.println("You would like to get all scores for a custom date range.");

          //asks the user for the lower end of the range
          System.out.print("Please enter the low end of the range you would like to search: ");
          stdin = new InputStreamReader(System.in);
          keyboard = new BufferedReader(stdin);
          String lowDate = keyboard.readLine();

          //asks the user for the higher end of the range
          System.out.print("Please enter the high end of the range you would like to search: ");
          stdin = new InputStreamReader(System.in);
          keyboard = new BufferedReader(stdin);
          String highDate = keyboard.readLine();

          //SELECT * FROM scoringInfo WHERE scoreDay BETWEEN >= 'lowDate' AND 'highDate';
          //store each into an arrayList
          //ArrayList scoresArray = new ArrayList();
          //for(int i=0; i<scoresArray.size(); i++)
          //{
          //  System.out.println(scoresArray.get(i));
          //}

          break;
        }
        case 3:
        {
          System.out.print("Which file would you like to retrieve the highest score for? ");
          stdin = new InputStreamReader(System.in);
          keyboard = new BufferedReader(stdin);
          fileName = keyboard.readLine();

          //int result = stmnt.executeQuery("SELECT MAX(scoreNum) FROM scoringInfo WHERE scoreFile = fileName");
          //System.out.println("The highest score for " + fileName + " is " + result + ".");

          break;
        }
        case 4:
        {
          System.out.print("Which file would you like to retrieve the lowest score for? ");
          stdin = new InputStreamReader(System.in);
          keyboard = new BufferedReader(stdin);
          fileName = keyboard.readLine();

          //int result = stmnt.executeQuery("SELECT MIN(scoreNum) FROM scoringInfo WHERE scoreFile = fileName");
          //System.out.println("The lowest score for " + fileName + " is " + result + ".");

          break;
        }
        case 5:
        {
          System.out.println("Goodbye!");
          System.exit(1);
          break;
        }
        default:
        {
          break;
        }
      }

      System.out.println("Enter 1 to make another choice. Enter 2 to exit.");
      System.out.print("Choice: ");
      choice1 = reader.nextInt();

    }while(choice1 == 1);

      }//end of main
   }
