import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.*;
import java.util.stream.Collectors;

public class ParallelArrayProcessor {

    private static final Logger logger = Logger.getLogger(ParallelArrayProcessor.class.getName());

    static {
        try {
            LogManager.getLogManager().readConfiguration();
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }
    }

    public static void main(String[] args) {
        String file1 = "array1.txt";
        String file2 = "array2.txt";
        String file3 = "array3.txt";

        generateAndSaveArray(file1);
        generateAndSaveArray(file2);
        generateAndSaveArray(file3);

        ExecutorService executor = Executors.newFixedThreadPool(3);
        try {
            Future<List<Integer>> array1Future = executor.submit(() -> processFirstArray(file1));
            Future<List<Integer>> array2Future = executor.submit(() -> processSecondArray(file2));
            Future<List<Integer>> array3Future = executor.submit(() -> processThirdArray(file3));

            List<Integer> array1 = array1Future.get();
            List<Integer> array2 = array2Future.get();
            List<Integer> array3 = array3Future.get();

            List<Integer> mergedResult = mergeAndFilterArrays(array1, array2, array3);
            logger.info("Фінальний з'єднаний массив: " + mergedResult);
        } catch (InterruptedException | ExecutionException e) {
            logger.severe("Отримали помилку: " + e.getMessage());
        } finally {
            executor.shutdown();
        }
    }

    private static void generateAndSaveArray(String fileName) {
        Random random = new Random();
        int size = 15 + random.nextInt(11); // [15; 25]
        List<Integer> numbers = random.ints(size, 0, 1001).boxed().collect(Collectors.toList());
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName))) {
            writer.write(numbers.stream().map(String::valueOf).collect(Collectors.joining(",")));
            logger.info("Массив був збережений до " + fileName + ": " + numbers);
        } catch (IOException e) {
            logger.severe("Помилка збереження " + fileName + ": " + e.getMessage());
        }
    }

    private static List<Integer> readArrayFromFile(String fileName) {
        try {
            return Arrays.stream(Files.readString(Paths.get(fileName)).split(","))
                         .map(Integer::parseInt)
                         .collect(Collectors.toList());
        } catch (IOException e) {
            logger.severe("Помилка зчитування файлу " + fileName + ": " + e.getMessage());
            return Collections.emptyList();
        }
    }

    private static List<Integer> processFirstArray(String fileName) {
        List<Integer> numbers = readArrayFromFile(fileName);
        return numbers.stream().filter(n -> n % 2 != 0).sorted().toList();
    }

    private static List<Integer> processSecondArray(String fileName) {
        List<Integer> numbers = readArrayFromFile(fileName);
        return numbers.stream().map(n -> n / 3).sorted().toList();
    }

    private static List<Integer> processThirdArray(String fileName) {
        List<Integer> numbers = readArrayFromFile(fileName);
        return numbers.stream().filter(n -> n >= 50 && n <= 250).sorted().toList();
    }

    private static List<Integer> mergeAndFilterArrays(List<Integer> array1, List<Integer> array2, List<Integer> array3) {
        Set<Integer> exclusionSet = new HashSet<>(array3);
        List<Integer> result = new ArrayList<>(array1);
        result.addAll(array2);
        result.removeIf(exclusionSet::contains);
        Collections.sort(result);
        return result;
    }
}
