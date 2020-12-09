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

;; Part two

(defn- take-until-totalling [total xs]
  (loop [remaining    (rest xs)
         subseq       [(first xs)]
         subseq-total (first xs)]
    (cond
      (= total subseq-total) subseq
      (< total subseq-total) nil
      :else                  (recur (rest remaining)
                                    (conj subseq (first remaining))
                                    (+ subseq-total (first remaining))))))

(defn contiguous-sequence-totalling [total xs]
  (loop [xs xs]
    (if-let [range (take-until-totalling total xs)]
      range
      (recur (rest xs)))))

(defn- sum-of-smallest-and-largest [xs]
  (+ (apply min xs) (apply max xs)))

(defn solution-part-two [input length]
  (let [total (solution-part-one input length)]
    (->> (parse-input input)
         (contiguous-sequence-totalling total)
         (sum-of-smallest-and-largest))))

