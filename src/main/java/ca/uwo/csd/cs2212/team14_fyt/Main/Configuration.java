package ca.uwo.csd.cs2212.team14_fyt.Main;
import java.io.*;
import java.awt.Color;

/**
 * Configuration.java
 * 
 * Configuration creates and stores the user's preferences as to theme and
 * tile visibility. Implements Serializable for use in object serialization.
 * 
 * @author Fyt Team
 * @version 1.0
 * @since February 29, 2016
 */
public class Configuration implements Serializable {
    
    //============== Attributes ==========================
    private boolean[] visible; // Array of Boolean values to set which tiles are visible
    private Color[] theme; // Array of Colors to update to reflect the theme
    
    //============== Constructors ==========================
    /**
    * Constructor that initializes the attribute arrays.
    * 
    * Takes no parameters.
    */
    public Configuration(){
        theme = new Color[2]; // Primary and secondary theme colours stored here
        visible = new boolean[8]; // The visibility (T / F) of each tile stored here
    }
    
    //============== Methods ==========================
    /**
     * Accessor method to get the visibility of the given tile.
     * @param tile (required) - The integer value corresponding to the tile of which we would like to know the visibility.
     * @return <tt>true</tt> if the tile is visible, <tt>false</tt> otherwise
     */
    public boolean getVisible(int tile){
        return visible[tile];
    }

    /**
     * Accessor method to get the primary colour of a theme.
     * @return The primary Color value of the theme.
     */
    public Color getPrimaryColor() {
        return theme[0];
    }
    
    /**
     * Accessor method to get the secondary colour of a theme.
     * @return The secondary Color value of the theme.
     */
    public Color getSecondaryColor(){
        return theme[1];
    }
    
    /**
     * Accessor method to get the theme.
     * @return Returns the theme, or <tt>null</tt> if an Exception is caught.
     */
    public static Color[] getTheme(){
        Color[] pair = new Color[2];
        try {
            Configuration c = load();
            pair[0] = c.theme[0];
            pair[1] = c.theme[1];
        } catch (Exception e) {
            return null;
        }
        return pair;
    }

    /**
     * Setter method to set the theme.
     * @param newTheme The Color array corresponding to the new theme.
     */
    public void setTheme(Color[] newTheme) {
        this.theme = newTheme;
    }
    
    /**
     * Setter method that sets the visibility of a given tile.
     * @param tile The (address of the) tile that we are setting the visibility of
     * @param value <tt>true</tt> if we want the tile to be visible, <tt>false</tt> otherwise.
     */
    public void setVisible(int tile,boolean value){
        visible[tile] = value;
    }
    
    /**
     * Save method serializes the current configuration to a config file.
     * @throws Exception Throws an exception when the config file cannot be stored.
     */
    public void save() throws Exception{
        ObjectOutputStream out = new ObjectOutputStream(
        new FileOutputStream("config",false));
        out.writeObject(this);
        out.close();
    }
    
    /**
     * Load method accesses the config file and loads the user's preferences.
     * @return Configuration c
     * @throws Exception Throws an exception when the config file cannot be found.
     */
    public static Configuration load() throws Exception{
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("config"));
        Configuration c = (Configuration) in.readObject();
        in.close();
        return c;
    }
}
