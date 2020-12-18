(ns advent-of-code-2020.day-13
  (:require [advent-of-code-2020.utils :refer [parse-long]]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(def problem-input
  (string/trim (slurp (io/resource "day-13-input.txt"))))

(defn- parse-timetable [[time buses]]
  {:time  (parse-long time)
   :buses (into {}
                (for [[s idx] (partition 2 (interleave (string/split buses #",") (range)))
                      :when (not= "x" s)]
                  [(parse-long s) idx]))})

(defn- parse-input [input]
  (->> input
       (string/split-lines)
       (parse-timetable)))

(defn- waiting-times [{:keys [time buses]}]
  (map (fn [x] [x (- x (mod time x))]) (keys buses)))

(defn solution-part-one [input]
  (->> (parse-input input)
       (waiting-times)
       (apply min-key fnext)
       (apply *)))

;; Part two

(defn- find-matching-times [{:keys [curr-time step buses] :as state}]
  (if (empty? buses)
    state
    (let [matching-curr-time (first (filter (fn [[bus offset]] (zero? (mod (+ curr-time offset) bus))) buses))]
      (if (nil? matching-curr-time)
        (recur (update state :curr-time + step))
        (recur (-> state
                   (assoc  :buses (filter (partial not= matching-curr-time) buses))
                   (update :step * (first matching-curr-time))))))))

(defn solution-part-two [input]
  (let [{:keys [buses]} (parse-input input)
        result          (find-matching-times {:curr-time 0 :step 1 :buses buses})]
    (:curr-time result)))
