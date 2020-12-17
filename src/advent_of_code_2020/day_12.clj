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

(defn- create-ship [waypoint-location]
  {:loc      [0 0]
   :waypoint waypoint-location})

(defn- rotate-waypoint [{:keys [waypoint] :as ship} dir degrees]
  (if (= dir :left)
    (rotate-waypoint ship :right (- degrees))
    (let [theta     (/ (* degrees Math/PI) 180)
          cos-theta (Math/cos theta)
          sin-theta (Math/sin theta)
          [x y]     waypoint]
      (assoc ship :waypoint
             [(Math/round (- (* x cos-theta) (* y sin-theta)))
              (Math/round (+ (* y cos-theta) (* x sin-theta)))]))))

(defn- dir-to-waypoint [dir]
  (dir {:north [0 -1]
        :east  [1  0]
        :south [0  1]
        :west  [-1 0]}))

(defn- move-toward-waypoint [{:keys [loc] :as ship} waypoint units]
  (assoc ship :loc
         (map + (map (partial * units) waypoint) loc)))

(defn- move-ship [ship dir units]
  (move-toward-waypoint ship (dir-to-waypoint dir) units))

(defn- process-instruction [{:keys [waypoint] :as ship} {:keys [dir units] :as instr}]
  (cond
    (= dir :forward)                (move-toward-waypoint ship waypoint units)
    (contains? #{:left :right} dir) (rotate-waypoint ship dir units)
    :else                           (move-ship ship dir units)))

(defn- distance-from-origin [{:keys [loc]}]
  (let [[x y] loc] (+ (Math/abs x) (Math/abs y))))

(defn solution-part-one [input]
  (distance-from-origin
   (reduce process-instruction (create-ship [1 0]) (parse-input input))))

;; Part two

(defn- move-waypoint [{:keys [waypoint] :as ship} dir units]
  (assoc ship :waypoint
         (map + (map (partial * units) (dir-to-waypoint dir)) waypoint)))

(defn- process-instructions-for-waypoint [{:keys [waypoint] :as ship} {:keys [dir units] :as instr}]
  (cond
    (= dir :forward)                (move-toward-waypoint ship waypoint units)
    (contains? #{:left :right} dir) (rotate-waypoint ship dir units)
    :else                           (move-waypoint ship dir units)))

(defn solution-part-two [input]
  (distance-from-origin
   (reduce process-instructions-for-waypoint (create-ship [10 -1]) (parse-input input))))
