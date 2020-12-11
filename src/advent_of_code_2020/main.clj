(ns advent-of-code-2020.main
  (:gen-class)
  (:require [advent-of-code-2020.day-11 :refer [parse-input problem-input states line-of-sight-neighbours-rules]]
            [clojure.string :as string]))

;; Dirty visualisation of day 11

(defn- render-seating-plan [seating-plan]
  (let [width  (inc (apply max (map first (keys seating-plan))))
        height (inc (apply max (map fnext (keys seating-plan))))]
    (->> (for [y (range height)
               x (range width)]
           (case (get seating-plan [x y])
             :empty-seat    " "
             :occupied-seat "■"
             :floor         " "))
         (partition width)
         (map string/join)
         (string/join "\n"))))

(defn- render [seating-plan]
  (let [height (inc (apply max (map fnext (keys seating-plan))))]
    (println (render-seating-plan seating-plan)
             (format "\033[%dA" height))
    (Thread/sleep 50)))

(defn -main [& args]
  (loop [[curr & rest] (states (parse-input problem-input) line-of-sight-neighbours-rules)]
    (if (= curr (first rest))
      nil
      (do
        (render curr)
        (recur rest)))))

