(ns advent-of-code-2020.day-17-test
  (:require [advent-of-code-2020.day-17 :refer :all]
            [advent-of-code-2020.utils :refer [def-]]
            [clojure.string :as string]
            [clojure.test :refer :all]))

(def- example-input
    (string/join "\n"
                 [".#."
                  "..#"
                  "###"]))

(defn- to-display [state]
  (let [coords-of-axis (fn [n] (distinct (sort (map #(nth % n) (keys state)))))
        xs             (coords-of-axis 0) 
        ys             (coords-of-axis 1) 
        zs             (coords-of-axis 2)]
    (string/join "\n\n"
                 (for [z zs]
                   (str "z=" z "\n"
                        (->> (for [y ys
                                   x xs]
                               (case (get state
                                          [x y z 0])
                                 :active   "#"
                                 :inactive "."))
                             (partition (count xs))
                             (map string/join)
                             (string/join "\n")))))))

(defn- nth-state [input n]
  (-> (iterate (partial next-state neighbours-3d) (parse-input input))
      (nth n)
      (to-display)))

(def- expected-initial-state
  (string/join "\n"
               ["z=0"
                example-input]))

(def- expected-after-one-cycle
  (string/join "\n"
               ["z=-1"
                "....."
                "....."
                ".#..."
                "...#."
                "..#.."
                ""
                "z=0"
                "....."
                "....."
                ".#.#."
                "..##."
                "..#.."
                ""
                "z=1"
                "....."
                "....."
                ".#..."
                "...#."
                "..#.."]))

(deftest next-state-given-example-input-then-example-result
  (testing "initial state"
    (let [expected-initial-state (str "z=0\n" example-input)]
      (is (= expected-initial-state (nth-state example-input 0)))))
  (testing "after one cycle"
    (is (= expected-after-one-cycle (nth-state example-input 1)))))

(deftest ^:slow solution-part-one-given-example-input-then-example-result
  (is (= 112 (solution-part-one example-input))))

(deftest ^:slow solution-part-one-given-problem-input-then-correct-result
  (is (= 267 (solution-part-one problem-input))))

(deftest ^:slow solution-part-two-given-example-input-then-example-result
  (is (= 848 (solution-part-two example-input))))

(deftest ^:slow solution-part-two-given-problem-input-then-problem-result
  (is (= 1812 (solution-part-two problem-input))))
