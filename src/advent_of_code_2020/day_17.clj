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
            [[x y 0 0] (case (get-in lines [y x])
                         \. :inactive
                         \# :active)]))))

(def- example-input
    (string/join "\n"
                 [".#."
                  "..#"
                  "###"]))

(defn neighbours-3d [[x y z w]]
  (for [dx (range -1 2)
        dy (range -1 2)
        dz (range -1 2)
        :when (not= [0 0 0 0] [dx dy dz 0])]
    (map + [x y z w] [dx dy dz 0])))

(defn- states [f-next state]
  (iterate f-next state))

(defn- expand-area [f-neighbours state]
  (apply merge
         (for [[location status] state]
           (into {} (for [k (f-neighbours location)] [k :inactive])))))

(defn- active-neighbours [f-neighbours state location]
  (let [by-state (group-by (fn [[loc state]] state) (select-keys state (f-neighbours location)))]
    (get by-state :active)))

(defn next-state [f-neighbours curr-state]
  (into {}
        (for [[location status] (merge (expand-area f-neighbours curr-state) curr-state)]
          (let [active-count (count (active-neighbours f-neighbours curr-state location))
                new-status   (cond
                               (and (= status :active)   (<= 2 active-count 3)) :active
                               (and (= status :inactive) (= 3 active-count))    :active
                               :else                                            :inactive)]
            [location new-status]))))

(defn- states [f-neighbours initial-state]
  (iterate (partial next-state f-neighbours) initial-state))

(defn solution-part-one [input]
  (->> (nth (states neighbours-3d (parse-input input)) 6)
       (filter (fn [[location status]] (= status :active)))
       (count)))

;; Part two

(defn neighbours-4d [[x y z w]]
  (for [dx (range -1 2)
        dy (range -1 2)
        dz (range -1 2)
        dw (range -1 2)
        :when (not= [0 0 0 0] [dx dy dz dw])]
    (map + [x y z w] [dx dy dz dw])))

(defn solution-part-two [input]
  (->> (nth (states neighbours-4d (parse-input input)) 6)
       (filter (fn [[location status]] (= status :active)))
       (count)))
