import java.util.*;

public class Main {
    public static void main(String[] args){
        Odkurzacz o1 = new Odkurzacz(10, 5);

        o1.wypiszAkcje();
    }
}

class Odkurzacz{
    private class StanOdkurzacza{
        StanOdkurzacza rodzic;
        boolean[] lokalizacja;
        boolean[] czyBrudne;
        String akcja;

        StanOdkurzacza(StanOdkurzacza rodzic, int pozycja, boolean[] stanPokoi, String akcja){
            this.rodzic = rodzic;
            lokalizacja = new boolean[liczbaPokoi];
            czyBrudne = stanPokoi;
            this.akcja = akcja;

            Arrays.fill(lokalizacja, false);

            lokalizacja[pozycja] = true;
        }

        StanOdkurzacza(int pozycja){
            rodzic = null;
            lokalizacja = new boolean[liczbaPokoi];
            czyBrudne = new boolean[liczbaPokoi];
            akcja = null;

            Arrays.fill(lokalizacja, false);
            Arrays.fill(czyBrudne, true);

            lokalizacja[pozycja] = true;
        }
    }

    private int liczbaPokoi;
    StanOdkurzacza poczatek;
    List<StanOdkurzacza> odwiedzone;
    List<String> akcje;

    public void wypiszAkcje(){
        if(akcje.isEmpty()) System.out.println("Nie udalo sie wyczyscic wszystkich pokoi");
        else{
            for(String akcja: akcje){
                System.out.println(akcja);
            }
        }
    }

    private StanOdkurzacza idzLewo(StanOdkurzacza wezel){
        int i = 0;
        while(wezel.lokalizacja[i] == false) i++;
        i--;
        StanOdkurzacza nowyWezel = new StanOdkurzacza(wezel, i, wezel.czyBrudne, "Przejscie w lewo do pokoju "+i);
        return nowyWezel;
    }

    private StanOdkurzacza idzPrawo(StanOdkurzacza wezel){
        int i = 0;
        while(wezel.lokalizacja[i] == false) i++;
        i++;
        StanOdkurzacza nowyWezel = new StanOdkurzacza(wezel, i, wezel.czyBrudne, "Przejscie w prawo do pokoju "+i);
        return nowyWezel;
    }

    private StanOdkurzacza czysc(StanOdkurzacza wezel){
        int i = 0;
        while(wezel.lokalizacja[i] == false) i++;
        boolean[] kopia = Arrays.copyOf(wezel.czyBrudne, liczbaPokoi);
        kopia[i] = false;
        StanOdkurzacza nowyWezel =new StanOdkurzacza(wezel, i, kopia, "Pokoj "+i+": Czyszczenie");
        return nowyWezel;
    }

    private void BFS(){
        Queue<StanOdkurzacza> Q = new LinkedList<>();
        StanOdkurzacza wezel;

        Q.add(poczatek);

        while(!Q.isEmpty() && !czyCzysto(Q.peek().czyBrudne)){
            wezel = Q.remove();

            if(wezel.lokalizacja[0] == false){
                StanOdkurzacza nowyWezel = idzLewo(wezel);
                if(!czySiePowtarza(nowyWezel)){
                    odwiedzone.add(nowyWezel);
                    Q.add(nowyWezel);
                }
            }
            if(wezel.lokalizacja[liczbaPokoi-1] == false){
                StanOdkurzacza nowyWezel = idzPrawo(wezel);
                if(!czySiePowtarza(nowyWezel)){
                    odwiedzone.add(nowyWezel);
                    Q.add(nowyWezel);
                }
            }
            if(wezel.czyBrudne[nrPokojuZOdkurzaczem(wezel.lokalizacja)]){
                StanOdkurzacza nowyWezel = czysc(wezel);
                if(!czySiePowtarza(nowyWezel)){
                    odwiedzone.add(nowyWezel);
                    Q.add(nowyWezel);
                }
            }
        }
        akcje = new ArrayList<>();
        if(!Q.isEmpty()){
            wezel = Q.remove();

            while(wezel != poczatek){
                akcje.add(0, wezel.akcja);
                wezel=wezel.rodzic;
            }
        }
    }

    private boolean czyCzysto(boolean[] pokoje){
        for(boolean pokoj: pokoje)
            if(pokoj == true) return false;
        return true;
    }

    private int nrPokojuZOdkurzaczem(boolean[] pokoje){
        for(int i = 0; i < pokoje.length; i++)
            if(pokoje[i] == true) return i;
        return 1001;
    }

    private boolean czySiePowtarza(StanOdkurzacza wezel){
        for(StanOdkurzacza odwiedzonyWezel: odwiedzone)
            if(porownaj(wezel.lokalizacja, odwiedzonyWezel.lokalizacja) && porownaj(wezel.czyBrudne, odwiedzonyWezel.czyBrudne))
                return true;
        return false;
    }

    private boolean porownaj(boolean[] a, boolean[] b){
        if(a.length != b.length) return false;
        for(int i = 0; i < a.length; i++)
            if(a[i] != b[i]) return false;
        return true;
    }

    Odkurzacz(int liczbaPokoi, int start){
        this.liczbaPokoi = liczbaPokoi;
        odwiedzone = new ArrayList<>();

        poczatek = new StanOdkurzacza(start);
        odwiedzone.add(poczatek);

        BFS();
    }
}