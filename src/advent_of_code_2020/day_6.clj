(ns advent-of-code-2020.day-6
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(def problem-input
  (string/trim (slurp (io/resource "day-6-input.txt"))))

(defn- parse-line [s]
  (string/split s #"\n"))

(defn- parse-input [input]
  (->> (string/split input #"(?m)^\n")
       (map parse-line)
       (into [])))

(defn- questions-answered [answers]
  (->> (string/join answers)
       (seq)
       (into #{})
       (count)))

(defn solution-part-one [input]
  (->> (parse-input input)
       (map questions-answered)
       (apply +)))





