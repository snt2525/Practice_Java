import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

interface Predicate {
    boolean isVaildNumber(Integer number);
}

class Pair {
    int average;
    int standardDeviation;
    Pair(int average, int standardDeviation) {
        this.average = average;
        this.standardDeviation = standardDeviation;
    }
}
public class Main {

    public static void main(String[] args) {
	// TODO Auto-generated method stub
        List<List<Integer>> list = new ArrayList<>();

        for(int i = 0;i < 10;i++) {
            List<Integer> random = new ArrayList<>();		
            for(int j = 0;j < 10;j++) {
                int randomNumber = (int)(Math.random() * 100) + 1;
                random.add(randomNumber);
            }	
            list.add(i, random);
        }

        Predicate predicate = new Predicate() {
            @Override
            public boolean isVaildNumber(Integer number) {
                return number < 50;
            }
        };

        Predicate predicat2 = number -> number < 50;
        
        List<List<Integer>> result = list.stream()
                .map(x -> {
                   return x.stream()
                            .filter(predicate::isVaildNumber)
                            .collect(toList());
                })
                .collect(toList());

        
        List<Integer> result2 = list.stream()
                .map(x -> {
                    return x.stream()
                            .collect(Collectors.summingInt(Integer::intValue));
                })
                .collect(toList());

        
        List<List<Double>> result3 = list.stream()
                .map(each -> {
                    double avg = each.stream().mapToInt(i -> i).average().getAsDouble();
                    double stddev = Math.sqrt(each.stream().mapToInt(i -> i * i).average().getAsDouble() - avg * avg);
                    
                    return Stream.of(avg, stddev).collect(toList());
                })
                .collect(toList());

        System.out.println(result.toString());
        System.out.println(result2.toString());

    }
}
