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

(defn- create-initial-cache [starting-numbers]
  (into {} (map-indexed
            (fn [idx x]
              (let [turn (inc idx)]
                [x [turn turn]]))
            starting-numbers)))

(defn- update-cache [cache number turn]
  (update cache number (fn [x]
                         (if-let [[prior-spoken-turn spoken-turn] x]
                           [spoken-turn turn]
                           [turn turn]))))

(defn nth-number [starting-numbers n]
  (loop [prev  (last starting-numbers)
         turn  (inc (count starting-numbers))
         cache (create-initial-cache starting-numbers)]
    (let [[spoken-prior-turn spoken-turn] (get cache prev [turn turn])
          number                          (- spoken-turn spoken-prior-turn)]
      (if (= turn n)
        number
        (recur number
               (inc turn)
               (update-cache cache number turn))))))

(defn solution-part-one [input]
  (-> (nth-number (parse-input input) 2020)))

;; Part two
;;
;; No obvious pattern to the numbers, so just refactor to speed things up

(defn solution-part-two [input]
  (-> (nth-number (parse-input input) 30000000)))
