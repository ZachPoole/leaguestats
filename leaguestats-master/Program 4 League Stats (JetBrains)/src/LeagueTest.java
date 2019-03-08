public class LeagueTest
/**
   Program to test the basic functionality of the LeagueStats class.
*/
{
   public static void main(String [] args) throws Exception
	{
      LeagueStats league = new LeagueStats("games1-24.csv");
      String results = league.getStats("trans.txt");
      System.out.println(results);
	}
}