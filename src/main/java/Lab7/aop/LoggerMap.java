package Lab7.aop;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class LoggerMap {
    private final Map<String, Long> executionMap = new HashMap<>();

    private static <K, V extends Comparable<? super V>> Map<K, V> sortByValue (Map<K, V> map) {
        return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    public static<K, V> List<Map.Entry<K, V>> convertToList(Map<K, V> map) {
        return new ArrayList<>(map.entrySet());
    }

    public void add(String className, Long time) {
        executionMap.put(className, time);
    }

    public void get() {
        var CSM = convertToList(sortByValue(executionMap)); // CSM - Converted Sorted Map

        if (CSM.isEmpty()) {
            System.out.println("No methods used");
        }
        for (Map.Entry<String, Long> listValue: CSM) {
            System.out.println(listValue);
        }
    }
}
