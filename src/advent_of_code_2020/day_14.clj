(ns advent-of-code-2020.day-14
  (:require [advent-of-code-2020.utils :refer [parse-long]]
            [clojure.java.io :as io]
            [clojure.set :refer [union]]
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

(defn- indexes-of [coll item]
  (for [[x idx] (map vector coll (range))
        :when (= x item)] idx)
)
(defn- create-mask [s]
  (let [rev-s (reverse s)]
    {:floating-bits (indexes-of rev-s \X)
     :set-bits      (indexes-of rev-s \1)
     :clear-bits    (indexes-of rev-s \0)}))

(defn- twiddle-bits [value f bits]
  (reduce (fn [acc x] (f acc x)) value bits))

(defn- masked-value [{:keys [floating-bits set-bits clear-bits]} value]
  (-> value
      (twiddle-bits bit-set set-bits)
      (twiddle-bits bit-clear clear-bits)))

(defn- version-one-decoder [{:keys [mask-data] :as state} {:keys [instr addr value]}]
  (case instr
    :mask (assoc-in state [:mask-data] (create-mask value))
    :mem  (assoc-in state [:memory addr] (masked-value mask-data value))))

(defn- execute-program [decoder instrs]
  (reduce decoder
          {:memory {}}
          instrs))

(defn solution-part-one [input]
  (->> (parse-input input)
       (execute-program version-one-decoder)
       (:memory)
       (vals)
       (apply +)))

;; Part two

(defn- subsets [coll]
  (loop [[x & xs] coll
         sets     #{#{}}]
    (if (nil? x)
      sets
      (recur xs
             (union sets (map (fn [a] (conj a x)) sets))))))

(defn- addresses-for [{:keys [floating-bits set-bits clear-bits]} addr]
  (let [base-value   (-> addr
                         (twiddle-bits bit-set set-bits)
                         (twiddle-bits bit-clear floating-bits))
        permutations (subsets floating-bits)]
    (map (partial twiddle-bits base-value bit-set) permutations)))

(defn- version-two-decoder [{:keys [mask-data] :as state} {:keys [instr addr value]}]
  (case instr
    :mask (assoc-in state [:mask-data] (create-mask value))
    :mem  (reduce (fn [state addr] (assoc-in state [:memory addr] value))
                  state
                  (addresses-for mask-data addr))))

(defn solution-part-two [input]
  (->> (parse-input input)
       (execute-program version-two-decoder)
       (:memory)
       (vals)
       (apply +)))

