package animalgame.food.abstractmodels;

public abstract class Food {
    private String name;
    private int weight;
    private int foodPrice;

    public Food(String name, int weight) {
        this.name = name;
        this.weight = weight;

    }

    public String getName(){
        
        return this.name;

    }

    public void addWeight(int weight){
    this.weight += weight;
    }

    public int getWeight() {
        return weight;
    }
    public void setFoodPrice(int foodPrice){
        this.foodPrice = foodPrice;
    }
    public int getFoodPrice(){
        return this.foodPrice;
    }
}
