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
         (sort)
         (reverse))))

(defn- result-of-voltage-jumps [chain]
  (let [jumps (->> chain
                   (partition 2 1)
                   (map (fn [[a b]] (- a b)))
                   (frequencies))]
    (* (get jumps 3) (get jumps 1))))

(defn solution-part-one [input]
  (->> (parse-input input)
       (adaptor-chain)
       (result-of-voltage-jumps)))
