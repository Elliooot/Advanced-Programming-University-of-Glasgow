public class Dog extends Pet{
    private String breed;
    private String furColour;
    private String favouriteToy;
    public Dog(String n, int a, String b, String fC, String fT){
        super(n, a);
        this.breed = b;
        this.furColour = fC;
        this.favouriteToy = fT;
    }

    public String giveTreat(){
        return this.name + " says thanks for the treat!";
    }
//    @Override
//    public String toString(){
//        return this.name + " is a " + this.breed + " and enjoys playing with a " + this.favouriteToy + " every day.";
//    }

    //@Overloading
    public String toString(String extraString){
        return this.name + " is a " + this.breed + " and enjoys going to " + this.favouriteToy + " every day " + extraString + ".";
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getFurColour() {
        return furColour;
    }

    public void setFurColour(String furColour) {
        this.furColour = furColour;
    }

    public String getFavouriteToy() {
        return favouriteToy;
    }

    public void setFavouriteToy(String favouriteToy) {
        this.favouriteToy = favouriteToy;
    }
}
