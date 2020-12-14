(ns advent-of-code-2020.day-12
  (:require [advent-of-code-2020.utils :refer [parse-long]]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(def problem-input
  (string/trim (slurp (io/resource "day-12-input.txt"))))

(defn parse-line [s]
  {:dir (case (first s)
          \F :forward
          \N :north
          \E :east
          \S :south
          \W :west
          \L :left
          \R :right)
   :units (parse-long (apply str (rest s)))})

(defn- parse-input [input]
  (->> input
       (string/split-lines)
       (map parse-line)
       (into [])))

(defn- create-ship []
  {:facing :east
   :loc    [0 0]})

(defn- move-in-direction [{:keys [loc] :as ship} dir units]
  (assoc ship :loc
         (let [[x y] loc]
           (case dir
             :north [x (- y units)]
             :east  [(+ x units) y]
             :south [x (+ y units)]
             :west  [(- x units) y]))))

(defn- change-facing [ship facing]
  (assoc ship :facing facing))

(defn- facing-to-degrees [facing]
  (get {:north 0
        :east  90
        :south 180
        :west  270} facing))

(defn- degrees-to-facing [degrees]
  (get {0   :north
        90  :east
        180 :south
        270 :west} degrees))

(defn- turn [{:keys [facing] :as ship} dir degrees]
  (if (= dir :left)
    (turn ship :right (- degrees))
    (assoc ship :facing (-> facing
                            (facing-to-degrees)
                            (+ degrees)
                            (mod 360)
                            (degrees-to-facing)))))

(defn- process-instruction [{:keys [facing] :as ship} {:keys [dir units]}]
  (cond
    (= dir :forward)                (move-in-direction ship facing units)
    (contains? #{:left :right} dir) (turn ship dir units)
    :else                           (move-in-direction ship dir units)))

(defn- distance-from-origin [{:keys [loc]}]
  (let [[x y] loc] (+ (Math/abs x) (Math/abs y))))

(defn solution-part-one [input]
  (distance-from-origin
   (reduce process-instruction (create-ship) (parse-input input))))
