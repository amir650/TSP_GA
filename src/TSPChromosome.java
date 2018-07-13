import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TSPChromosome {

    private final List<TSPGene> chromosome;
    private final double distance;

    public double getDistance() {
        return this.distance;
    }

    private TSPChromosome(final List<TSPGene> chromosome) {
        this.chromosome = Collections.unmodifiableList(chromosome);
        this.distance = calculateDistance();
    }

    static TSPChromosome create(final TSPGene[] points) {
        final List<TSPGene> genes = Arrays.asList(Arrays.copyOf(points, points.length));
        Collections.shuffle(genes);
        return new TSPChromosome(genes);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for(final TSPGene gene : this.chromosome) {
            builder.append(gene.toString()).append((" : "));
        }
        return builder.toString();
    }

    List<TSPGene> getChromosome() {
        return this.chromosome;
    }

    double calculateDistance() {
        double total = 0.0f;
        for(int i = 0; i < this.chromosome.size() - 1; i++) {
            total += this.chromosome.get(i).distance(this.chromosome.get(i+1));
        }
        return total;
    }

    TSPChromosome[] crossOver(final TSPChromosome other) {

        final List<TSPGene>[] myDNA = TSPUtils.split(this.chromosome);
        final List<TSPGene>[] otherDNA = TSPUtils.split(other.getChromosome());

        final List<TSPGene> firstCrossOver = new ArrayList<>(myDNA[0]);

        for(TSPGene gene : otherDNA[0]) {
            if(!firstCrossOver.contains(gene)) {
                firstCrossOver.add(gene);
            }
        }

        for(TSPGene gene : otherDNA[1]) {
            if(!firstCrossOver.contains(gene)) {
                firstCrossOver.add(gene);
            }
        }

        final List<TSPGene> secondCrossOver = new ArrayList<>(otherDNA[1]);

        for(TSPGene gene : myDNA[0]) {
            if(!secondCrossOver.contains(gene)) {
                secondCrossOver.add(gene);
            }
        }

        for(TSPGene gene : myDNA[1]) {
            if(!secondCrossOver.contains(gene)) {
                secondCrossOver.add(gene);
            }
        }

        if(firstCrossOver.size() != TSPUtils.CITIES.length ||
           secondCrossOver.size() != TSPUtils.CITIES.length) {
            throw new RuntimeException("oops!");
        }

        return new TSPChromosome[] {
                new TSPChromosome(firstCrossOver),
                new TSPChromosome(secondCrossOver)
        };
    }

    TSPChromosome mutate() {
        final List<TSPGene> copy = new ArrayList<>(this.chromosome);
        int indexA = TSPUtils.randomIndex(copy.size());
        int indexB = TSPUtils.randomIndex(copy.size());
        while(indexA == indexB) {
            indexA = TSPUtils.randomIndex(copy.size());
            indexB = TSPUtils.randomIndex(copy.size());
        }
        Collections.swap(copy, indexA, indexB);
        return new TSPChromosome(copy);
    }


}
