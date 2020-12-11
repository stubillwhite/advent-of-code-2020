(ns advent-of-code-2020.day-11
  (:require [advent-of-code-2020.utils :refer [parse-long]]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(def problem-input
  (string/trim (slurp (io/resource "day-11-input.txt"))))

(defn- from-symbol [ch]
  (case ch
    \L :empty-seat
    \# :occupied-seat
    \. :floor))

(defn- index-by-coordinate [lines]
  (into {}
        (for [y (range (count lines))
              x (range (count (first lines)))]
          [[x y] (from-symbol (get-in lines [y x]))])))

(defn parse-input [input]
  (->> input
       (string/split-lines)
       (index-by-coordinate)))

(defn- occupied-adjacent-seats [seating-plan [x y]]
  (apply +
         (for [dy (range -1 2)
               dx (range -1 2)
               :when (not= [0 0] [dx dy])]
           (if (= :occupied-seat (get seating-plan [(+ x dx) (+ y dy)])) 1 0))))

(defn- new-state-of-seat [seating-plan location]
  (let [curr-state          (get seating-plan location)
        occupied-neighbours (occupied-adjacent-seats seating-plan location)]
    (cond
      (and (= :empty-seat curr-state)
           (zero? occupied-neighbours))  :occupied-seat

      (and (= :occupied-seat curr-state)
           (>= occupied-neighbours 4))   :empty-seat

      :else curr-state)))

(defn states [initial-state]
  (let [new-state (fn [curr] (into {} (for [loc (keys curr)] [loc (new-state-of-seat curr loc)])))]
    (iterate new-state initial-state)))

(defn- consume-until-unchanging [xs]
  (reduce
   (fn [curr next]
     (if (= curr next)
       (reduced curr)
       next))
   xs))

(defn- total-occupied-seats [plan]
  (->> (vals plan)
       (filter (fn [x] (= :occupied-seat x)))
       (count)))

(defn solution-part-one [input]
  (->> (states (parse-input input))
       (consume-until-unchanging)
       (total-occupied-seats)))

