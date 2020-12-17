(ns advent-of-code-2020.day-13
  (:require [advent-of-code-2020.utils :refer [parse-long]]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(def problem-input
  (string/trim (slurp (io/resource "day-13-input.txt"))))

(defn- parse-timetable [[time buses]]
  {:time  (parse-long time)
   :buses (->> (string/split buses #",")
               (filter (partial not= "x"))
               (map parse-long)
               (into []))})

(defn- parse-input [input]
  (->> input
       (string/split-lines)
       (parse-timetable)))

(defn- waiting-times [{:keys [time buses]}]
  (map (fn [x] [x (- x (mod time x))]) buses))

(defn solution-part-one [input]
  (->> (parse-input input)
       (waiting-times)
       (apply min-key fnext)
       (apply *)))

