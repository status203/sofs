(ns sofs.core-test
  (:require [clojure.test :refer [are deftest is]]
            [sofs.core :as sut]))

(deftest length-of-longest-row
  (are [grid expected]
       (= expected (sut/longest-count grid))
    []                  0
    [[]]                0
    [[1]]               1
    [[1] [2]]           1
    [[1 2] [3]]         2
    [[1] [3 4]]         2))

(deftest length-of-shortest-row
  (are [grid expected]
       (= expected (sut/shortest-count grid))
    []                  0
    [[]]                0
    [[1]]               1
    [[1] [2]]           1
    [[1 2] [3]]         1
    [[1] [3 4]]         1))

(deftest longest-row
  (are [grid expected]
       (= expected (sut/longest grid))
    []                  nil
    [[]]                []
    [[1]]               [1]
    [[1] [2]]           [1]
    [[1 2] [3]]         [1 2]
    [[1] [3 4]]         [3 4]))

(deftest shortest-row
  (are [grid expected]
       (= expected (sut/shortest grid))
    []                  nil
    [[]]                []
    [[1]]               [1]
    [[1] [2]]           [1]
    [[1 2] [3]]         [3]
    [[1] [3 4]]         [1]))

(deftest single-sofs-mapping
  (is (= [[2] [3 4 5] [6 7]] (sut/map inc [[1] [2 3 4] [5 6]]))))

(deftest two-sofs-mapping
  (is (= [[7] [10 12 12] [10 6]] (sut/map * [[1] [2 3 4] [5 6]] [[7 6] [5 4 3] [2 1]]))))

(deftest three-sofs-mapping
  (is (= [[33]] (sut/map #(+ (/ %1 %2) %3) [[60]] [[2]] [[3]]))))

(deftest three-sofs-mapping-again
  (is (= [["33 a"]] (sut/map #(str (+ (/ %1 %2) %3) " " %4) [[60]] [[2]] [[3]] [[\a]]))))

(deftest reducing-without-initial-values
  (is (= 25 (sut/reduce / - [[60 2 1] [12 3] [1]]))))

(deftest reducing-with-initial-values
  (is (= 321 (sut/reduce * 2 + 7 [[60 2 1] [12 3] [1]]))))

