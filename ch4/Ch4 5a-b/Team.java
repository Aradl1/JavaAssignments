//Austin Radloff
//p. 157

public class Team {
    private String highSchoolName;
    private String sport;
    private String teamName;

    public final static String MOTTO = "Sportsmanship!";

    public Team(String school, String sp, String name) {
        highSchoolName = school;
        sport = sp;
        teamName = name;
    }

    public String getHighSchoolName() {
        return highSchoolName;
    }

    public String getSport() {
        return sport;
    }

    public String getTeamName() {
        return teamName;
    }
}