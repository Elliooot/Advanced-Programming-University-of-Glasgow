public class Cat extends Pet{
    private String breed;
    private String furColour;
    private String favouriteSpot;
    public Cat(String n, int a, String b, String fC, String fS){
        super(n, a);
        this.breed = b;
        this.furColour = fC;
        this.favouriteSpot = fS;
    }

    public String getBreed() {
        return breed;
    }
//    @Override
//    public String toString(){
//        return this.name + " is a " + this.breed + " and enjoys going to " + this.favouriteSpot + " every day.";
//    }
    //@Overloading
    public String toString(String extraString){
        return this.name + " is a " + this.breed + " and enjoys going to " + this.favouriteSpot + " every day " + extraString + ".";
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

    public String getFavouriteSpot() {
        return favouriteSpot;
    }

    public void setFavouriteSpot(String favouriteSpot) {
        this.favouriteSpot = favouriteSpot;
    }
}
