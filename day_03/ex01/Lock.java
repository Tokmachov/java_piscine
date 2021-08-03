public class Lock {
    private static Lock lock = new Lock();
    static public Lock getLock() {
        return lock;
    }
}