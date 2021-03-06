package animalgame.utilities;

import animalgame.Game;
import animalgame.animals.abstractmodels.Animal;
import animalgame.enums.Gender;
import animalgame.food.abstractmodels.Food;
import java.util.ArrayList;

/**
 * This is the Menu class where we store all of our game menus.
 * @author Sebastian Banfi, Oskar Herdenberg, Mathilda Nilsson, Hanna Petersson
 */
public class Menu {
    private Game currentGame;

    /**
     * Constructor for the Menu class
     * Initialize currentgame for easy access from menus
     * @param currentGame input the current game
     */
    public Menu(Game currentGame){
        this.currentGame = currentGame;
    }

    /**
     * First menu of the game.
     * Display and decide if player is starting a new game or loading from saved file
     */
    public void newGameMenu(){
        System.out.println(ProgramUtils.YELLOW+" *".repeat(29)+"\n"+" *".repeat(9)+" WELCOME TO ANIMAL GAME"+" *".repeat(9)+"\n"+" *".repeat(30)+"\n In this game, you " +
                "and your opponents compete to make as much \n money as possible by buying, feeding and " +
                "selling animals.\n The one who has the most money in the end wins.  " + ProgramUtils.RESET + "\uD83D\uDC51");
        switch (ProgramUtils.menuBuilder("\nStart menu","New game","Load game")){
            case 1:
                this.currentGame.startGame();
                break;
            case 2:
                loadGameMenu();
                break;
            default:
                newGameMenu();
        }
    }

    /**
     * Menu that display saved files and load them if selected, or starts new game if there's none
     */
    private void loadGameMenu(){
        if(ProgramUtils.readAllLines().size() == 0){
            System.out.println("No files saved, starting game...");
            currentGame.startGame();
        }
        for(String files : ProgramUtils.readAllLines()){
            switch(ProgramUtils.menuBuilder("\nLoad game",files, "Back")){
                case 1:
                    this.currentGame.loadGame(ProgramUtils.readAllLines().get(0));
                    break;
                case 2:
                    this.currentGame.loadGame(ProgramUtils.readAllLines().get(1));
                    break;
                case 3:
                    this.currentGame.loadGame(ProgramUtils.readAllLines().get(2));
                    break;
                case 4:
                    newGameMenu();
                    break;
                default:
                    loadGameMenu();
            }
        }
    }

    /**
     * Decision menu for each player each round, gives the option to advance through other menus
     */
    public void roundMenu(){
        switch(ProgramUtils.menuBuilder("\nChoose one","Shop","Feed animals","Mate animals", "Save game", "Exit game")){
            case 1:
                this.currentGame.getStore().setCustomer(this.currentGame.getCurrentPlayer());
                shopMenu();
                break;
            case 2:
                feedAnimalsMenu();
                break;
            case 3:
                mateAnimalsMenu();
                break;
            case 4:
                saveGameMenu();
            case 5:
                exitMenu();
                break;
            default:
                roundMenu();
        }
    }

    /**
     * Menu to display and execute alternative to shut down the game
     */
    private void exitMenu(){
        switch(ProgramUtils.menuBuilder("\nExit game?","Exit game","Back")){
            case 1:
                exitGame();
                break;
            case 2:
                roundMenu();
                break;
            default:
                exitMenu();
        }
    }

    /**
     * Turn off the program on method call
     */
    private void exitGame(){
        System.out.println("Shutting down...");
        System.exit(1);
    }


    /**
     * Display options for player to choose from.
     * return true or false depending on choice.
     * @param type String that will display item in output
     * @param buySell String that will change the wording of the output
     * @return
     */
    private boolean continueMenu(String type, String buySell){
        switch(ProgramUtils.menuBuilder("\nContinue to "+buySell+ " more " + type +" ?","Yes","No")){
            case 1:
                return true;
            case 2:
                return false;
            default:
                continueMenu(type,buySell);
        }
        return true;
    }

    /**
     * Shop decision menu for each player each round, gives the option to advance through other menus.
     * Looping each time player choose to.
     */
    private void shopMenu(){
        switch(ProgramUtils.menuBuilder("\nShop","Buy animals","Buy Food","Sell animals")){
            case 1:
                do{
                    if(animalChoice()){
                        break;
                    }
                }while(continueMenu("animals","buy"));
                break;
            case 2:
                do{
                    if(shopFoodMenu()){
                        break;
                    }
                }while(continueMenu("food","buy"));
                break;
            case 3:

                if(this.currentGame.getCurrentPlayer().getPlayerAnimal().size() == 0){
                    System.out.println("No animal to sell!");
                    shopMenu();
                }else{
                    do{
                        if(animalSellMenu()){
                            break;
                        }
                    }while(continueMenu("animals", "sell"));
                }
                break;
            default:
                shopMenu();
        }
    }

