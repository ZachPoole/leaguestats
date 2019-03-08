/**
 Date: 12/1/18

 Course: CSCI 2073

 Description: This class provides a constructor for setting up a HashMap and TreeSet
   with the elements being the teams taken from the game history file. These teams are
   turned into Team objects from the Team class and then stored in the data sturctures
   listed above. This class provides a getStats method, a stats method, a best method,
   highScoringTeams method, and a rank method.

 */


import java.io.File;
import java.util.*;



/**
 * This class provides a constructor for setting up a HashMap and TreeSet
 * with the elements being the teams taken from the game history file. These teams are
 * turned into Team objects from the Team class and then stored in the data sturctures
 * listed above. This class provides a getStats method, a stats method, a best method,
 * highScoringTeams method, and a rank method. See methods for method descriptions.
 *
 *
 * @author Zach Poole
 */
public class LeagueStats
{

   // declaring data structures, teamMap will be used to store the Team objects with the
   //    key being the team name and the value being the Team object. The teamTree will
   //    will be used to keep track of the rank of the teams.

   Map<String, Team> teamMap;
   TreeSet<Team> teamTree;




   /**
    * Sets up both data structures and updates all team objects based on the results
    * of each game. The results are given in the format: Home Team, Away Team, Home
    * Team Goals, Away Team Goals.
    *
    * @param csvFile    the file containing the results of all the games.
    * @throws Exception if the csvFile is not found.
    */
   public LeagueStats(String csvFile) throws Exception
   {


      // initializing scanner and file object
      File gamesFile = new File(csvFile);
      Scanner in = new Scanner(gamesFile);


      //initializing data structures
      teamMap = new HashMap<>();
      teamTree = new TreeSet<>();


      //loop to go through each game and update each Team object attributes and add
      //    the teams to the data structures
      while (in.hasNextLine())
      {

         // puts each line into an array
         String[] gameArr = in.nextLine().split(",");


         // alternate between home team and away team
         for (int x = 0; x < 2; x++) {

            String teamName = gameArr[x];
            int teamGoalsFor;
            int teamGoalsAgainst;


            if (x == 0)
            {
               teamGoalsFor = Integer.parseInt(gameArr[2]);
               teamGoalsAgainst = Integer.parseInt(gameArr[3]);
            }
            else
            {
               teamGoalsFor = Integer.parseInt(gameArr[3]);
               teamGoalsAgainst = Integer.parseInt(gameArr[2]);
            }


            // if the team isn't in the HashMap, adds it
            if (!teamMap.containsKey(teamName)) {
               teamMap.put(teamName, new Team(teamName));
            }


            Team teamObj = teamMap.get(teamName);


            // updates Team attributes
            if (teamGoalsFor > teamGoalsAgainst) {
               teamObj.wins += 1;
               teamObj.points += 3;
            } else if (teamGoalsFor < teamGoalsAgainst) {
               teamObj.losses += 1;
            } else {
               teamObj.draws += 1;
               teamObj.points += 1;
            }


            teamObj.goalsFor += teamGoalsFor;
            teamObj.goalsAgainst += teamGoalsAgainst;

         }
      }

      // adds all the teams to the TreeSet after all the attributes have been updated
      for (String key: teamMap.keySet())
      {
         teamTree.add(teamMap.get(key));
      }

   }



   /**
    * Reads commands in from a file with a team name and performs said commands and
    * returns the output of those commands.The commands can be: STATS, BEST, RANK,
    * or HSCORING. The STATS command displays the team name, wins, draws, losses,
    * goals for, goals against, and points (3 pts. per win, 1 pt. per draw). If the
    * team does not exist, the statistics are replaced by the phrase 'NOT FOUND'. The
    * BEST command displays the same information as the STATS transaction
    * for the team with the largest number of points. If two or more teams are tied
    * with the largest number of points, the tiebreakers are:
    *    o largest goal differential (the difference between goal scored and goals allowed)
    *    o most goals scored
    * The HSCORING command displays the names of any teams that have scored more goals
    * than the BEST team (or 'NONE' if no such team exists). The RANK command displays
    * the team name and its ranking (if the team does not exist, the statistics are
    * replaced by the phrase 'NOT FOUND'). The same criteria for tie-breakers as used
    * for the BEST operation.
    * The formats for the commands are:
    *    o STATS [insert team name]
    *    o BEST
    *    o HSCORING
    *    o RANK [insert team name]
    *
    * @param statsFile  the file that has the commands.
    * @return           all the output associated with the commands.
    * @throws Exception if statsFile is not found.
    * */
   public String getStats(String statsFile) throws Exception
   {
      // initializing scanner and file objects
      File newFile = new File(statsFile);
      Scanner in = new Scanner(newFile);


      // declaring variables
      String command;
      String line;
      String teamName = "";
      int space;
      String output = "";


      // loop through file to perform each command line by line
      while (in.hasNextLine())
      {
         line = in.nextLine();
         space = line.indexOf(" ");

         // some commands don't provide a team name, so have to
         //    search for space after the commands to check
         if (space == -1)
         {
            command = line;
         }
         else
         {
            command = line.substring(0, space);
            teamName = line.substring(space + 1);
         }


         // based on command performs corresponding method
         if (command.equals("STATS"))
         {
            output += "TEAM: " + stats(teamName) + "\n";
         }
         else if (command.equals("BEST"))
         {
            output += "BEST: " + best() + "\n";
         }
         else if (command.equals("HSCORING"))
         {
            output += "HIGH SCORERS: " + highScoringTeams() + "\n";
         }
         else if(command.equals("RANK"))
         {
            output += "RANK: " + rank(teamName) + "\n";
         }

      }

      return output;
   }



