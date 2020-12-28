(ns advent-of-code-2020.day-16
  (:require [advent-of-code-2020.utils :refer [parse-long]]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(def problem-input
  (string/trim (slurp (io/resource "day-16-input.txt"))))

;; Parsing

(defn- create-validator [s]
  (let [[min max] (map parse-long (string/split s #"-"))]
    (fn [x] (<= min x max))))

(defn- create-field-validator [s]
  (let [[field constraints] (string/split s #": ")
        validator           (apply some-fn (map create-validator (string/split constraints #" or ")))]
    {(keyword (string/replace field " " "-")) validator}))

(defn- parse-top [s]
  (apply merge (map create-field-validator (string/split-lines s))))

(defn- parse-middle [s]
  (let [csv (fnext (string/split-lines s))]
    (into [] (map parse-long (string/split csv #",")))))

(defn- parse-bottom [s]
  (let [lines (->> (string/split-lines s) (rest))]
    (->> lines
         (map (fn [x] (into [] (map parse-long (string/split x #",")))))
         (into []))))

(defn parse-input [input]
  (let [[top middle bottom] (string/split input #"(?m)^$\n")]
    {:field-validators (parse-top top)
     :your-ticket      (parse-middle middle)
     :nearby-tickets   (parse-bottom bottom)}))

;; Part one

(defn- valid-ticket? [field-validators ticket]
  (let [valid-for-some-field? (apply some-fn (vals field-validators))]
    (every? valid-for-some-field? ticket)))

(defn parse-and-categorise-tickets [input]
  (let [{:keys [field-validators your-ticket nearby-tickets]} (parse-input input)
        categorised-tickets (->> nearby-tickets (group-by (partial valid-ticket? field-validators)))]
    {:field-validators       field-validators
     :your-ticket            your-ticket
     :nearby-valid-tickets   (get categorised-tickets true  [])
     :nearby-invalid-tickets (get categorised-tickets false [])}))

(defn solution-part-one [input]
  (let [{:keys [field-validators nearby-invalid-tickets]} (parse-and-categorise-tickets input)
        satisfies-none? (complement (apply some-fn (vals field-validators)))]
    (->> nearby-invalid-tickets
         (flatten)
         (filter satisfies-none?)
         (apply +))))

;; Part two

(defn find-fields-which-fit-ticket-values [{:keys [field-validators your-ticket nearby-valid-tickets]}]
  (let [all-tickets     (concat [your-ticket] nearby-valid-tickets)
        idx-field-pairs (for [[field pred] field-validators
                              idx          (range (count your-ticket))
                              :when (every? pred (map (fn [x] (nth x idx)) all-tickets))]
                          [idx field])]
    (into {}
          (for [[k vs] (group-by first idx-field-pairs)]
            [k (into #{} (map last vs))]))))

(defn- remove-name [by-idx name]
  (into {}
        (for [[idx names] by-idx
              :when (not= #{name} names)]
          [idx (disj names name)])))

(defn resolve-fields
  ([by-idx]
   (resolve-fields by-idx (into [] (repeat (count (keys by-idx)) nil))))

  ([by-idx fields]
   (if (or (empty? by-idx) (every? identity fields))
     fields
     (let [idx            (first (first (sort-by (fn [[k v]] (count v)) by-idx)))
           [name & names] (get by-idx idx)]
       (if (nil? names)
         (resolve-fields (remove-name (dissoc by-idx idx) name) (assoc fields idx name))
         (or (resolve-fields (remove-name (dissoc by-idx idx) name) (assoc fields idx name))
             (resolve-fields (remove-name (dissoc by-idx idx) name) fields)))))))

(defn- product-of-departure-fields [your-ticket resolved-fields]
  (let [field-indexes (for [f resolved-fields
                            :when (string/starts-with? (name f) "departure")]
                        (.indexOf resolved-fields f))]
    (->> field-indexes
         (map (partial get your-ticket))
         (apply *))))

(defn solution-part-two [input]
  (let [parsed-input     (parse-and-categorise-tickets input)
        possible-fields  (find-fields-which-fit-ticket-values parsed-input)
        resolved-fields  (resolve-fields possible-fields)]
    (product-of-departure-fields (:your-ticket parsed-input) resolved-fields)))

