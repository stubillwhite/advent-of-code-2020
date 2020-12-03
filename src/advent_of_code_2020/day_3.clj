(ns advent-of-code-2020.day-3
  (:require [advent-of-code-2020.utils :refer [parse-long]]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(def problem-input
  (string/trim (slurp (io/resource "day-3-input.txt"))))

(defn- parse-input [input]
  (->> input
       (string/split-lines)
       (map vec)
       (into [])))

(defn- initial-state [forest dx dy]
  {:forest forest
   :width  (count (first forest))
   :height (count forest)
   :trees  0
   :x      0
   :y      0
   :dx     dx
   :dy     dy})

(defn- tree? [forest x y]
  (-> forest
      (nth y [])
      (nth x [])
      (= \#)))

(defn- next-state [{:keys [forest width trees x y dx dy] :as state}]
  (let [new-x     (mod (+ x dx) width)
        new-y     (+ y dy)
        hit-tree? (tree? forest new-x new-y)]
    (assoc state
           :trees  (if hit-tree? (inc trees) trees)
           :forest (assoc-in forest [new-y new-x] (if hit-tree? \X \O)) 
           :x      new-x
           :y      new-y)))

(defn- states [state]
  (iterate next-state state))

(defn solution-part-one [input]
  (let [state (initial-state (parse-input input) 3 1)]
    (->> (states state)
         (take-while (fn [{:keys [height y]}] (< y height)))
         (last)
         (:trees))))
