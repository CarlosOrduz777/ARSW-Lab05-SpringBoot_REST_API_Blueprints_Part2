package edu.eci.arsw.blueprints.filters;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import one.util.streamex.StreamEx;

import java.util.List;
import java.util.Set;

//@Component("bpf")
public class bluePrintsRedundance implements bluePrintsFilters{
    @Override
    public Blueprint filtrar(Blueprint blueprint) {
        List<Point> distinctPoints = null;
        for (Point p : blueprint.getPoints()) {
            distinctPoints = StreamEx.of(blueprint.getPoints()).distinct(e -> e.equals(p)).toList();
        }
        return new Blueprint(blueprint.getAuthor(),blueprint.getName(),distinctPoints);
    }

    @Override
    public Set<Blueprint> filterByAuthor(Set<Blueprint> blueprints) {
        return blueprints;
    }
}
