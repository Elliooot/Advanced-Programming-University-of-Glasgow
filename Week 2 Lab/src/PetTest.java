public class PetTest {
    public static void main(String[] args){
        Pet[] petNumber = new Pet[3];
        petNumber[0] = new Pet("Neutral", 2);
        petNumber[1] = new Cat("Kathy", 3, "American Shorthair", "Blue", "Dormitory");
        petNumber[2] = new Dog("Wynn", 5, "Golden Retriever", "Blue", "Hairpin");

        for(int i = 0; i < petNumber.length; i++){
            System.out.println(petNumber[i].toString());
            if(petNumber[i] instanceof Dog){
                System.out.println(((Dog) petNumber[i]).giveTreat());
            }
        }
    }
}