(ns advent-of-code-2020.day-10
  (:require [advent-of-code-2020.utils :refer [parse-long]]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(def problem-input
  (string/trim (slurp (io/resource "day-10-input.txt"))))

(defn- parse-input [input]
  (->> input
       (string/split-lines)
       (map parse-long)
       (into [])))

(defn- adaptor-chain [adaptors]
  (let [device (->> adaptors (apply max) (+ 3))]
    (->> (concat [device 0] adaptors)
         (sort))))

(defn- result-of-voltage-jumps [chain]
  (let [jumps (->> chain
                   (partition 2 1)
                   (map (fn [[a b]] (- b a)))
                   (frequencies))]
    (* (get jumps 3) (get jumps 1))))

(defn solution-part-one [input]
  (->> (parse-input input)
       (adaptor-chain)
       (result-of-voltage-jumps)))

;; Part two

(defn- total-arrangements-of [adaptors]
  (loop [adaptors (rest adaptors)
         cache    {0 1}]
    (let [[x & xs]      adaptors
          possible-ways (for [possible-adaptor (range 1 4)] (get cache (- x possible-adaptor) 0))
          total         (apply + possible-ways)]
      (if (empty? xs)
        total
        (recur xs (assoc cache x total))))))

(defn solution-part-two [input]
  (->> (parse-input input)
       (adaptor-chain)
       (total-arrangements-of)))
