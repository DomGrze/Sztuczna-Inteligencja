//Dominik Grzeszczyk 158794 grupa 2
import java.lang.Math;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        boolean [][] operatoryLogiczne = generujOperatory(3);
        String [] nazwyOperatorowLogicznych = {"p", "q", "r"};
        String S = "¬(¬(p ∨ q) ∧ ¬r)";
        rysujZadanie1(operatoryLogiczne, nazwyOperatorowLogicznych, S);
    }

    public static Boolean prawda(String S, HashMap<String, Integer> m) {
        if(S.contains("(")) {
            while (S.contains("(")) {
                int indexNawiasu = S.indexOf(")");
                Integer indexOtwartegoNawiasu = null;
                for (int i = indexNawiasu; i >= 0 && indexOtwartegoNawiasu == null; --i)
                    if (("" + S.charAt(i)).equals("(")) {
                        indexOtwartegoNawiasu = i;
                    }
                String stringBezNawiasu = S.substring(indexOtwartegoNawiasu+1, indexNawiasu);
                if(prawdaBezNawiasu(stringBezNawiasu, m))
                    S = S.substring(0, indexOtwartegoNawiasu)+"1"+S.substring(indexNawiasu+1);
                else
                    S = S.substring(0, indexOtwartegoNawiasu)+"0"+S.substring(indexNawiasu+1);
            }
        }
        return prawdaBezNawiasu(S, m);
    }

    public static Boolean prawdaBezNawiasu(String S, HashMap<String, Integer> m) {
        String [] operatoryBinarne = {"∧", "∨", "⇒", "⇔"};
        S = S.replace(" ", "");
        for(String i : m.keySet()) {
            S = S.replace(i, m.get(i).toString());
        }
        while(S.contains("¬")) {
            int index = S.indexOf("¬");
            String wartosc = ""+S.charAt(index+1);
            if(wartosc.equals("1"))
                S = S.substring(0,index)+"0"+S.substring(index+2);
            else
                S = S.substring(0,index)+"1"+S.substring(index+2);
        }
        for(String operator : operatoryBinarne) {
            while (S.contains(operator)) {
                int index = S.indexOf(operator);
                String argument1 = "" + S.charAt(index-1);
                String argument2 = "" + S.charAt(index+1);
                if(operator.equals("∧")) {
                    if(argument1.equals("1") && argument2.equals("1"))
                        S = S.substring(0, index-1)+"1"+S.substring(index+2);
                    else
                        S = S.substring(0, index-1)+"0"+S.substring(index+2);
                }
                if(operator.equals("∨")) {
                    if(argument1.equals("0") && argument2.equals("0"))
                        S = S.substring(0, index-1)+"0"+S.substring(index+2);
                    else
                        S = S.substring(0, index-1)+"1"+S.substring(index+2);
                }
                if(operator.equals("⇒")) {
                    if(argument1.equals("1") && argument2.equals("0"))
                        S = S.substring(0, index-1)+"0"+S.substring(index+2);
                    else
                        S = S.substring(0, index-1)+"1"+S.substring(index+2);
                }
                if(operator.equals("⇔")) {
                    if(argument1.equals(argument2))
                        S = S.substring(0, index-1)+"1"+S.substring(index+2);
                    else
                        S = S.substring(0, index-1)+"0"+S.substring(index+2);
                }
            }
        }
        return S.equals("1");
    }

    public static void rysujZadanie1 (boolean [][] operatoryLogiczne, String [] nazwyOperatorow, String S) {
        for(String i : nazwyOperatorow)
            System.out.print(" "+i+" │");
        System.out.println(" "+S);
        for (boolean[] booleans : operatoryLogiczne) {
            HashMap<String, Integer> m = new HashMap<>();
            for (int j = 0; j < operatoryLogiczne[0].length; ++j) {
                if (booleans[j]) {
                    System.out.print(" 1 │");
                    m.put(nazwyOperatorow[j], 1);
                } else {
                    System.out.print(" 0 │");
                    m.put(nazwyOperatorow[j], 0);
                }
            }
            for (int j = 0; j < S.length() / 2; ++j)
                System.out.print(" ");
            if (prawda(S, m))
                System.out.println(1);
            else
                System.out.println(0);
        }
    }

    public static boolean [][] generujOperatory(int liczbaZmiennych) {
        int liczbaZadan = (int) Math.pow(2, liczbaZmiennych);
        boolean [][] array = new boolean[liczbaZadan][liczbaZmiennych];

        for(int i = liczbaZmiennych-1; i >= 0; --i) {
            int temp = ((int) Math.pow(2, liczbaZmiennych-i));
            for(int j = 0; j < liczbaZadan; ++j) {
                array[j][i] = (j % temp) >= (temp / 2);
            }
        }
        return array;
    }
}
