import java.util.List;
import java.util.Random;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author Matilda Delacourt
 * @version 10.29.2021
 */
public abstract class Animal
{
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;
    
    // move the age field from Fox and Rabbit to here
    private int age;
    
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(Field field, Location location)
    {
        alive = true;
        age = 0;
        this.field = field;
        setLocation(location);
    }
    
    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     */
    abstract public void act(List<Animal> newAnimals);

    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }

    /**
     * Return the age of animal
     * @return the animal's age
     */
    protected int getAge(){
        return age;
    }
    
    /**
     * Set the age of the animal
     * @param age the age of the animal
     */
    protected void setAge(int age){
        this.age = age;
    }
    
     /**
     * Increase the age. This could result in the animal's death.
     */
    protected void incrementAge()
    {
        setAge(getAge()+1);
        if(getAge() > getMaxAge()) {
            setDead();
        }
    }
    
    /**
     * An animal can breed if it reaches breeding age
     * @return true if breeding age
     */
    public boolean canBreed(){
        return age >= getBreedingAge();
    }
    
    /**
     * Return breeding age
     * @return the breeding age of animal
     */
    abstract protected int getBreedingAge();
    
     /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    protected int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= getBreedingProbability()) {
            births = rand.nextInt(getMaxLitterSize()) + 1;
        }
        return births;
    }
    
    /**
     * Return breeding probability
     * @return the chance of breeding
     */
    abstract protected double getBreedingProbability();
    
    /**
     * Return max breeding age
     * @return max breeding age for animal
     */
    abstract protected int getMaxLitterSize();
    
    /**
     * Return the max age
     * @return the max age of animal
     */
    abstract protected int getMaxAge();
    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation()
    {
        return location;
    }
    
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    protected Field getField()
    {
        return field;
    }
}
