(ns advent-of-code-2020.day-1
  (:require [advent-of-code-2020.utils :refer [parse-long]]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(def problem-input
  (string/trim (slurp (io/resource "day-1-input.txt"))))

(defn- parse-input [input]
  (->> input
       (string/split-lines)
       (map parse-long)
       (into [])))

(defn combinations-of
  [n items]
  (cond
    (zero? n)           [[]]
    (= n (count items)) [items]
    :else               (lazy-cat
                         (map (partial cons (first items))
                              (combinations-of (dec n) (rest items)))
                         (combinations-of n (rest items)))))

(defn solution-part-one [input]
  (->> (combinations-of 2 (parse-input input))
       (filter (fn [[x y]] (= 2020 (+ x y))))
       (first)
       (apply *)))

;; Part two

(defn solution-part-two [input]
  (->> (combinations-of 3 (parse-input input))
       (filter (fn [[x y z]] (= 2020 (+ x y z))))
       (first)
       (apply *)))



