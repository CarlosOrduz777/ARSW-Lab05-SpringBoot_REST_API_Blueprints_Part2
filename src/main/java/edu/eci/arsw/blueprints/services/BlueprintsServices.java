/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.filters.bluePrintsFilters;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author hcadavid
 */

@Service
public class BlueprintsServices {
   
    @Autowired
    private BlueprintsPersistence bpp;
    @Autowired
    private bluePrintsFilters bpf;
    
    public void addNewBlueprint(Blueprint bp)throws BlueprintPersistenceException{
        bpp.saveBlueprint(bp);
    }
    
    public Set<Blueprint> getAllBlueprints(){
        Set<Blueprint> filterBlueprints = new HashSet<>();
        for (Blueprint bp: bpp.getAllBlueprints()) {
            Blueprint bpFiltered = bpf.filtrar(bp);
            filterBlueprints.add(bpFiltered);
        }
        return filterBlueprints;
    }
    
    /**
     * 
     * @param author blueprint's author
     * @param name blueprint's name
     * @return the blueprint of the given name created by the given author
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    public Blueprint getBlueprint(String author,String name) {
        try{
            if(bpp.getBlueprint(author, name)!= null){
                Blueprint bp =  bpp.getBlueprint(author, name);
                return bpf.filtrar(bp);
            }
        }catch (BlueprintNotFoundException b){
            return new Blueprint(author,"no se enontro blueprint"+name);
        }
        return null;
    }
    
    /**
     * 
     * @param author blueprint's author
     * @return all the blueprints of the given author
     * @throws BlueprintNotFoundException if the given author doesn't exist
     */
    @GetMapping("/blueprints/{author}")
    @ResponseBody
    public Set<Blueprint> getBlueprintsByAuthor(@PathVariable("author") String author) throws BlueprintNotFoundException{
        if(bpp.getBlueprintsByAuthor(author) != null){
            Set<Blueprint> bpResult= bpp.getBlueprintsByAuthor(author);
            return bpf.filterByAuthor(bpResult);
        }else{
            throw new BlueprintNotFoundException("The given author doesn´t exist");
        }
    }
    
}