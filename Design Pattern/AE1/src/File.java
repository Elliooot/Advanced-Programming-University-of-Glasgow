package file2981436;

public class File implements Component{ // Leaf(Concrete Component)
    private String name;
    private int size;
    private int count = 1;
    public File(String name, int size){
        this.name = name;
        this.size = size;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public int getCount() {
        return this.count;
    }

    @Override
    public String display(String prefix) {
        return this.getName() + " (" + this.getSize() + ")\n";
    }

    @Override
    public Component search(String name) {
        return this;
    }
}
