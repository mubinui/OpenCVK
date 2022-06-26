import java.util.Random;

public class RandomGenerator {
    public static void main(String[] args) {
        Random r = new Random();

        for (int i = 0; i <10 ; i++) {
            double x = r.nextDouble(-15.00,-10.00);
            double y = r.nextDouble(10.00,15.00);
            System.out.println(x+" "+y);

        }
    }
}
