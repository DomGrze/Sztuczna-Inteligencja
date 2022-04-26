package Zestaw3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class CSP {

    private ArrayList<String> variables;
    private ArrayList<ArrayList<String>> domains;
    private ArrayList<ArrayList<String>> constraints;

    public CSP() {
        variables = new ArrayList<>();
        domains = new ArrayList<>();
        constraints = new ArrayList<>();
    }

    public void addVariable(String var) {
        variables.add(var);
    }

    public void addDomain(ArrayList<String> domain) {
        domains.add(domain);
    }

    public void addConstraint(ArrayList<String> constraint) {
        constraints.add(constraint);
    }

    public String mrv(HashMap<String, String> assignment) {
        String [] assignedVariables = assignment.keySet().toArray(String[]::new);
        ArrayList<String> unassignedVariables = new ArrayList<>();
        for(String i : variables) {
            if(!Arrays.asList(assignedVariables).contains(i))
                unassignedVariables.add(i);
        }
        String min = unassignedVariables.get(0);

        HashMap<String, Integer> numberOfConstraints = new HashMap<>();
        for(String var : unassignedVariables) {
            int count = 0;
            for(int i = 0; i < constraints.size(); ++i) {
                for(int j = 0; j < constraints.get(i).size(); ++j) {
                    if(constraints.get(i).get(j).equals(var))
                        ++count;
                }
            }
            numberOfConstraints.put(var, count);
        }

        for(String i : unassignedVariables) {
            if(!i.equals(min)) {

            }
        }

        return min;
    }

    public ArrayList<String> orderDomainValues(String var) {
        Integer index = null;
        for(int i = 0; i < variables.size(); ++i) {
            if(variables.get(i).equals(var))
                index = i;
        }
        if(!index.equals(null)) {
            return domains.get(index);
        }
        return null;
    }

    public boolean isConsistent(HashMap<String, String> assignment, String var, String value) {
        for(ArrayList<String> constraint : constraints) {
            if(constraint.get(0).equals("notEqual")) {
                String firstVar = constraint.get(1);
                String secondVar = constraint.get(2);
                if(firstVar.equals(var)) {
                    if(value.equals(assignment.get(secondVar)))
                        return false;
                }
                if(secondVar.equals(var)) {
                    if(value.equals(assignment.get(firstVar)))
                        return false;
                }
            }
        }
        return true;
    }

    public String variablesToString() {
        String output = "{";
        int length = variables.size();;
        for(int i = 0; i < length; ++i) {
            output += variables.get(i);
            if(i != length-1)
                output += ", ";
        }
        output += "}";
        return output;
    }

    public String domainsToString() {
        String output = "";
        int length = domains.size();
        if(length == 0)
            return output;
        output += "D = {";
        for(int i = 0; i < length; ++i) {
            output += "D"+(i+1);
            if(i != length-1)
                output += ", ";
        }
        output += "}\n";

        for(int i = 0; i < length; ++i) {
            output += "D"+(i+1)+" = {";
            int lengthOfDomain = domains.get(i).size();
            for(int j = 0; j < lengthOfDomain; ++j) {
                output += domains.get(i).get(j);
                if(j != lengthOfDomain-1)
                    output += ", ";
            }
            output += "}\n";
        }
        return output;
    }

    public String constraintsToString() {
        String output = "";
        int length = constraints.size();
        if(length == 0)
            return output;
        output += "C = {";
        for(int i = 0; i < length; ++i) {
            if(i != 0)
                output += "     ";
            int lengthOfConstraint = constraints.get(i).size();
            output += constraints.get(i).get(0)+"(";
            for(int j = 1; j < lengthOfConstraint; ++j) {
                output += constraints.get(i).get(j);
                if(j != lengthOfConstraint-1)
                    output += ", ";
            }
            if(i != length-1)
                output += "),\n";
        }
        output += ")}\n";
        return output;
    }
}
