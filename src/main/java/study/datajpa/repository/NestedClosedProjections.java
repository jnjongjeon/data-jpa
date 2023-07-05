package study.datajpa.repository;

public interface NestedClosedProjections {

    String getUsername();

    int getAge();

    TeamInfo getTeam();

    interface TeamInfo {
        String getName();
    }

}
