(ns advent-of-code-2020.day-3
  (:require [advent-of-code-2020.utils :refer [def- parse-long]]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(def problem-input
  (string/trim (slurp (io/resource "day-3-input.txt"))))

(defn- parse-input [input]
  (->> input
       (string/split-lines)
       (map vec)
       (into [])))

(defn- initial-state [forest dx dy]
  {:forest forest
   :width  (count (first forest))
   :height (count forest)
   :trees  0
   :x      0
   :y      0
   :dx     dx
   :dy     dy})

(defn- hit-tree? [{:keys [forest x y]}]
  (-> forest
      (nth y)
      (nth x)
      (= \#)))

(defn- handle-collisions [{:keys [forest height x y trees] :as state}]
  (if (< y height)
    (assoc state
           :forest (assoc-in forest [y x] (if (hit-tree? state) \X \O)) 
           :trees  (if (hit-tree? state) (inc trees) trees))
    state))

(defn- next-state [{:keys [forest width trees x y dx dy] :as state}]
  (handle-collisions (assoc state
                            :x (mod (+ x dx) width)
                            :y (+ y dy))))

(defn- states [state]
  (iterate next-state state))

(defn solution-part-one
  ([input]
   (solution-part-one input 3 1))

  ([input dx dy]
   (let [state (initial-state (parse-input input) dx dy)]
     (->> (states state)
          (take-while (fn [{:keys [height y]}] (< y height)))
          (last)
          (:trees)))))

;; Part two

(def- potential-paths
  [[1 1]
   [3 1]
   [5 1]
   [7 1]
   [1 2]])

(defn solution-part-two [input]
  (->> potential-paths
       (map (fn [[dx dy]] (solution-part-one input dx dy)))
       (reduce *)))
