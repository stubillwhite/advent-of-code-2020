(ns advent-of-code-2020.day-2
  (:require [advent-of-code-2020.utils :refer [parse-long]]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(def problem-input
  (string/trim (slurp (io/resource "day-2-input.txt"))))

(defn- parse-line [line]
  (let [regex                 #"(\d+)-(\d+) (\S): (\S+)"
        [[_ a b ch password]] (re-seq regex line)]
    {:a        (parse-long a)
     :b        (parse-long b)
     :ch       (.charAt ch 0)
     :password password}))

(defn- parse-input [input]
  (->> input
       (string/split-lines)
       (map parse-line)
       (into [])))

(defn- is-valid-sled-rental-password? [{:keys [a b ch password]}]
  (let [actual (get (frequencies password) ch 0)]
    (<= a actual b)))

(defn solution-part-one [input]
  (->> (parse-input input)
       (filter is-valid-sled-rental-password?)
       (count)))

;; Part two

(defn- is-valid-toboggan-rental-password? [{:keys [a b ch password]}]
  (let [matches?    (fn [n] (= ch (.charAt password (dec n))))
        match-freqs (frequencies [(matches? a) (matches? b)])]
    (= (get match-freqs true) 1)))

(defn solution-part-two [input]
  (->> (parse-input input)
       (filter is-valid-toboggan-rental-password?)
       (count)))

