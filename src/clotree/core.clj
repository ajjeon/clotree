(ns clotree.core
  (:require [clojure.data.csv :as csv])
  (:require [clojure.java.io :as io])
  (:require [ubergraph.core :as uber])
  ;; (:require [loom.graph :as lgraph])
  ;; (:require [loom.gen :as lgen])
  ;; (:require [loom.io :as lio])
  (:use [clojure.java.shell :only [sh]]))


(defn unique-ns
  [mfile]
  (let [allpath (csv/read-csv (io/reader mfile))]
    (remove nil? (map #(when (clojure.string/includes? (second %) "bivex")
                         (merge (vec (drop-last %)))) allpath))))

(def x (unique-ns "resources/topo.txt"))

(def uns (distinct (map #(first (clojure.string/split % #"/")) (flatten x))))

(def x2 (zipmap (distinct (map #(keyword (first (clojure.string/split % #"/"))) (flatten x)))
[:red :blue :green :gold :deeppink :purple :sienna]))

(map #(merge % {:color ((keyword (first (clojure.string/split (first %) #"/"))) x2)}) x)



(unique-ns "resources/topo.txt")

(uber/viz-graph (apply uber/digraph (map #(merge % {:color ((keyword (first (clojure.string/split (first %) #"/"))) x2)}) x)) {:save {:filename "output/function-tree.png" :format :png}})
