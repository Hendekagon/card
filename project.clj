(defproject card  "1.0"
  :dependencies
  [
    [org.clojure/clojure "1.4.0"]
  ]
  :plugins [[lein-cljsbuild "0.2.8"]]
  :source-paths ["./src/clj"]
  :cljsbuild
  {
   :crossovers [card.x]
   :builds {:dev
            {
              :source-path "./src/cljs"
              :compiler
              {
                :incremental true
                :crossover-path "cljs"
                ;:output-dir "./public/js/"
                :output-to "./public/js/card.js"
                :optimizations :advanced
                :pretty-print false
              }
            }
            }
   }
:jvm-opts ["-Xms256m" "-Xmx1g" "-XX:+UseConcMarkSweepGC"]
)