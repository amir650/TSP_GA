import java.util.Objects;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class TSPGene {

    private final int x;
    private final int y;

    TSPGene(final int x,
            final int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y+ ")";
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    double distance(final TSPGene other) {
        return sqrt(pow(getX() - other.getX(), 2) + pow(getY() - other.getY(), 2));
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final TSPGene gene = (TSPGene) o;
        return this.x == gene.x &&
                this.y == gene.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }
}