   /**
    * Returns the specified teams: team name, wins, draws, losses, goals scored, goals allowed, and points.
    *
    * @param tName   name of the team to get stats for.
    * @return        all attributes of the team object; NOT FOUND if team not in data structure.
    * */
   private String stats(String tName)
   {
      if (!teamMap.containsKey(tName))
      {
         return tName + " NOT FOUND";
      }
      else
      {
         return teamMap.get(tName).toString();
      }
   }



   /**
    * Gets the team with the largest number of points and return it's: team name, wins, draws, losses, goals scored, goals allowed, and points.
    * If two or more teams are tied with the largest number of points, the tiebreakers are:
    *    o largest goal differential (the difference between goal scored and goals allowed)
    *    o most goals scored
    *
    * @return     team with most points.
    * */
   private String best()
   {
      // since TreeSet is auto sorted, grabs team at the top of the tree
      Iterator<Team> it = teamTree.iterator();
      return stats(it.next().teamName);
   }


   /**
    * Returns the teams that have more goals then the team with the most points.
    *
    * @return     teams that have more goals then the team with the most points.
    * */
   private String highScoringTeams()
   {

      String output = "";

      Iterator<Team> it = teamTree.iterator();
      int goalsToBeat = it.next().goalsFor;
      Team currentTeam;


      // loops over tree and compares each teams goals with the team at the top of the tree's goals
      while (it.hasNext())
      {
         currentTeam = it.next();

         if (currentTeam.goalsFor > goalsToBeat)
         {
            output += currentTeam.teamName + ", ";
         }
      }

      if (!output.equals(""))
      {
         int lastCommaPos = output.lastIndexOf(",");
         output = output.substring(0, lastCommaPos);
      }
      else
      {
         output += "NONE";
      }

      return output;
   }


   /**
    * Returns the rank of a team that is given.
    *
    * @param aTeamName     name of team that you want the rank of.
    * @return              rank of team that was specified.
    */
   private String rank(String aTeamName)
   {

      Iterator<Team> it = teamTree.iterator();

      for (int x = 1; x < teamTree.size() + 1; x++)
      {
         if (it.next().teamName.equals(aTeamName))
         {
            return aTeamName + " is ranked " + x + " out of " + teamTree.size();
         }
      }

      return aTeamName + " NOT FOUND";
   }





   /**
    *
    *
    *                         _,aaadP""""""Ybaaaa,,_
    *                    ,adP,__,,,aaaadP"""""Y888888a,_
    *                 ,a8888888P"''             "Y8888888b,
    *              _a888888888"                   `Y88888888b,
    *            ,d888888888P'                       "888888888b,
    *          ,88888888P"Y8,                       ,P'   `""Y888b,
    *        ,d8888P"'     "Ya,                    ,P'         `Ya`b,
    *       ,P88"'           `Ya,                 ,P'            `b`Yi
    *      d",P                `"Y,              ,P'              `Y "i
    *    ,P' P'                   "888888888888888b                `b "i
    *   ,P' d'                    d8888888888888888b                `b `b
    *   d' d'                    ,888888888888888888b                I, Y,
    *  ,f ,f                    ,88888888888888888888b               `b, b
    *  d' d'                    d888888888888888888888b              ,88,I
    * ,P  8                    ,88888888888888888888888b,_          ,d8888
    * d'  8,                   d8888888888888888888888P'`"Ya,_     ,d88888
    * 8  d88b,             ,adP""Y888888888888888888P'      `""Ya, d88888P
    * 8 ,88888b,       ,adP"'     `"Y8888888888888"'             `"888888I
    * Y,88888888b, ,adP"'             ""Y888888P"                  888888I
    * `888888888888P'                     ""YP"                    888888'
    *  I88888888888                          8                     888888
    *  `Y8888888888                          8                     88888b
    *   `Y888888888        Normand           8                     8888b
    *    `Y88888888        Veilleux          8                     8P"8
    *     `Y8888888,                         8                   ,d',d'
    *      `b""""Y8b                         8                 ,d" ,d'
    *        "b,   "Y,                       8               ,P" ,d"
    *          "b,   "Ya,_                 ,d88ba,,___   _,aP" ,P"
    *            "Ya_   ""Ya,_       _,,ad88888888888888P"' _,d"
    *              `"Ya_    ""Yaaad88888888888888888888P _,d"'
    *                  `"Ya,_     "Y888888888888888888P",d"'
    *                     `""Ya,__`Y888888888888888P"""
    *
    *
    *
    *
    *
    * source - https://www.asciiart.eu/sports-and-outdoors/soccer
    * */



   // end of class
}