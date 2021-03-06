package animalgame.animals.abstractmodels;

import animalgame.enums.Gender;
import animalgame.food.abstractmodels.Food;
import java.io.Serializable;

/**
 * Animal class is the abstract superclass to all animal subclasses
 * @author Sebastian Banfi, Oskar Herdenberg, Mathilda Nilsson, Hanna Petersson
 */
public abstract class Animal implements Serializable {
    private String name;
    private int health;
    private int maxAge;
    private int currentAge;
    private int animalPrice;
    private final Gender gender;
    private boolean aliveStatus;
    private int randNr;

    /**
     * Constructor for the Animal class
     * Initialize health of this animal to 100 and alive status to true when created.
     * Initialize name and gender decided by parameters.
     * @param name Name of this animal
     * @param gender Gender of this animal
     */
    public Animal(String name, Gender gender){
        this.name = name;
        this.health = 100;
        this.gender = gender;
        this.aliveStatus = true;
    }

    /**
     * If foodToEat instance of a food the animal doesn't eat returns false and an output to the console.
     * If true the animal eats the food object it is given.
     * @param foodToEat as food object
     * @return if the animal eats the food or not
     */
    public abstract boolean eat(Food foodToEat);

    /**
     * Kills the animal and removes it from owners list of animals
     */
    public void death(){
        this.aliveStatus = false;
    }

    /**
     * gets the amount lost hp
     * @return randNr gets hp lost in int
     */
    public int getRandNr() {
        return randNr;
    }

    /**
     * Remove 10-30% of maximum value of health each time method is running
     * checks if health is over 0, if not the animal dies
     */
    public void healthOverTime(){
        this.randNr = (int) (Math.random()*21)+10;
        if (randNr < health){
            this.health = this.health-randNr;
        }else{
            this.health = 0;
            death();
        }
    }

    /**
     * Takes an animal as a String and checks the art of the animal.
     * The method will randomize the amount of babies the animal will have
     * depending on the art.
     * @param animal as a string
     * @return the amount of babies the animals will have
     */
    public int howManyBabies(String animal){
        switch(animal){
            case "Ferret":
                return (int) (Math.random() * 6) + 1;
            case "Giraffe":
                return (int) (Math.random() * 3) + 1;
            case "Dragon":
                return (int) (Math.random() * 8) + 1;
            case "PolarBear":
                return (int) (Math.random() * 4) + 1;
            case "Troll":
                return (int) (Math.random() * 2) + 1;
        }return 0;
    }

    /**
     * Calculates the sell price for this animal
     * @return sellprice as an int
     */
    public int animalSellPrice() {
        if (currentAge > health) {
            return animalPrice * ((currentAge - health)) / 100;
        } else {
            return animalPrice * ((health - currentAge)) / 100;
        }
    }

    /**
     * Changes the sell price of this animal
     * @param animalPrice int as new value
     */
    public void setAnimalPrice(int animalPrice) {
        this.animalPrice = animalPrice;
    }

    /**
     * Return if animal is dead or not
     * @return alive status as a boolean
     */
    public boolean getAliveStatus() {
        return aliveStatus;
    }

    /**
     * Return the current age of this animal
     * @return current age as int
     */
    public int getCurrentAge(){
        return this. currentAge;
    }

    /**
     * Changes the current age of this animal
     * @param currentAge int as new value
     */
    public void setCurrentAge(int currentAge) {
        this.currentAge += currentAge;
    }

    /**
     * Return the gender of this animal
     * @return gender as Gender
     */
    public Gender getGender(){
        return this.gender;
    }

    /**
     * Return the health of this animal
     * @return health as int
     */
    public int getHealth(){
        return this.health;
    }

    /**
     * Changes the health of this animal
     * @param health int as new value
     */
    public void setHealth(int health){
        this.health = health;
    }

    /**
     * Return the maximum age of this animal
     * @return maximum age as int
     */
    public int getMaxAge(){
        return this.maxAge;
    }

    /**
     * Changes the maximum age of this animal
     * @param maxAge int as new value
     */
    public void setMaxAge(int maxAge){
        this.maxAge = maxAge;
    }

    /**
     * Return the name of this animal
     * @return name as String
     */
    public String getName(){
        return this.name;
    }
}