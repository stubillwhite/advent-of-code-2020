(ns advent-of-code-2020.day-4
  (:require [advent-of-code-2020.utils :refer [def- parse-long]]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(def problem-input
  (string/trim (slurp (io/resource "day-4-input.txt"))))

(defn- parse-record [record]
  (let [passport-regex #"([a-z]+):(\S+)"
        matches        (re-seq passport-regex record)]
    (into {} (for [[_ k v] matches] [k v]))))

(defn parse-input [input]
  (->> (string/split input #"(?m)^\n")
       (map parse-record)
       (into [])))

(def- expected-fields
  ["byr"
   "iyr"
   "eyr"
   "hgt"
   "hcl"
   "ecl"
   "pid"
   ;; "cid" -- treat as optional
   ])

(defn- valid-passport? [record]
  (every? (fn [k] (contains? record k)) expected-fields))

(defn solution-part-one [input]
  (->> input
       (parse-input)
       (filter valid-passport?)
       (count)))