    /**
     * Give the player choice to either buy an animal or continue without buying
     * @return boolean for decision-making to break loop
     */
    private boolean animalChoice(){
        String animal ="";
        Gender gender = null;
        int price = 0;
        switch(ProgramUtils.menuBuilder("\nBuyAnimal","Troll"+":\t\t800 Gold"+"\tEats taco and waffles","Giraffe"+":\t1000 Gold"+"\tEats waffles","Polar bear"+":\t1500 Gold"+"\tEats sausages","Ferret"+":\t\t2250 Gold"+"\tEats sausages and taco","Dragon"+":\t\t4000 Gold"+"\tEats taco", "Continue")){
            case 1:
                animal = "Troll";
                price = 800;
                gender = genderSelectionMenu();
                break;
            case 2:
                animal = "Giraffe";
                price = 1000;
                gender = genderSelectionMenu();
                break;
            case 3:
                animal = "PolarBear";
                price = 1500;
                gender = genderSelectionMenu();
                break;
            case 4:
                animal = "Ferret";
                price = 2250;
                gender = genderSelectionMenu();
                break;
            case 5:
                animal = "Dragon";
                price = 4000;
                gender = genderSelectionMenu();
                break;
            case 6:
                return true;
            default:
                animalChoice();
        }
        if(!currentGame.getStore().animalToBuy(animal,gender,price)){
            animalChoice();
        }
        return false;
    }

    /**
     * Prints out a menu to show the player what food they can buy and the price. The player
     * chooses what food to buy and the food will be added in to the player food list and remove
     * money from the player.
     */
    private boolean shopFoodMenu(){
        String food ="";
        int price = 0;
        switch( ProgramUtils.menuBuilder("\nAvailable food","Sausage \tPreferred by: Polar bear and Ferret" + ": \t20 "+ProgramUtils.YELLOW+"Gold"+ProgramUtils.RESET+"/kg", "Waffles \tPreferred by: Giraffe and Troll" + ": \t\t50 "+ProgramUtils.YELLOW+"Gold"+ProgramUtils.RESET+"/kg","Taco \t\tPreferred by: Dragon,Ferret and Troll" + ": \t100 "+ProgramUtils.YELLOW+"Gold"+ProgramUtils.RESET+"/kg", "Continue")){
            case 1:
                food = "Sausage";
                price = 20;
                break;
            case 2:
                food = "Waffles";
                price = 50;
                break;
            case 3:
                food = "Taco";
                price = 100;
                break;
            case 4:
                return true;
            default:
                shopFoodMenu();
        }
        this.currentGame.getStore().foodToBuy(food,price);
        return false;
    }

    /**
     * Checks that the player has at least one animal to sell if not returns false and an error output to console.
     * If one or more animals exists the player can choose which animal to sell.
     * @return false if no animal exists in players animal list, true if player has animals to sell
     */
    public boolean animalSellMenu() {
        if(this.currentGame.getCurrentPlayer().getPlayerAnimal().size() == 0){
            System.out.println("No animal to sell!");
            return true;
        }else{
            ArrayList<Animal>animalList = this.currentGame.getCurrentPlayer().getPlayerAnimal();
            System.out.println("Choose which animal to sell");
            playerAnimalsAsMenu();
            int menuChoice = ProgramUtils.tryCatch(1,animalList.size());
            this.currentGame.getStore().animalToSell(animalList.get(menuChoice-1));
            return false;
        }
    }

    /**
     * Takes an animal as a string and checks what kind of food that animal eats.
     * @param animal as a String
     * @return what animal eats as a String
     */
    private String whatAnimalEats(String animal){
        switch (animal){
            case "Ferret":
                return "Taco and Sausage";
            case "Giraffe":
                return "Waffles";
            case "Dragon":
                return "Taco";
            case "PolarBear":
                return "Sausage";
            case "Troll":
                return "Waffles and Taco";
            default:
                return"";
        }
    }

    /**
     * Checks so the player at least have one animal and one item of food to feed the animal with.
     * Then asks the player to choose which of their animal to feed, what food and how many kilos
     * of that food to feed the animal with. Calls the feedAnimal method...
     */
    private void feedAnimalsMenu() {
        ArrayList<Animal> playerAnimalList = currentGame.getCurrentPlayer().getPlayerAnimal();
        ArrayList<Food> playerFoodList = currentGame.getCurrentPlayer().getFoods();
        boolean buyMoreFood = false;

        if (playerAnimalList.size() == 0) {
            System.out.println("You must have one animal to feed!");
        } else if (playerFoodList.size() == 0) {
            System.out.println("You must buy food to feed your animal with!");
        } else {
            do{
                System.out.println("Choose which animals to feed: ");
                playerAnimalsAsMenu();
                int animalChoice = ProgramUtils.tryCatch(1,playerAnimalList.size());
                System.out.println(playerAnimalList.get(animalChoice-1).getName() + " likes to eat: " + whatAnimalEats(playerAnimalList.get(animalChoice-1).getClass().getSimpleName()));
                System.out.println("Choose which food to feed your animal with: ");
                playerFoodAsMenu();
                int foodChoice = ProgramUtils.tryCatch(1,playerFoodList.size());
                System.out.println("How many kg do you want to feed your animal: ");
                int kgChoice = ProgramUtils.tryCatch();
                currentGame.getCurrentPlayer().feedAnimal(playerAnimalList.get(animalChoice-1),playerFoodList.get(foodChoice-1), kgChoice);
                System.out.println("Feed another animal?");
                System.out.println("1.\tYes");
                System.out.println("2.\tNo");
                switch(ProgramUtils.tryCatch(1,2)){
                    case 1:
                        buyMoreFood = true;
                        break;
                    case 2:
                        buyMoreFood = false;
                        break;
                }

            }while(buyMoreFood);

        }
    }

