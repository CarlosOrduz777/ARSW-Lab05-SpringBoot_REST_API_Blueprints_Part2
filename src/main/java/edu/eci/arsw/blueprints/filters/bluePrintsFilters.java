package edu.eci.arsw.blueprints.filters;

import edu.eci.arsw.blueprints.model.Blueprint;

import java.util.Set;


public interface bluePrintsFilters {
    public Blueprint filtrar(Blueprint blueprint);


    public Set<Blueprint> filterByAuthor(Set<Blueprint> blueprints);

}
