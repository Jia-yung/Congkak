package com.congkak.objects;

import java.io.*;

/**
 * <h1>Hole</h1>
 * A Hole Object that contains values that are referred as seeds
 *
 * @author  Lishan Abbas
 * @author  Hans Maulloo
 * @author  Daniel Jedidiah Antonio
 * @since   6/12/17
 */
public class Hole implements Serializable {

    private static final long serialVersionUID = 1L;

    private int value;

   /**
    * Constructor A
    *
    * <p>
    * Creates a new Hole with the default seed count: 4
    * </p>
    *
    */
    public Hole() {
        this.value = 4;
    }

   /**
    * Constructor B
    *
    * <p>
    * Creates a new Hole with the value passed in
    * </p>
    * 
    * @param value Custom value to pass in
    */
    public Hole(int value) {
        this.value = value;
    }

   /**
    * Increment the value of the Hole
    */
    public void incrementValue() { 
        this.value++; 
    }

   /**
    * Checks whether the hole is empty
    * 
    * @return boolean Whether the hole is empty or not
    */
    public boolean isEmpty() {
        if (this.value > 0) {
            return false;
        } else {
            return true;
        }
    }

   /**
    * Clears the hole's value
    */
    public void clear() { 
        this.value = 0; 
    }

   /**
    * @return int gets the value of the Hole
    */
    public int getValue() { 
        return this.value; 
    }

   /**
    * @param value sets the value to the Hole
    */
    public void setValue(int value) { this.value = value; }
}
