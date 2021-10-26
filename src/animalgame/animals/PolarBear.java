package animalgame.animals;

import animalgame.animals.abstractmodels.Animal;
import animalgame.enums.Gender;
import animalgame.food.Taco;
import animalgame.food.Waffles;
import animalgame.food.abstractmodels.Food;

public class PolarBear extends Animal {

    public PolarBear(String name, int value, int maxAge, Gender gender) {
        super(name, value, maxAge, gender);
    }

    @Override
    public boolean eat(Food foodToEat) {
        if(foodToEat instanceof Taco || foodToEat instanceof Waffles){
            System.out.println("Your polar bear doesn´t eat " + foodToEat.getName());
            return false;
        }else{
            return true;
        }

    }


}