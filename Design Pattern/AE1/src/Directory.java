package file2981436;

import java.util.ArrayList;

public class Directory implements Component{ // Composite(Concrete Class)
    private ArrayList<Component> children;
    private String name;

    public Directory(String name){
        children = new ArrayList<Component>();
        this.name = name;
    }

    public void add(Component component){
        children.add(component);
    }

    public void remove(Component component){
        children.remove(component);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getSize() {
        int size = 0;
        for(Component a: children){
            size += a.getSize();
        }
        return size;
    }

    @Override
    public int getCount() {
        int count = 0;
        for(Component b: children){
            count += b.getCount();
        }
        return count;
    }
    
    @Override
    public String display(String prefix) {
        String message = this.getName() + ": (count=" + this.getCount() + ", size=" + this.getSize() + ")\n";

        for(Component c: children){
            message += prefix + c.display(prefix + prefix);
        }

        return message;
    }

    @Override
    public Component search(String name) {
        for(Component d: children){
            if(d.getName().equals(name)) return this;
            else if(d instanceof Directory){
                Component e = d.search(name);
                if(e != null) return e;
            }
        }
        return null;
    }
}
