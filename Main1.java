import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

// Interfaz para la Pila Genérica
interface StackADT<T> {
    void push(T item); // agregar un elemento
    T pop(); // sacar el elemento superior 
    T peek(); // ver elemento superior sin sacarlo 
    boolean isEmpty(); // ver si la pila está vacía
    int size(); // tener tamaño de pila 
}

// Implementación con Vector
class VectorStack<T> implements StackADT<T> {
    private Vector<T> stack;    

    public VectorStack() {
        stack = new Vector<>(); // vector para almacenar datos 
    }  

    public void push(T item) {
        stack.add(item); // al final agrega elemento 
    }

    public T pop() {
        if (isEmpty()) throw new IllegalStateException("La pila está vacía.");
        return stack.remove(stack.size() - 1); // elimina y devuelve último elemento 
    } 

    public T peek() {
        if (isEmpty()) throw new IllegalStateException("La pila está vacía.");
        return stack.lastElement(); // devúelve último elemento sin eliminarlo 
    } 

    public boolean isEmpty() {
        return stack.isEmpty();
    } // verifica si está vacía 

    public int size() {
        return stack.size();
    } // retorna el tamaño 
}

// Implementación con ArrayList
class ArrayListStack<T> implements StackADT<T> {
    private ArrayList<T> stack;

    public ArrayListStack() {
        stack = new ArrayList<>();
    }

    public void push(T item) {
        stack.add(item);
    }

    public T pop() {
        if (isEmpty()) throw new IllegalStateException("La pila está vacía.");
        return stack.remove(stack.size() - 1);
    }

    public T peek() {
        if (isEmpty()) throw new IllegalStateException("La pila está vacía.");
        return stack.get(stack.size() - 1);
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public int size() {
        return stack.size();
    }
}

// Interfaz para la Calculadora Postfix
interface PostfixCalculatorADT {
    int evaluate(String expression);
}

// Implementación de la Calculadora Postfix
class PostfixCalculator implements PostfixCalculatorADT {
    private StackADT<Integer> stack;

    public PostfixCalculator(StackADT<Integer> stackImplementation) {
        this.stack = stackImplementation;
    }

    public int evaluate(String expression) {
        StringTokenizer tokenizer = new StringTokenizer(expression);

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();

            if (token.matches("\\d+")) { // Es un número
                stack.push(Integer.parseInt(token));
            } else { // Es un operador
                if (stack.size() < 2) throw new IllegalArgumentException("Expresión inválida: operandos insuficientes.");
                
                int operandB = stack.pop();
                int operandA = stack.pop();
                int result = 0;

                switch (token) {
                    case "+": result = operandA + operandB; break;
                    case "-": result = operandA - operandB; break;
                    case "*": result = operandA * operandB; break;
                    case "/":
                        if (operandB == 0) throw new ArithmeticException("Error: División por cero.");
                        result = operandA / operandB;
                        break;
                    case "%": result = operandA % operandB; break;
                    default: throw new IllegalArgumentException("Operador no válido: " + token);
                }
                stack.push(result); // guardamos resultado en la pila
            }
        }
        if (stack.size() != 1) throw new IllegalArgumentException("Expresión inválida: operandos sobrantes.");
        return stack.pop(); // devolvemos resultado final 
    }
}

// Clase Principal con Interfaz de Usuario
public class Main1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StackADT<Integer> stack;

        System.out.println("Seleccione la implementación de pila:");
        System.out.println("1. VectorStack");
        System.out.println("2. ArrayListStack");

        int option = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        if (option == 1) {
            stack = new VectorStack<>();
        } else {
            stack = new ArrayListStack<>();
        }

        PostfixCalculator calculator = new PostfixCalculator(stack);

        System.out.print("Ingrese el nombre del archivo con la expresión postfix: ");
        String filename = scanner.nextLine();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String expression = reader.readLine();
            if (expression != null) {
                System.out.println("Expresión leída: " + expression);
                int result = calculator.evaluate(expression);
                System.out.println("Resultado: " + result);
            } else {
                System.out.println("El archivo está vacío.");
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        } catch (IllegalArgumentException | ArithmeticException e) {
            System.out.println("Error en la expresión: " + e.getMessage());
        }

        scanner.close();
    }
}
