(ns advent-of-code-2020.day-7-test
  (:require [advent-of-code-2020.day-7 :refer :all]
            [advent-of-code-2020.utils :refer [def-]]
            [clojure.string :as string]
            [clojure.test :refer :all]))

(def- example-input-one
  (string/join "\n"
               ["light red bags contain 1 bright white bag, 2 muted yellow bags."
                "dark orange bags contain 3 bright white bags, 4 muted yellow bags."
                "bright white bags contain 1 shiny gold bag."
                "muted yellow bags contain 2 shiny gold bags, 9 faded blue bags."
                "shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags."
                "dark olive bags contain 3 faded blue bags, 4 dotted black bags."
                "vibrant plum bags contain 5 faded blue bags, 6 dotted black bags."
                "faded blue bags contain no other bags."
                "dotted black bags contain no other bags."]))

(def- example-rules
  {:light-red    {:bright-white 1 :muted-yellow 2}
   :dark-orange  {:bright-white 3 :muted-yellow 4}
   :bright-white {:shiny-gold   1}
   :muted-yellow {:shiny-gold   2 :faded-blue   9}
   :shiny-gold   {:dark-olive   1 :vibrant-plum 2}
   :dark-olive   {:faded-blue   3 :dotted-black 4}
   :vibrant-plum {:faded-blue   5 :dotted-black 6}          
   :faded-blue   {}
   :dotted-black {}})

(deftest parse-input-given-example-input-then-map
  (is (= example-rules (parse-input example-input-one))))

(deftest solution-part-one-given-example-input-then-example-result
  (is (= 4 (solution-part-one example-input-one))))

(deftest ^:slow solution-part-one-given-problem-input-then-correct-result
  (is (= 265 (solution-part-one problem-input))))

(def- example-input-two
  (string/join "\n"
               ["shiny gold bags contain 2 dark red bags."
                "dark red bags contain 2 dark orange bags."
                "dark orange bags contain 2 dark yellow bags."
                "dark yellow bags contain 2 dark green bags."
                "dark green bags contain 2 dark blue bags."
                "dark blue bags contain 2 dark violet bags."
                "dark violet bags contain no other bags."]))

(deftest solution-part-two-given-example-input-one-then-example-result
  (is (= 32 (solution-part-two example-input-one))))

(deftest solution-part-two-given-example-input-two-then-example-result
  (is (= 126 (solution-part-two example-input-two))))

(deftest solution-part-two-given-problem-input-then-correct-result
  (is (= 14177 (solution-part-two problem-input))))
