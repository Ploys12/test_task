import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение:");
        String input = scanner.nextLine();

        try {
            String result = calculate(input);
            System.out.println(result);
        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }

    public static String calculate(String input) throws Exception {
        String regex = "\"([^\"]{1,10})\"\\s*([+\\-*/])\\s*(\"([^\"]{1,10})\"|\\d{1,2})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (!matcher.matches()) {
            throw new Exception("Неправильный формат выражения.");
        }

        String str1 = matcher.group(1);
        String operation = matcher.group(2);
        String strOrNum = matcher.group(3);

        String result = "";

        switch (operation) {
            case "+":
                if (strOrNum.startsWith("\"")) {
                    String str2 = matcher.group(4);
                    result = str1 + str2;
                } else {
                    throw new Exception("Операция сложения возможна только между двумя строками.");
                }
                break;

            case "-":
                if (strOrNum.startsWith("\"")) {
                    String str2 = matcher.group(4);
                    result = str1.replace(str2, "");
                } else {
                    throw new Exception("Операция вычитания возможна только между двумя строками.");
                }
                break;

            case "*":
                int multiplier = Integer.parseInt(strOrNum);
                if (multiplier < 1 || multiplier > 10) {
                    throw new Exception("Число должно быть в диапазоне от 1 до 10.");
                }
                result = str1.repeat(multiplier);
                break;

            case "/":
                int divisor = Integer.parseInt(strOrNum);
                if (divisor < 1 || divisor > 10) {
                    throw new Exception("Число должно быть в диапазоне от 1 до 10.");
                }
                int newLength = str1.length() / divisor;
                result = str1.substring(0, newLength);
                break;

            default:
                throw new Exception("Неподдерживаемая операция.");
        }

        if (result.length() > 40) {
            result = result.substring(0, 40) + "...";
        }

        return "\"" + result + "\"";
    }
}

