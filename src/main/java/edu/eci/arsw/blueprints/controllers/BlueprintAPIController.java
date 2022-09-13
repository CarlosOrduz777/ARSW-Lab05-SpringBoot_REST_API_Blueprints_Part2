/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author hcadavid
 */
@RestController
@RequestMapping(value = "/blueprints")
public class BlueprintAPIController {

    @Autowired
    private BlueprintsServices blueprintsServices;

    @GetMapping()
    public ResponseEntity<Set<?>> getbluprints() {
        //obtener datos que se enviarán a través del API
        return new ResponseEntity<Set<?>>(blueprintsServices.getAllBlueprints(), HttpStatus.ACCEPTED);
    }

    @GetMapping("{author}")
    public ResponseEntity<Set<?>> getblueprintbyauthor(@PathVariable String author) throws BlueprintNotFoundException {
        Set<Blueprint> compare = new HashSet<>();
        if( !blueprintsServices.getBlueprintsByAuthor(author).equals(compare)){
                return new ResponseEntity<Set<?>>(blueprintsServices.getBlueprintsByAuthor(author), HttpStatus.ACCEPTED);

            }
        return new ResponseEntity<Set<?>>(Collections.singleton("Este"+author +"no se encuentra, intente de nuevo: "),HttpStatus.NOT_FOUND);
    }

    @GetMapping("{author}/{bpname}")
    public ResponseEntity<Blueprint> getblueprint(@PathVariable String author, @PathVariable String bpname) throws BlueprintNotFoundException {
        Blueprint bp = blueprintsServices.getBlueprint(author, bpname);
        if(bp.getAuthor() == null){
            return new ResponseEntity<Blueprint>(blueprintsServices.getBlueprint(author, bpname),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Blueprint>(blueprintsServices.getBlueprint(author, bpname), HttpStatus.ACCEPTED);

    }

    @PostMapping("/addBlueprint")
    public ResponseEntity<Blueprint> postBlueprint(@RequestBody Blueprint bp){
        blueprintsServices.addNewBlueprint(bp);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/addBlueprint/{author}/{bpname}")
    public ResponseEntity<Blueprint> replaceBlueprint(@RequestBody Blueprint blueprintUpdate,@PathVariable String author,@PathVariable String bpname){
        blueprintsServices.updateBluePrint(blueprintUpdate,author,bpname);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}

