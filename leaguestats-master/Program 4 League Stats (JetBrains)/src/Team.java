/**
 Date: 12/1/18

 Course: CSCI 2073

 Description: This class is to create user defined object for the data
    regarding the LeagueStats class. This class provides similar attributes
    to each of the Teams read in from the CSV file of the games of each
    soccer season. This class also provides an overridden toString method,
    an overridden compareTo method, as well as a constructor.

 */



/**
 * This class is to create user defined object for the data
 * regarding the LeagueStats class. This class provides similar attributes
 * to each of the Teams read in from the CSV file of the games of each
 * soccer season. This class also provides an overridden toString method,
 * an overridden compareTo method, as well as a constructor. This class
 * implements the Comparable interface to make sure that the Team objects
 * are comparable.
 * */
public class Team implements Comparable<Team>
{
    // declaring variables
    String teamName;
    int wins;
    int draws;
    int losses;
    int goalsFor;
    int goalsAgainst;
    int points;


    /**
     * Initializes all attributes of team when first added to HashMap in LeagueStats class.
     *
     * @param teamName      name of team being constructed.
     * */
    public Team(String teamName)
    {
        this.teamName = teamName;
        wins = 0;
        draws = 0;
        losses = 0;
        goalsFor = 0;
        goalsAgainst = 0;
        points = 0;
    }



    /**
     * Returns teamName, wins, draws, losses, goalsFor, goalsAgainst, and points of the team calling the method.
     *
     * @return      returns teamName, wins, draws, losses, goalsFor, goalsAgainst, and points of the team calling the method.
     * */
    @Override
    public String toString()
    {
        return String.format("%-18s W: %-2d D: %-2d L: %-2d GF: %-2d GA: %-2d PTS: %-2d",
                teamName, wins, draws, losses, goalsFor, goalsAgainst, points);
    }



    /**
     * Provides a way for Team objects to be compared so that the TreeSet knows where to put each object in the tree.
     *
     * @param aTeam     team object that is being compared with.
     * @return          number that defines whether one Team object is greater then the specificed Team object.
     * */
    @Override
    public int compareTo(Team aTeam)
    {
        int output;

        if (teamName.equals(aTeam.teamName))
        {
            output = 0;
        }
        else if (points > aTeam.points)
        {
            output = -1;
        }
        else if (points < aTeam.points)
        {
            output = 1;
        }
        else
        {
            if ((goalsFor - goalsAgainst) > (aTeam.goalsFor - aTeam.goalsAgainst))
            {
                output = -1;
            }
            else if ((goalsFor - goalsAgainst) < (aTeam.goalsFor - aTeam.goalsAgainst))
            {
                output = 1;
            }
            else
            {
                if (goalsFor > aTeam.goalsFor)
                {
                    output = -1;
                }
                else if (goalsFor < aTeam.goalsFor)
                {
                    output = 1;
                }
                else
                {
                    output = teamName.compareTo(aTeam.teamName);
                }
            }
        }

        return output;
    }
}

