(ns card.plot.test
  (:require
    [card.core :as card]
    [incanter.core :as inc]
    [incanter.charts :as charts]
  )
)


(defn test-sierpinski
  "show a plot of the sierpinski function with sliders to vary its parameters"
  []
  (inc/view
  (charts/dynamic-scatter-plot
    [r (range 0 1 0.1) s (range 3 11)]
    (take 1024 (card/sierpinskis (card/polygon s) r))
    )
  )
)