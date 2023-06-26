import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static int inp1;
    static int inp2;
    static char op;
    static int result;

    public static void main(String[] args) throws Exception {
        System.out.println("Введите выражение...");
        String userInput = sc.nextLine();
       // sc.next();
        //userInput=userInput.trim();
        char[] inp_char = new char[100];

        for (int i = 0; i < userInput.length(); i++) {

            inp_char[i] = userInput.charAt(i);

            if (inp_char[i] == '+') {
                op = '+';
            }
            if (inp_char[i] == '-') {
                op = '-';
            }
            if (inp_char[i] == '*') {
                op = '*';
            }
            if (inp_char[i] == '/') {
                op = '/';
            }

        }
        String inputStr0 = String.valueOf(userInput);
        String[] inputStr = inputStr0.split("[-+/*]");

        if (inputStr.length >= 3) {
            throw new Exception("Вычисление ограничено двумя аргументами.");
        }
        if (inputStr.length < 2) {
            throw new Exception("Не является математическим выражением");
        }
        String operand1 = inputStr[0];
        String operand2 = inputStr[1];
        operand2 = operand2.trim();
        operand1 = operand1.trim();
        operand2 = operand2.toUpperCase();
        operand1 = operand1.toUpperCase();


        if (isNumeric(operand1)&& isNumeric(operand2)) {

                    inp1 = Integer.parseInt(operand1);
                    inp2 = Integer.parseInt(operand2);
                    if (inp1 > 10 || inp2 > 10) throw new Exception("Мне запретили оперировать числами больше 10 :( ");
                    result = calculated(inp1, inp2, op);
                    System.out.println(result);
        }

        else {
            if (isNumeric(operand1))
            {
                inp2 = romanToArabic(operand2);
                throw new Exception("Используются одновременно разные системы счисления, я отказываюсь считать такое!");
            }
            if (isNumeric(operand2))
            {
                inp2 = romanToArabic(operand1);
                throw new Exception("Используются одновременно разные системы счисления, я отказываюсь считать такое!");
            }
            inp1 = romanToArabic(operand1);
            inp2 = romanToArabic(operand2);
            if (inp1 > 10 || inp2 > 10) throw new Exception("Мне запретили,оперировать числами больше X :( ");

            result = calculated(inp1, inp2, op);
            String resultRoman;
            try {
                 resultRoman = arabicToRoman(result);
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new ArrayIndexOutOfBoundsException("Результат вычисления римскими цифрами не может быть отрицательным.");
            }
            System.out.println(resultRoman);
        }
    }


    public static int calculated ( int num1, int num2, char op){
        int result = 0;
        switch (op) {
            case '+' -> result = num1 + num2;
            case '-' -> result = num1 - num2;
            case '*' -> result = num1 * num2;
            case '/' -> {
                try {
                    result = num1 / num2;
                } catch (ArithmeticException e) {

                    throw new ArithmeticException("Деление на ноль, Seriously?? 0_o ");
                }
            }
        }
        return result;
    }

    public static boolean isNumeric (String strNum){
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }


    public static String arabicToRoman(final int number) {
        if (number <=0 || 4000 <= number) {
            throw new IllegalArgumentException("Римское число не может быть меньше или равно нулю,(И больше 3999, там ад, можно я не буду это считать, раз сами римляне не охотно за это брались?)");
        }
        String[] nums = { "I", "V", "X", "L", "C", "D", "M" };
        int numCounter = 0;
        String result = "";
        StringBuilder partResult = new StringBuilder();
        int numeral;
        String stringNumber = String.valueOf(number);
        for (int i = stringNumber.length() - 1; i >= 0; i--) {
            partResult.delete(0, partResult.length());
            numeral = Integer.parseInt(stringNumber.substring(i, i + 1));
            if (1 <= numeral && numeral < 4) {
                for (int j = 0; j < numeral; j++) {
                    partResult.append(nums[numCounter]);
                }
                numCounter += 2;
            } else if (4 <= numeral && numeral < 9) {
                numCounter += 2;
                if (numeral == 4) {
                    partResult.append(nums[numCounter - 2]);
                }
                partResult.append(nums[numCounter - 1]);
                for (int j = 0; j < (numeral - 5); j++) {
                    partResult.append(nums[numCounter - 2]);
                }
            } else if (numeral == 9) {
                numCounter += 2;
                partResult.append(nums[numCounter - 2] + nums[numCounter]);
            } else if (numeral == 0) {
                numCounter += 2;
            }
            result = partResult.append(result).toString();
        }
        return result;
    }
    public static int romanToArabic(final String number) {
        int sum = 0;
        for (int i = 0; i < number.length(); i++) {
            switch (number.charAt(i)) {
                case 'I':
                    sum++;
                    break;
                case 'V':
                    if (sum == 0 || number.charAt(i-1) != 'I') {
                        sum += 5;
                    } else {
                        sum += 3; // IV - I from previous step
                    }
                    break;
                case 'X':
                    if (sum == 0 || number.charAt(i-1) != 'I') {
                        sum += 10;
                    } else {
                        sum += 8;
                    }
                    break;
                case 'L':
                    if (sum == 0 || number.charAt(i-1) != 'X') {
                        sum += 50;
                    } else {
                        sum += 30;
                    }
                    break;
                case 'C':
                    if (sum == 0 || number.charAt(i-1) != 'X') {
                        sum += 100;
                    } else {
                        sum += 80;
                    }
                    break;
                case 'D':
                    if (sum == 0 || number.charAt(i-1) != 'C') {
                        sum += 500;
                    } else {
                        sum += 300;
                    }
                    break;
                case 'M':
                    if (sum == 0 || number.charAt(i-1) != 'C') {
                        sum += 1000;
                    } else {
                        sum += 800;
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Введенное выражение не является корректным.");
            }
        }
        return sum;
    }
}


