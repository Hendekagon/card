(ns card.ui
  "Javascript interop for displaying the the card with HTML5 canvas"
  (:require
    [card.core :as card]
  )
)

(defn create-image-data [context w h] (. context createImageData w h))

(defn put-image-data!
  ([context image-data x y] (. context putImageData image-data x y))
  ([context image-data] (put-image-data! context image-data 0 0))
)

(defn context
  "Make a context and image-data from the given canvas element"
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
  "Do the function f at regular intervals and send the results to g, starting with x"
  ([g f x] (. js/window setTimeout
             (fn [x]
               (g x)
               (thread* g f (f x))) 100 x))
)

(defn pixel!
  "set the given pixel xy in image-data to rgba"
  ([image-data xy rgba] (pixel! (.-data image-data) (.-width image-data) (.-height image-data) xy rgba))
  ([data w h [x y] rgba]
    (doall (map
      (fn [component offset]
        (aset data (+ (* 4 (+ x (* y w))) offset) component))
      rgba (range 4)
    ))
  )
)

(defn pixels!
  "set the given points in image-data to rgba"
  [image-data points rgba]
  (doseq
    [xy points]
    (pixel! image-data xy rgba)
  )
  image-data
)

(defn scale [xy] (map (comp int (partial * 512)) xy))

(defn make-ui
  ([] (make-ui (context) ))
  ([c] (make-ui (:context c) (:image-data c) ))
  ([context image-data]
    (thread*
      (fn [points]
        (pixels! image-data (map scale points) [(+ 200 (rand-int 55)) (+ 200 (rand-int 55)) (+ 200 (rand-int 55)) 255])
        (put-image-data! context image-data)
      )
      (fn [points]
        (take 63
          (card/sierpinskis
            (map (fn [p] (map (comp (partial + 0.5) (partial * 0.5)) p)) (card/polygon 6))
            0.6
            (last points)
          )
        )
      )
      [[(rand) (rand)]]
    )
  )
)

(set! (.-onload js/window) (fn [e] (make-ui)))