(ns card.core)

(def pi2 (* 2 Math/PI))

(defn towards [r x y] (map + x (map (comp (partial * r)  -) y x)))

(defn sierpinski [p r] (fn [x] (towards r x (rand-nth p))))

(defn sierpinskis
  ([p r x] (iterate (sierpinski p r) x))
  ([p r] (sierpinskis p r (repeatedly (count (first p)) rand)))
)

(defn polygon [n] (map (fn [a] [(Math/cos a) (Math/sin a)] ) (range 0 pi2 (/ pi2 n))))
