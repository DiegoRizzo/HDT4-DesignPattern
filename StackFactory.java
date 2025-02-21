public class StackFactory<T> {
    //selecciona la implementacion a utilizar para un stack
    //se utiliza el patron Factory
    public Stack<T> getStack(String entry) {
        // seleccion de la implementacion a utilizar:
        if (entry.equals("AL"))
            return new StackArrayList<T>(); //regresa ArrayList
        else
            return new StackVector<T>(); //regresa Vector
    }
}
