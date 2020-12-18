(ns advent-of-code-2020.day-14
  (:require [advent-of-code-2020.utils :refer [parse-long]]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(def problem-input
  (string/trim (slurp (io/resource "day-14-input.txt"))))

(defn- parse-instruction [s]
  (let [match (first (re-seq #"(mask|mem)(?:\[(\d+)\])? = (\S+)" s))
        [_ instr addr value] match]
    (case instr
      "mask" {:instr :mask :value value}
      "mem"  {:instr :mem  :addr (parse-long addr) :value (parse-long value)})))

(defn- parse-input [input]
  (->> input
       (string/split-lines)
       (map parse-instruction)
       (into [])))

(defn- from-binary-string [x]
  (Long/parseLong x 2))

(defn- set-bitmask [s]
  (let [mask (from-binary-string (string/replace s #"(X|0)" "0"))]
    (partial bit-or mask)))

(defn- clear-bitmask [s]
  (let [mask (from-binary-string (string/replace s #"(X|1)" "1"))]
    (partial bit-and mask)))

(defn- create-mask-function [value]
  (comp (set-bitmask value) (clear-bitmask value)))

(defn- process-instruction [{:keys [f-mask] :as state} {:keys [instr addr value]}]
  (case instr
    :mask (assoc-in state [:f-mask] (create-mask-function value))
    :mem  (assoc-in state [:memory addr] (f-mask value))))

(defn- execute-program [instrs]
  (reduce process-instruction
          {:memory {}
           :f-mask identity}
          instrs))

(defn solution-part-one [input]
  (->> (parse-input input)
       (execute-program)
       (:memory)
       (vals)
       (apply +)))


