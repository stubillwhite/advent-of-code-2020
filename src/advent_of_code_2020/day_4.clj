(ns advent-of-code-2020.day-4
  (:require [advent-of-code-2020.utils :refer [parse-long]]
            [clojure.java.io :as io]
            [clojure.spec.alpha :as spec]
            [clojure.string :as string]))

(def problem-input
  (string/trim (slurp (io/resource "day-4-input.txt"))))

(defn- parse-record [record]
  (let [passport-regex #"([a-z]+):(\S+)"
        matches        (re-seq passport-regex record)]
    (into {} (for [[_ k v] matches] [(keyword k) v]))))

(defn parse-input [input]
  (->> (string/split input #"(?m)^\n")
       (map parse-record)
       (into [])))

(defn- has-valid-passport-fields? [record]
  (let [expected-fields [:byr :iyr :eyr :hgt :hcl :ecl :pid]]
    (every? (fn [k] (contains? record k)) expected-fields)))

(defn solution-part-one [input]
  (->> input
       (parse-input)
       (filter has-valid-passport-fields?)
       (count)))

;; Part two

(defn- height? [s]
  (when-let [[m] (re-seq #"(\d+)(in|cm)" s)]
    (let [[_ v unit] m
          value      (parse-long v)]
      (or (and (= "cm" unit) (<= 150 value 193))
          (and (= "in" unit) (<= 59  value 76))))))

(defn- year-between-inclusive [min max]
  (fn [s]
    (and (re-matches #"[0-9]{4}" s)
         (<= min (parse-long s) (inc max)))))

(defn- year? [s]
  (re-matches #"[0-9]{4}" s))

(spec/def ::byr (year-between-inclusive 1920 2002))
(spec/def ::iyr (year-between-inclusive 2010 2020))
(spec/def ::eyr (year-between-inclusive 2020 2030))
(spec/def ::hgt (spec/and string? height?))
(spec/def ::hcl (spec/and string? #(re-matches #"#[0-9a-f]{6}" %)))
(spec/def ::ecl #{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"})
(spec/def ::pid (spec/and string? #(re-matches #"[0-9]{9}" %)))

(spec/def ::passport
  (spec/keys :req-un [::byr ::iyr ::eyr ::hgt ::hcl ::ecl ::pid]))

(defn- valid-passport? [record]
  (spec/valid? ::passport record))

(defn solution-part-two [input]
  (->> input
       (parse-input)
       (filter valid-passport?)
       (count)))

