import java.util.Scanner;
import java.util.Random;

public class game{

	public static void main(String args[])
    { 
    		generateRandom();
    } 

    public static void generateRandom(){
 
    	Random rand=new Random();

    	int randomNum=rand.nextInt(11);

    	guess(randomNum);
    }
    public static void guess(int randomNum){
    	
    	Scanner in=new Scanner(System.in);

    	System.out.println("");
    	System.out.println("Number Guessing Game");
    	System.out.print("Guess a number between 0-10: ");
    	int guess=in.nextInt();
    	System.out.println("");

    	while (guess<0 || guess>10){
    		System.out.print("Guess a number between 0-10: ");
    		guess=in.nextInt();
    		System.out.println("");
    	}

    	int tries=0;
    	while(guess!=randomNum){
    		tries++;
    		System.out.println("Wrong Guess!");
    		System.out.print("Guess again: ");
    		guess=in.nextInt();
    		System.out.println("");

    		while (guess<0 || guess>10){
    			System.out.print("Guess a number between 0-10: ");
    			guess=in.nextInt();
    			System.out.println("");
    		}
    	}

    	System.out.println("Correct Answer. You Won!");
    	System.out.println("Wrong Tries: "+tries);
    	System.out.println("");

    	System.out.println("Press 1 to Play again.");
    	System.out.println("Press 0 to exit.");
    	int choice=in.nextInt();

    	if(choice==1)
    		generateRandom();
    	else
    		return;
    }
}