    /**
     * Menu that display all players animals to be bred,
     * Checks players animal count and if the same animal is chosen.
     */
    public void mateAnimalsMenu(){
        ArrayList<Animal> playerAnimalList = currentGame.getCurrentPlayer().getPlayerAnimal();
        if (playerAnimalList.size() < 2) {
            System.out.println(ProgramUtils.RED+"You currently have "+playerAnimalList.size()+" animal, you need 2 or more animals!"+ProgramUtils.RESET);
            System.out.println(" ");
            roundMenu();
        }else{
            playerAnimalsAsMenu();
            int menuChoice = 0;
            int otherChoice = 0;
            do {
                ArrayList<Animal> newAnimalList = new ArrayList<>();
                System.out.print("First animal to breed: ");
                menuChoice = ProgramUtils.tryCatch(1,playerAnimalList.size());
                newAnimalList.add(playerAnimalList.get(menuChoice-1));
                System.out.print("Write the second animal to breed: ");
                otherChoice = ProgramUtils.tryCatch(1,playerAnimalList.size());
                if(otherChoice == menuChoice){
                    System.out.println("\nCan't choose the same animal!\n");
                }else{
                    newAnimalList.add(playerAnimalList.get(otherChoice-1));
                    Factory.tryMating(newAnimalList.get(0),newAnimalList.get(1),currentGame.getCurrentPlayer());
                }
            }while (menuChoice < 1 || menuChoice > playerAnimalList.size() || menuChoice == otherChoice);
        }
    }

    /**
     * Menu that gives the player choice of gender and return it as a Gender
     * @return gender as Gender
     */
    private Gender genderSelectionMenu(){
        switch (ProgramUtils.menuBuilder("\nGenderChoice", "MALE","FEMALE")){
            case 1:
                return Gender.MALE;
            case 2:
                return Gender.FEMALE;
            default:
                genderSelectionMenu();
        }
        return null;
    }

    /**
     * Menu for displaying and give player option to store game to file
     */
    private void saveGameMenu(){
        String[] menuArray = {"","",""};
        for(int i = 0; i < ProgramUtils.readAllLines().size() ; i++){
            menuArray[i] = ProgramUtils.readAllLines().get(i);
        }
        switch (ProgramUtils.menuBuilder("\nSave game)",menuArray[0],menuArray[1],menuArray[2], "Back")){
            case 1:
                saveGameMenuHelper(menuArray[0],1);
                break;
            case 2:
                saveGameMenuHelper(menuArray[1],2);
                break;
            case 3:
                saveGameMenuHelper(menuArray[2],3);
                break;
            case 4:
                roundMenu();
                break;
            default:
                break;
        }
    }

    /**
     * Help method to the saveGameMenu.
     * Checks if player is sure about overwriting already saved games.
     * Gives the option to name the game file.
     * @param menuOptionName input String for condition check
     * @param menuIndex The index of the menu option
     */
    private void saveGameMenuHelper(String menuOptionName, int menuIndex){
        if(!menuOptionName.equals("")){
            System.out.println("Are you sure you want to delete your saved game? y/n");
            String yesOrNo = ProgramUtils.userInput();
            if(yesOrNo.equalsIgnoreCase("y") ){
                ProgramUtils.DeleteFile(ProgramUtils.readAllLines().get(menuIndex-1));
            }else{
                saveGameMenu();
            }
        }
        System.out.println("Write the name of your save file");
        String fileName = ProgramUtils.userInput();
        this.currentGame.saveGame(fileName);
        ProgramUtils.writeToSaveFile(fileName,menuIndex);
    }

    /**
     * Prints out a summary of the current players animals to the console.
     */
    private void playerAnimalsAsMenu(){
        int i = 1;
        for(Animal animal : this.currentGame.getCurrentPlayer().getPlayerAnimal()) {
            System.out.println(i + ".\t" + animal.getClass().getSimpleName() + " :     Name: " + ProgramUtils.PURPLE+animal.getName()+ProgramUtils.RESET+ "     Gender: "+animal.getGender().toString().toLowerCase());
            i++;
        }
    }

    /**
     * Prints out the current players amount and type of food they have bought to the console.
     */
    private void playerFoodAsMenu(){
        int i = 1;
        for(Food food : this.currentGame.getCurrentPlayer().getFoods()){
            System.out.println(i + ".\t Amount of weight: " + food.getWeight() + "KG  :  Type of food: " + food.getName());
            i++;
        }
    }

}
