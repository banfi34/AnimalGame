package animalgame;

import animalgame.animals.abstractmodels.Animal;
import animalgame.food.abstractmodels.Food;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This is the Player class where we store info about the player, and we have arraylists for
 * the players alive animals, deceased animals, and the player food they bought in the store. 
 * @author Sebastian Banfi, Oskar Herdenberg, Mathilda Nilsson, Hanna Petersson
 */
public class Player implements Serializable {
    private String name;
    private int money;
    private ArrayList<Animal> playerAnimal;
    private ArrayList<Food> foods;
    private ArrayList<Animal> deceasedAnimals;

    /**
     * Constructor for the Player Class
     * Initialize name decided by parameters.
     * Initialize money of this player to start the game with 20000 gold.
     * Initialize this player animals as a new arraylist.
     * Initialize this player food as a new arraylist.
     * Initialize this player deceased animals as a new arraylist.
     * @param name Name of this player
     */
    public Player(String name) {
        this.name = name;
        this.money = 20000;
        this.playerAnimal = new ArrayList<>();
        this.foods = new ArrayList<>();
        this.deceasedAnimals = new ArrayList<>();
    }

    /**
     * Checks if the animal eats the food it is given.
     * Then checks if the player has the amount of food that they try to give the animal.
     * Add 10% health per kilo food and remove food from players
     * foodslist if the animal eats the food.
     * @param animal as an animal object.
     * @param food as a food object.
     * @param weight as an int.
     */
    public void feedAnimal(Animal animal, Food food, int weight) {
        if(animal.eat(food)){
            if(weight <= food.getWeight()){
                int f = weight*10;
                animal.setHealth(animal.getHealth()+f);
                if(animal.getHealth() >= 100){
                    animal.setHealth(100);
                }
                if(weight == food.getWeight()){
                    this.foods.remove(food);
                }else{
                    food.removeWeight(weight);
                }
            }else{
                System.out.println("You don't have " +weight + "kg " +food.getName().toLowerCase() + " to give " +animal.getName());
            }
        }
    }

    /**
     * Returns the food in this player food list
     * @return the list of food
     */
    public ArrayList<Food> getFoods(){
        return this.foods;
    }

    /**
     * Add food to this player food list
     * @param food the food object to add
     */
    public void setFoods(Food food){
        this.foods.add(food);
    }

    /**
     * Return the name of this player
     * @return name as string
     */
    public String getName() {
        return name;
    }

    /**
     * Changes the current money of this player
     * @param money int to add to value
     */
    public void addMoney(int money){
        this.money += money;
    }

    /**
     * Return the money of this player
     * @return money as int
     */
    public int getMoney() {
        return money;
    }

    /**
     * Changes the current money of this player
     * @param money int to remove from value
     */
    public void removeMoney(int money){
        this.money -= money;
    }

    /**
     * Add animal to this player animal list
     * @param animal the animal object to add
     */
    public void addPlayerAnimal(Animal animal) {
        this.playerAnimal.add(animal);
    }

    /**
     * Returns the animals in this player dead animals list
     * @return a list of dead animals
     */
    public ArrayList<Animal> getDeceasedAnimals() {
        return deceasedAnimals;
    }

    /**
     * Add dead animal to this player dead animal list
     * @param deceasedAnimal animal object to add
     */
    public void setDeceasedAnimals(Animal deceasedAnimal) {
        this.deceasedAnimals.add(deceasedAnimal);
    }

    /**
     * Replaces the deceased animal list
     * @param deceasedAnimalList replace list with parameter list
     */
    public void setDeceasedAnimalList(ArrayList<Animal> deceasedAnimalList){
        this.deceasedAnimals = deceasedAnimalList;
    }

    /**
     * Return all the animals in this player animal list
     * @return list of animals
     */
    public ArrayList<Animal> getPlayerAnimal() {
        return this.playerAnimal;
    }

    /**
     *  Replaces the playerAnimal list
     * @param animalList replace the pLayerAnimal list with parameter list
     */
    public void setPlayerAnimal(ArrayList<Animal> animalList) {
        this.playerAnimal = animalList;
    }
}
