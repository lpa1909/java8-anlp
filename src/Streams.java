import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Streams {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
        List<String> fillteredNames = names.stream().filter(name -> name.startsWith("C")).collect(Collectors.toList());
        System.out.println(fillteredNames);
    }
}
