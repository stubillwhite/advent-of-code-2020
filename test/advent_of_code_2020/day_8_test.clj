(ns advent-of-code-2020.day-8-test
  (:require [advent-of-code-2020.day-8 :refer :all]
            [advent-of-code-2020.utils :refer [def-]]
            [clojure.string :as string]
            [clojure.test :refer :all]))

;; Computer

(defn- create-computer [s]
  (let [prg (parse-input (string/join "\n" s))]
    (initialise-computer prg)))

(deftest step-given-nop-then-no-operation
  (let [computer (create-computer ["nop +1"])
        expected (assoc computer
                  :ip 1
                  :trace [0])]
    (is (= expected (step computer)))))

(deftest step-given-acc-then-modifies-accumulator
  (let [computer (create-computer ["acc -3"])
        expected (assoc computer
                        :ip    1
                        :acc   -3
                        :trace [0])]
    (is (= expected (step computer)))))

(deftest step-given-jmp-then-modifies-instruction-pointer
  (let [computer (create-computer ["jmp 2"])
        expected (assoc computer
                        :ip    2
                        :trace [0])]
    (is (= expected (step computer)))))

;; Part one

(def- example-input
  (string/join "\n"
               ["nop +0"
                "acc +1"
                "jmp +4"
                "acc +3"
                "jmp -3"
                "acc -99"
                "acc +1"
                "jmp -4"
                "acc +6"]))

(deftest solution-part-one-given-example-input-then-example-result
  (is (= 5 (solution-part-one example-input))))

(deftest solution-part-one-given-problem-input-then-problem-result
  (is (= 1584 (solution-part-one problem-input))))
