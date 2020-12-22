(ns advent-of-code-2020.day-15
  (:require [advent-of-code-2020.utils :refer [parse-long]]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(def problem-input
  "0,12,6,13,20,1,17")

(defn- parse-input [input]
  (->> (string/split input #",")
       (map parse-long)
       (into [])))

(defn- number-seq [starting-numbers]
  (let [starting-length (count starting-numbers)
        next-number     (fn [{:keys [last-number turn numbers cache] :as state}]
                          (let [[x & xs]    numbers
                                new-turn    (inc turn)
                                new-number  (cond
                                              (< turn starting-length)      x
                                              (contains? cache last-number) (- turn (get cache last-number))
                                              :else                         0)]
                            (-> state
                                (assoc :last-number new-number)
                                (assoc :turn        new-turn) 
                               (assoc :numbers     xs)
                                (assoc :cache       (assoc cache last-number turn)))))]
    (->> (iterate next-number
                  {:last-number nil
                   :turn        0
                   :numbers     (cycle starting-numbers)
                   :cache       {}})
         (map :last-number))))

(defn solution-part-one [input]
  (-> (number-seq (parse-input input))
      (nth 2020)))
