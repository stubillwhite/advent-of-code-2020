(ns advent-of-code-2020.day-6
  (:require [clojure.java.io :as io]
            [clojure.set :refer [intersection]]
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

;; Part two

(defn- questions-all-answered-yes [answers]
  (->> answers
       (map (fn [x] (into #{} x)))
       (apply intersection)))

(defn solution-part-two [input]
  (->> (parse-input input)
       (map questions-all-answered-yes)
       (map count)
       (apply +)))



