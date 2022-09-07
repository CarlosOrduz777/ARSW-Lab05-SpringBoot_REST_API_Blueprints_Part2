package edu.eci.arsw.blueprints.filters;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component("bpf")
public class bluePrintsUndersampling implements bluePrintsFilters {
    @Override
    public Blueprint filtrar(Blueprint blueprint) {
        List<Point> copyPoints = new ArrayList<>(blueprint.getPoints());

        List<Integer> pairIndexes = generatePairIndexes(copyPoints.size());
        List<Integer> unpairIndexes = generateUnpairIndexes(copyPoints.size());

        for (int i = 0; i < blueprint.getPoints().size(); i++ ) {
           if(pairIndexes.contains(i) || unpairIndexes.contains(i)){
               copyPoints.remove(blueprint.getPoints().get(i));
           }
        }
        return new Blueprint(blueprint.getAuthor(),blueprint.getName(),copyPoints);
    }

    @Override
    public Set<Blueprint> filterByAuthor(Set<Blueprint> blueprints) {
        Set<Blueprint> bpResult = new HashSet<>();
        for (Blueprint bp: blueprints){
            Blueprint bpFilter = filtrar(bp);
            bpResult.add(bpFilter);
        }
        return bpResult;
    }

    public List<Integer> generatePairIndexes(int tam){
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < tam; i+=4) {
            indexes.add(i);
        }
        if(tam%2==1 && tam >=5){
            indexes.remove(indexes.size()-1);
        }
        return indexes;
    }

    public List<Integer> generateUnpairIndexes(int tam){
        List<Integer> indexes = new ArrayList<>();
        for (int i = 3; i < tam; i+=4) {
            indexes.add(i);
        }
        return indexes;
    }
}
