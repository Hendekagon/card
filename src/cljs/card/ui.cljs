(ns card.ui
  (:require
    [card.x.core :as card]
  )
)

(def pi2 (* 2 Math/PI))

(defn create-image-data [context w h] (. context createImageData w h))

(defn context
  ([] (context (. js/document getElementById "canvas")))
  ([canvas] (context canvas (. canvas getContext "2d")) )
  ([canvas context]
    {
      :canvas canvas :context context
      :w (.-width canvas) :h (.-height canvas)
      :image-data (create-image-data context (.-width canvas) (.-height canvas))
    }
  )
)

(defn thread*
  ([g f x] (. js/window setTimeout
             (fn [x]
               (g x)
               (thread* g f (f x))) 10 x))
)

(defn pixel!
  ([image-data xy rgba] (pixel! (.-data image-data) (.-width image-data) (.-height image-data) xy rgba))
  ([data w h [x y] rgba]
    (doall (map
      (fn [component offset]
        (aset data (+ (* 4 (+ x (* y w))) offset) component))
      rgba (range 4)
    ))
  )
)

(defn pixels! [image-data points rgba]
  (doseq
    [xy points]
    (pixel! image-data xy rgba)
  )
)

(defn scale [xy] (map (comp int (partial * 512)) xy))

(defn polygon [n] (map (fn [a] [(Math/cos a) (Math/sin a)] ) (range 0 pi2 (/ pi2 n))))

(defn make-ui
  ([] (make-ui (context) ))
  ([c] (make-ui (:context c) (:image-data c) ))
  ([context image-data]
    (thread*
      (fn [points]
          (pixels! image-data (map scale points) [(+ 200 (rand-int 55)) (+ 200 (rand-int 55)) (+ 200 (rand-int 55)) 255])
        (. context putImageData image-data 0 0))
      (fn [points]
        (take 63
          (card/sierpinskis
            (map (fn [p] (map (comp (partial + 0.5) (partial * 0.5)) p)) (polygon 6))
            (last points)
          )
        )
      )
      [[(rand) (rand)]]
    )
  )
)

(set! (.-onload js/window) (fn [e] (make-ui)))