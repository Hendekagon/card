(ns card.x.core)

(defn towards [r x y] (map + x (map (comp (partial * r)  -) y x)))

(defn sierpinski [p] (fn [x] (towards 0.6 x (rand-nth p))))

(defn sierpinskis [p x] (iterate (sierpinski p) x) )
