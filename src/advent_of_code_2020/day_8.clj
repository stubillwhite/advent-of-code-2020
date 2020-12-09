(ns advent-of-code-2020.day-8
  (:require [advent-of-code-2020.utils :refer [parse-long]]
            [clojure.java.io :as io]
            [clojure.string :as string]
            [taoensso.timbre :as timbre]))

(timbre/refer-timbre)

;; To enable debugging
;; (timbre/set-level! (keyword :debug))

;; Computer

(defn initialise-computer
  "Returns an initialised computer with the specified program."
  [prg]
  {:prg   (into [] prg)
   :ip    0
   :acc   0
   :trace []})

(defn- current-instruction [{:keys [ip prg]}]
  (get prg ip))

;; Instruction set

(defn- instruction-type [computer]
  (let [[instr arg] (current-instruction computer)]
    instr))

(ns-unmap *ns* 'execute-instruction)
(defmulti execute-instruction instruction-type)

(defmethod execute-instruction "nop" [{:keys [ip] :as computer}]
  (assoc computer
         :ip (inc ip)))

(defmethod execute-instruction "acc" [{:keys [ip acc] :as computer}]
  (let [[instr arg] (current-instruction computer)]
    (assoc computer
           :ip  (inc ip)
           :acc (+ acc arg))))

(defmethod execute-instruction "jmp" [{:keys [ip acc] :as computer}]
  (let [[instr arg] (current-instruction computer)]
    (assoc computer
           :ip  (+ ip arg))))

;; Execution

(defn- take-while-inclusive [f coll]
  (let [[x & xs] coll]
    (if-not (f x)
      (take 1 coll)
      (cons x (lazy-seq (take-while-inclusive f xs))))))

(defn step
  "Returns the computer state after executing the current instruction."
  [{:keys [ip prg] :as computer}]
  (let [traced-computer (update computer :trace conj ip)]
    (debug (format "Executing instruction %s %s" (current-instruction computer) (dissoc computer :prg)))
    (execute-instruction traced-computer)))

(defn- step-program-until [computer f]
  (take-while-inclusive (complement f) (iterate step computer)))

;; Part one

(def problem-input
  (string/trim (slurp (io/resource "day-8-input.txt"))))

(defn- parse-line [s]
  (let [[instr arg] (string/split s #" ")]
    [instr (parse-long arg)]))

(defn parse-input [input]
  (->> input
       (string/split-lines)
       (map parse-line)
       (into [])))

(defn- trace-contains-duplicates [{:keys [trace]}]
  (and (not (empty? trace))
       (not (apply distinct? trace))))

(defn solution-part-one [input]
  (let [computer (initialise-computer (parse-input input))]
    (-> (step-program-until computer trace-contains-duplicates)
        (butlast)
        (last)
        (:acc))))

