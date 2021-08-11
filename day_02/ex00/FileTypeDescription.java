import java.util.List;

public class FileTypeDescription {
    public String name;
    public List<Short> signature;
    @Override
    public String toString() {
        return name + ": " + signature.toString();
    }

}
