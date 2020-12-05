(ns advent-of-code-2020.day-5
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

;; There's a hack for this -- it's just a binary number
;;
;; The number of seats is a power of two, and each time the range is halving, so we can just treat "take the lower half"
;; as 0, and "take the upper half" as one at each point:
;; 
;;  F = 0 
;;  B = 1 
;;
;;  6 3 1
;;  4 2 6 8 4 2 1
;;  -------------
;;  F B F B B F F
;;  0 1 0 1 1 0 0 = 32 + 8 + 4 = 44
;;
;; The seat in the row is encoded the same way:
;;
;;  R = 1
;;  L = 0
;;
;;  4 2 1
;;  R L R 
;;  1 0 1 = 4 + 1 = 5
;;
;; The final answer is row * 8 + seat, and 8 is the same as a left-shift of 3:
;;
;;   44 * 8 + 5 === (44 << 3) + 5
;; 
;; So this is just 10-bit binary number
;; 
;;  5 2 1
;;  1 5 2 6 3 1
;;  2 6 8 4 2 6 8 4 2 1
;;  -------------------
;;  F B F B B F F R L R
;;  0 1 0 1 1 0 0 1 0 1 = 256 + 64 + 32 + 4 + 1 = 357
;;  
;;  F = 0 
;;  B = 1 
;;  R = 1
;;  L = 0

(def problem-input
  (string/trim (slurp (io/resource "day-5-input.txt"))))

(defn- parse-input [input]
  (->> input
       (string/split-lines)
       (into [])))

(defn seat-number [boarding-pass]
  (-> boarding-pass
      (string/replace #"(F|L)" "0")
      (string/replace #"(B|R)" "1")
      (Integer/parseInt 2)))

(defn solution-part-one [input]
  (->> (parse-input input)
       (map seat-number)
       (apply max)))
