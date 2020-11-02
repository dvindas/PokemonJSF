/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pokemonjsf.controller;

import com.mycompany.pokemonjsf.model.PokemonDto;
import com.mycompany.pokemonjsf.service.PokemonService;
import com.mycompany.pokemonjsf.util.TipoPokemon;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;

/**
 *
 * @author dperez
 */
@Named(value = "pokemonController")
@ViewScoped
public class PokemonController implements Serializable {
    
    private static final Logger LOG = Logger.getLogger(PokemonController.class.getName());

    @Inject
    private PokemonService pokemonService;

    private PokemonDto pokemonDto;
    
    private PokemonDto pokemonSeleccionado;
    
    private boolean registroCargado;

    public PokemonController() {}

    @PostConstruct
    public void init() {
        nuevoPokemonOnAction();
    }

    public PokemonService getPokemonService() {
        return pokemonService;
    }

    public void setPokemonService(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    public PokemonDto getPokemonDto() {
        return pokemonDto;
    }
    
    public void setPokemonDto(PokemonDto pokemonDto) {
        this.pokemonDto = pokemonDto;
    }

    public PokemonDto getPokemonSeleccionado() {
        return pokemonSeleccionado;
    }

    public void setPokemonSeleccionado(PokemonDto pokemonSeleccionado) {
        this.pokemonSeleccionado = pokemonSeleccionado;
    }

    public List<PokemonDto> getPokemones() {
        return pokemonService.getPokemones();
    }
    
    private void limpiar() {
        this.pokemonDto = new PokemonDto();
        this.pokemonDto.setId(UUID.randomUUID().toString());
        this.pokemonDto.setSexo("M");
        this.pokemonSeleccionado = null;
        this.registroCargado = false;
    }

    public void cargarPokemon() {
        if (pokemonSeleccionado != null) {
            this.pokemonDto = this.pokemonSeleccionado;
            this.registroCargado = true;
        }
    }

    public String getDescripcionTipo(String tipo){
       if(tipo.equals(TipoPokemon.AGUA.getValue())){
           return "Agua";
       }
       if(tipo.equals(TipoPokemon.FUEGO.getValue())){
           return "Fuego";
       }
       if(tipo.equals(TipoPokemon.ROCA.getValue())){
           return "Roca";
       }
       if(tipo.equals(TipoPokemon.RAYO.getValue())){
           return "Rayo";
       }
       if(tipo.equals(TipoPokemon.PLANTA.getValue())){
           return "Planta";
       }
       throw new IllegalArgumentException("El tipo de pokémon indicado no es válido.");
    }

    public void nuevoPokemonOnAction() {
        this.limpiar();
    }

    public void guardarPokemonOnAction() {
        try {
            if (validarFormulario()) {
                this.pokemonService.guardarPokemon(this.pokemonDto);
                FacesContext.getCurrentInstance()
                        .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Guardar Pokémon", "El pokémon se ha guardado correctamente."));
                limpiar();
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al guardar el pokémon.", ex);
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Guardar Pokémon", "Ocurrió un error al guardar el pokémon."));
        }
    }

    private boolean validarFormulario() {
        List<String> requeridos = new LinkedList();

        if (pokemonDto.getNombre() == null || pokemonDto.getNombre().isEmpty()) {
            requeridos.add("Nombre");
        }
        if (pokemonDto.getDescripcion() == null || pokemonDto.getDescripcion().isEmpty()) {
            requeridos.add("Descripción");
        }
        if (pokemonDto.getTipo() == null || pokemonDto.getTipo().isEmpty()) {
            requeridos.add("Tipo");
        }
        if(pokemonDto.getAltura() == null || pokemonDto.getAltura() == 0.d){
            requeridos.add("Altura");
        }
        if(pokemonDto.getPeso() == null || pokemonDto.getPeso() == 0.d){
            requeridos.add("Peso");
        }
        if(pokemonDto.getSexo() == null || pokemonDto.getSexo().isEmpty()){
            requeridos.add("Sexo");
        }
       
        if (!requeridos.isEmpty()) {
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Validación Formulario",
                            String.format("Los siguientes datos son requeridos %s.", requeridos.stream()
                                    .collect(Collectors.joining(", ")))));
            return false;
        }

        return true;
    }

    public void eliminarPokemonOnAction() {
        try {
            if (registroCargado) {
                this.pokemonService.eliminarPokemon(pokemonDto);
                limpiar();
                FacesContext.getCurrentInstance()
                        .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminar Pokémon", "El pokémon se ha eliminado correctamente."));
                PrimeFaces.current().ajax().update("formPokemon");
                PrimeFaces.current().ajax().update("dlgBusqueda");
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Eliminar Pokémon",
                        "Primero debes cargar el pokémon que deseas eliminar."));
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al eliminar el pokémon.", ex);
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Guardar Pokémon", "Ocurrió un error al eliminar el pokémon."));
        }
    }

}
