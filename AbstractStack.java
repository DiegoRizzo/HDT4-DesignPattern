abstract public class AbstractStack<T>
        implements Stack<T>
{
    public boolean empty()
    // post: regresa true si el stack esta vacio
    {
        return size() == 0;
    }
}