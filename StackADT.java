// Interfaz para la Pila Genérica
interface StackADT<T> {
    void push(T item); // agregar un elemento
    T pop(); // sacar el elemento superior 
    T peek(); // ver elemento superior sin sacarlo 
    boolean isEmpty(); // ver si la pila está vacía
    int size(); // tener tamaño de pila 
}