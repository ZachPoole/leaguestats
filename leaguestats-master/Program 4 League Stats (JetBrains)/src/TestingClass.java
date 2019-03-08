import java.io.File;
import java.util.*;

public class TestingClass
{
   public static void main (String[] args) throws Exception
   {
      LeagueStats games24 = new LeagueStats("/Users/zachpoole/Desktop/Program 4 League Stats (JetBrains)/src/games1-24.csv");

      System.out.println(games24.getStats("/Users/zachpoole/Desktop/Program 4 League Stats (JetBrains)/src/trans.txt"));

   }
}