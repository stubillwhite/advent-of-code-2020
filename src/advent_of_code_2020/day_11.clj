(ns advent-of-code-2020.day-11
  (:require [clojure.java.io :as io]
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

(defn states [initial-state f]
  (let [new-state (fn [curr] (into {} (for [loc (keys curr)] [loc (f curr loc)])))]
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

;; Part one

(defn- occupied-adjacent-seats [seating-plan [x y]]
  (apply +
         (for [dy (range -1 2)
               dx (range -1 2)
               :when (not= [0 0] [dx dy])]
           (if (= :occupied-seat (get seating-plan [(+ x dx) (+ y dy)])) 1 0))))

(defn immediate-neighbours-rules [seating-plan location]
  (let [curr-state (get seating-plan location)
        occupied   (occupied-adjacent-seats seating-plan location)]
    (cond
      (and (= :empty-seat curr-state)
           (zero? occupied))  :occupied-seat

      (and (= :occupied-seat curr-state)
           (>= occupied 4))   :empty-seat

      :else curr-state)))

(defn solution-part-one [input]
  (->> (states (parse-input input) immediate-neighbours-rules)
       (consume-until-unchanging)
       (total-occupied-seats)))

;; Part two

(defn- first-seat-in-direction [seating-plan [x y] [dx dy]]
  (loop [x (+ x dx)
         y (+ y dy)]
    (let [contents (get seating-plan [x y])]
      (if (not= contents :floor)
        contents
        (recur (+ x dx) (+ y dy))))))

(defn- occupied-in-line-of-sight [seating-plan [x y]]
  (apply +
         (for [dy (range -1 2)
               dx (range -1 2)
               :when (not= [0 0] [dx dy])]
           (if (= :occupied-seat (first-seat-in-direction seating-plan [x y] [dx dy])) 1 0))))

(defn line-of-sight-neighbours-rules [seating-plan location]
  (let [curr-state          (get seating-plan location)
        occupied-neighbours (occupied-in-line-of-sight seating-plan location)]
    (cond
      (and (= :empty-seat curr-state)
           (zero? occupied-neighbours))  :occupied-seat

      (and (= :occupied-seat curr-state)
           (>= occupied-neighbours 5))   :empty-seat

      :else curr-state)))

(defn solution-part-two [input]
  (->> (states (parse-input input) line-of-sight-neighbours-rules)
       (consume-until-unchanging)
       (total-occupied-seats)))
