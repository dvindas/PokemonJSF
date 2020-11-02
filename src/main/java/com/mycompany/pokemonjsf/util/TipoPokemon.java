/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pokemonjsf.util;

/**
 *
 * @author dperez
 */
public enum TipoPokemon {
    AGUA("A"),
    FUEGO("F"),
    ROCA("R"),
    RAYO("Y"),
    PLANTA("P");
    
    private final String value;
    
    private TipoPokemon(String value){
        this.value = value;
    }

    public String getValue(){
      return value;
    }
}
