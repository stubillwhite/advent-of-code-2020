(ns advent-of-code-2020.day-17
  (:require [advent-of-code-2020.utils :refer [def- parse-long]]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(def problem-input
  (string/trim (slurp (io/resource "day-17-input.txt"))))

(defn parse-input [input]
  (->> input
       (string/split-lines)
       (map parse-long)
       (into [])))

(defn parse-input [input]
  (let [lines  (string/split-lines input)
        height (count lines)
        width  (count (first lines))]
    (into {}
          (for [y (range height)
                x (range width)]
            [[x y 0] (case (get-in lines [y x])
                       \. :inactive
                       \# :active)]))))

(def- example-input
    (string/join "\n"
                 [".#."
                  "..#"
                  "###"]))

(defn- neighbours [[x y z]]
  (for [dx (range -1 2)
        dy (range -1 2)
        dz (range -1 2)
        :when (not= [0 0 0] [dx dy dz])]
    (map + [x y z] [dx dy dz])))

(defn- states [f-next state]
  (iterate f-next state))

(defn- expand-area [state]
  (apply merge
         (for [[location status] state]
           (into {} (for [k (neighbours location)] [k :inactive])))))

(defn- active-neighbours [state location]
  (let [by-state (group-by (fn [[loc state]] state) (select-keys state (neighbours location)))]
    (get by-state :active)))

(defn next-state [curr-state]
  (into {}
        (for [[location status] (merge (expand-area curr-state) curr-state)]
          (let [active-count (count (active-neighbours curr-state location))
                new-status   (cond
                               (and (= status :active)   (<= 2 active-count 3)) :active
                               (and (= status :inactive) (= 3 active-count))    :active
                               :else                                            :inactive)]
            [location new-status]))))

(defn- states [initial-state]
  (iterate next-state initial-state))

(defn solution-part-one [input]
  (->> (nth (states (parse-input input)) 6)
       (filter (fn [[location status]] (= status :active)))
       (count)))
