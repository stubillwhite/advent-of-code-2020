(ns advent-of-code-2020.day-16
  (:require [advent-of-code-2020.utils :refer [parse-long]]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(def problem-input
  (string/trim (slurp (io/resource "day-16-input.txt"))))

(defn- create-predicate [s]
  (let [[min max] (map parse-long (string/split s #"-"))]
    (fn [x] (<= min x max))))

(defn- create-field [s]
  (let [[field constraints] (string/split s #": ")
        validator           (apply some-fn (map create-predicate (string/split constraints #" or ")))]
    {(keyword field) validator}))

(defn- parse-top [s]
  (apply merge (map create-field (string/split-lines s))))

(defn- parse-bottom [s]
  (let [lines (->> (string/split-lines s) (rest))]
    (->> lines
         (map (fn [x] (into [] (map parse-long (string/split x #",")))))
         (into []))))

(defn- parse-input [input]
  (let [[top middle bottom] (string/split input #"(?m)^$\n")]
    {:fields         (parse-top top)
     :nearby-tickets (parse-bottom bottom)}))

(defn solution-part-one [input]
  (let [{:keys [fields nearby-tickets]} (parse-input input)
        satisfies-none?                 (complement (apply some-fn (vals fields)))]
    (->> nearby-tickets
         (flatten)
         (filter satisfies-none?)
         (apply +))))



