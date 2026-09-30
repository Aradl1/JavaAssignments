//Austin Radloff
//p. 157

import java.util.*;

public class TestTeam {
    public static void main(String[] args) {
        Team team1;
        Team team2;
        Team team3;

        team1 = setTeamData();
        team2 = setTeamData();
        team3 = setTeamData();

        displayTeam(team1);
        displayTeam(team2);
        displayTeam(team3);
    }

    public static Team setTeamData() {
        String school;
        String sport;
        String teamName;

        Scanner input = new Scanner(System.in);

        System.out.print("Enter high school name >> ");
        school = input.nextLine();

        System.out.print("Enter sport >> ");
        sport = input.nextLine();

        System.out.print("Enter team name >> ");
        teamName = input.nextLine();

        Team tempTeam = new Team(school, sport, teamName);

        return tempTeam;
    }

    public static void displayTeam(Team team) {
        System.out.println();
        System.out.println("High school: " +
                team.getHighSchoolName());

        System.out.println("Sport: " +
                team.getSport());

        System.out.println("Team name: " +
                team.getTeamName());

        System.out.println("Motto: " + Team.MOTTO);
    }
}