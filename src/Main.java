import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.io.IOException;
import com.google.gson.Gson;

public class Main {
    public static void main(String args[]) throws FileNotFoundException {
        Scanner scan = new Scanner(System.in);

        // 2 - FIBONACCI
        // System.out.println("Insert a number to check if it is in Fibonacci sequence:
                // ");
                // int number = scan.nextInt();

        // 3 - MONTH PROFIT DATA
        ArrayList<Day> month = new ArrayList<>();
        month = getJsonData();
        getMonthSummary(month);

        // 4 - STATE PERCENTUAL MONTH PROFIT
        int op = 0;
        ArrayList<State> states = new ArrayList<>();

        while(op == 0) {
            System.out.println("Want to insert data manually? YES - 1 | NO - 0");
            int op2 = scan.nextInt();
            System.out.println(op2);
            if(op2 == 0) break;

            System.out.println("Insert the state name: ");
            String name = scan.next();
            System.out.println("Insert the state profit: ");
            Double profit = scan.nextDouble();

            State newState = new State();
            newState.setName(name);
            newState.setProfit(profit);
            states.add(newState);

            System.out.println("Do you want to stop it? YES - 1 | NO - 0 ");
            op = scan.nextInt();
        }

        states = getStatesData();
        getPercents(states);

        // 5 - REVERSE STRING
        System.out.println("Insert a string to reverse it: ");
        String str = scan.next();
        System.out.println(reverseString(str));
    }

    public static void getMonthSummary(ArrayList<Day> month) {
        Day largestProfitDay = new Day();
        Day smallestProfitDay = new Day();
        int qtBelowAverageDays = 0;
        int qtAboveAverageDays = 0;
        double totalProfit = 0.0;

        for(int i = 0; i < month.size(); i++){
            Day currentDay = month.get(i);
            if (i == 0) {
                largestProfitDay = currentDay;
                smallestProfitDay = currentDay;
            }

            if (currentDay.getValor() > largestProfitDay.getValor()) largestProfitDay = currentDay;
            if (currentDay.getValor() < smallestProfitDay.getValor()) smallestProfitDay = currentDay;

            totalProfit += currentDay.valor;
        }

        double averageProfitPerDay = getDailyProfitAverage(totalProfit, month.size());

        for (Day day : month) {
            if (day.valor > averageProfitPerDay) qtAboveAverageDays++;
            if (day.valor < averageProfitPerDay) qtBelowAverageDays++;
        }


        System.out.println(
            "--- MONTH SUMMARY ---\n" +
            "\nSMALLEST PROFIT DAY - R$ " + smallestProfitDay.getValor() + " ON DAY " + smallestProfitDay.getDia() +
            "\nLARGEST PROFIT DAY - R$ " + largestProfitDay.getValor() + " ON DAY " + largestProfitDay.getDia()  +
            "\nN° DAYS ABOVE AVERAGE - " + qtAboveAverageDays  +
            "\nN° DAYS BELOW AVERAGE - " + qtBelowAverageDays  +
            "\nAVERAGE PROFIT / DAY - R$ " + averageProfitPerDay  +
            "\nTOTAL PROFIT - R$ " + totalProfit + "\n\n"
        );
    }

    public static double getDailyProfitAverage(double totalProfit, int totalDays) {
        return totalProfit / totalDays;
    }

    public static void getPercents(ArrayList<State> states) {
        Double totalProfit = (double) 0;

        for (State state : states) {
            totalProfit += state.getProfit();
        }

        for (State state : states) {
            Double percentage = (state.getProfit() / totalProfit) * 100;
            System.out.printf("State %s - R$ %f - %f of total profits %n", state.getName(), state.getProfit(), percentage);
        }
        System.out.printf("TOTAL STATES PROFIT - R$ %f \n\n", totalProfit);
    }

    public static String reverseString(String str) {

        StringBuilder newStr = new StringBuilder();

        for (int i = str.length() - 1; i >= 0; i--) {
            char c = str.charAt(i);
            newStr.append(c);
        }

        return newStr.toString();
    }

    public static ArrayList<State> getStatesData() {
        int nTimes = 10;
        ArrayList<State> states = new ArrayList<>();
        Random random = new Random();

        String[] statesName = {"AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA",
                "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN",
                "RS", "RO", "RR", "SC", "SP", "SE", "TO"};

        for(int i = 0; i < nTimes; i++) {
            State newState = new State();
            int stateIndex = random.nextInt(statesName.length);
            double profit = random.nextDouble(100000);
            newState.setName(statesName[stateIndex]);
            newState.setProfit(profit);
            states.add(newState);
        }

        return states;
    }

    public static ArrayList<Day> getJsonData() throws FileNotFoundException {
        String path = "/home/lucas/Documents/projects/java/exercises/teste-estagio-target/src/dados.json";
            ArrayList<Day> month = new ArrayList<>();

            Gson gson = new Gson();
            FileReader reader = new FileReader(path);
            Day[] monthData = gson.fromJson(reader, Day[].class);

            for (Day day : monthData) {
                Day newDay = new Day();
                newDay.setDia(day.getDia());
                newDay.setValor(day.getValor());
                month.add(newDay);
            }

        return month;
    }
}

