/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 *
 * @author hcadavid
 */
@Service
public class InMemoryBlueprintPersistence implements BlueprintsPersistence{

    private final Map<Tuple<String,String>,Blueprint> blueprints=new ConcurrentHashMap<>();

    public InMemoryBlueprintPersistence() {
        //load First BluePrint
        Point[] pts=new Point[]{new Point(142, 145),new Point(115, 115),new Point(190,78)};
        Blueprint bp=new Blueprint("valentina", "monalisa",pts);
        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        //load Second Blueprint
        Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15), new Point(30,40),new Point(10,30),new Point(50,20),new Point(10,30),new Point(40,60)};
        Blueprint bp0=new Blueprint("valentina", "plano valentina",pts0);
        blueprints.put(new Tuple<>(bp0.getAuthor(),bp0.getName()), bp0);
        //load Third Blueprint
        Point[] pts1=new Point[]{new Point(140, 140),new Point(115, 115)};
        Blueprint bp1=new Blueprint("carlos", "maverick",pts1);
        blueprints.put(new Tuple<>(bp1.getAuthor(),bp1.getName()), bp1);
    }    
    
    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(),bp.getName()))){
            throw new BlueprintPersistenceException("The given blueprint already exists: "+bp);
        }
        else{
            blueprints.putIfAbsent(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        }
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        if (!blueprints.containsKey(new Tuple<>(author,bprintname))){
            throw new BlueprintNotFoundException("Blueprint not found");
        }
        else{
           return blueprints.get(new Tuple<>(author,bprintname));
        }
    }

    @Override
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        return blueprints.entrySet().stream().filter(e -> author.equals(e.getKey().getElem1())).map(Map.Entry::getValue).collect(Collectors.toSet());
    }

    @Override
    public Set<Blueprint> getAllBlueprints() {
        return new HashSet<>(blueprints.values());
    }

    @Override
    public Blueprint updateBluePrint(Blueprint blueprintToUpdate,String author,String name) {
        for (Blueprint bp: getAllBlueprints()) {
            System.out.println("Blueprint:--------------"+ bp.toString());
        }
        System.out.println("----------------"+blueprintToUpdate.toString()+"....................................");
        Blueprint bp  = getAllBlueprints().stream().filter(bps -> (bps.getName().equals(blueprintToUpdate.getName()) && bps.getAuthor().equals(blueprintToUpdate.getAuthor()))).findAny().get();


        bp.setAuthor(author);
        bp.setName(name);
        return bp;
    }
    
    
}
