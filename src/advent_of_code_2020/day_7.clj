(ns advent-of-code-2020.day-7
  (:require [advent-of-code-2020.utils :refer [parse-long]]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(def problem-input
  (string/trim (slurp (io/resource "day-7-input.txt"))))

(defn- to-keyword [s]
  (keyword (string/replace s " " "-")))

(defn- parse-line [s]
  (let [[outer-bag s-contents] (string/split s #" bags contain ")
        contents               (re-seq #"(?:(\d+) ([^.,]+) bag)+" s-contents)]
    {(to-keyword outer-bag)
     (into {} (for [[_ n inner-bag] contents] [(to-keyword inner-bag) (parse-long n)]))}))

(defn parse-input [input]
  (->> input
       (string/split-lines)
       (map parse-line)
       (apply merge)))

(defn- all-bags-within [rules bag]
  (let [contents (get rules bag)]
    (into #{}
          (concat (keys contents)
                  (mapcat (partial all-bags-within rules) (keys contents))))))

(defn solution-part-one [input]
  (let [rules    (parse-input input)
        all-bags (keys rules)]
    (->> all-bags
         (map (partial all-bags-within rules))
         (filter (fn [x] (contains? x :shiny-gold)))
         (count))))

;; Part two

(defn- count-of-all-bags-within [rules bag]
  (let [contents (get rules bag)]
    (->> contents
         (map (fn [[color n]] (* n (inc (count-of-all-bags-within rules color)))))
         (apply +))))

(defn solution-part-two [input]
  (let [rules (parse-input input)]
    (count-of-all-bags-within rules :shiny-gold)))
