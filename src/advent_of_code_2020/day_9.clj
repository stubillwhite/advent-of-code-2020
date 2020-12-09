(ns advent-of-code-2020.day-9
  (:require [advent-of-code-2020.utils :refer [parse-long]]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(def problem-input
  (string/trim (slurp (io/resource "day-9-input.txt"))))

(defn- parse-input [input]
  (->> input
       (string/split-lines)
       (map parse-long)
       (into [])))

;; Borrowed from day-1
(defn- combinations-of
  [n items]
  (cond
    (zero? n)           [[]]
    (= n (count items)) [items]
    :else               (lazy-cat
                         (map (partial cons (first items))
                              (combinations-of (dec n) (rest items)))
                         (combinations-of n (rest items)))))

(defn- is-valid? [[n & preceding]]
  (not (empty? (->> (combinations-of 2 preceding)
                    (filter (fn [xs] (= n (apply + xs))))))))

(defn solution-part-one [input length]
  (->> (parse-input input)
       (partition (inc length) 1)
       (map reverse)
       (drop-while is-valid?)
       (first)
       (first)))

