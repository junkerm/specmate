export class Sets {
    public static union<T>(...sets: Set<T>[]): Set<T> {
        let out = new Set();
        sets.forEach( set => {
            set.forEach( elem => out.add(elem));
        });
        return out;
    }

    public static intersection<T>(...sets: Set<T>[]): Set<T> {
        let out = new Set();
        if (sets.length == 0) {
            return out;
        }
        let mainSet = sets[0];
        mainSet.forEach( elem => {
            if (sets.every(set => set.has(elem))) {
                out.add(elem);
            }
        });
        return out;
    }

    public static difference<T>(setA: Set<T>, setB: Set<T>): Set<T> {
        let out = new Set();
        setA.forEach(elem => {
            if (!setB.has(elem)) {
                out.add(elem);
            }
        });
        return out;
    }
}
