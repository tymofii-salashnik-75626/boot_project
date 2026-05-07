package pl.vistula.webintro.demo;

import java.util.ArrayList;
import java.util.List;

public class HelloResponse {
    private String message;
    private String team;
    private List<Integer> studentIndexes = new ArrayList<>();

    public HelloResponse() {
    }

    public String getMessage() {
        return message;
    } 

    public String getTeam() {
        return team;
    } 

    public List<Integer> getStudentIndexes() {
        return studentIndexes;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public void setStudentIndexes(List<Integer> studentIndexes) {
        this.studentIndexes.clear();
        this.studentIndexes.addAll(studentIndexes);
    }
}
