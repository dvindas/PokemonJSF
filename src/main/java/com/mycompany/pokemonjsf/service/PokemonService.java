/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pokemonjsf.service;

import com.mycompany.pokemonjsf.model.PokemonDto;
import com.mycompany.pokemonjsf.util.TipoPokemon;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author dperez
 */
@ApplicationScoped
public class PokemonService {
    
    private List<PokemonDto> pokemones;

    public PokemonService() {}
    
    @PostConstruct
    public void init() {
        this.pokemones = new LinkedList();

        this.pokemones.add(new PokemonDto(UUID.randomUUID().toString(), "Picachu", "Cuanto más potente es la energía eléctrica que genera este Pokémon, más suaves y elásticas se vuelven las bolsas de sus mejillas.", TipoPokemon.RAYO.getValue(), 0.4, 6.d, "M"));
        this.pokemones.add(new PokemonDto(UUID.randomUUID().toString(), "Charmander", "Prefiere las cosas calientes. Dicen que cuando llueve le sale vapor de la punta de la cola.", TipoPokemon.FUEGO.getValue(), 0.6, 8.5d, "M"));
        this.pokemones.add(new PokemonDto(UUID.randomUUID().toString(), "Magmar", "Nace en cráteres de volcanes. Su cuerpo envuelto en llamas parece una bola de fuego.", TipoPokemon.FUEGO.getValue(), 1.3, 45.5d, "M"));
        this.pokemones.add(new PokemonDto(UUID.randomUUID().toString(), "Raichu", "Su larga cola le sirve como toma de tierra para protegerse a sí mismo del alto voltaje que genera su cuerpo.", TipoPokemon.FUEGO.getValue(), 0.8, 30d, "M"));
        this.pokemones.add(new PokemonDto(UUID.randomUUID().toString(), "Raichu", "Para acabar con su enemigo, lo aplasta con el peso de su cuerpo. En momentos de apuro, se esconde en el caparazón.", TipoPokemon.AGUA.getValue(), 1.6, 85.5d, "M"));
    }
    
    public void guardarPokemon(PokemonDto pokemonDto){
        this.pokemones.add(pokemonDto);
    }
    
    public void eliminarPokemon(PokemonDto pokemonDto){
        this.pokemones.remove(pokemonDto);
    }

    public List<PokemonDto> getPokemones() {
        return pokemones;
    }    
    
}
