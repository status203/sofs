(ns sofs.grid-test
  (:refer-clojure :exclude [partition])
  (:require [clojure.test :refer [are deftest is]]
            [sofs.grid :as sut]))

(deftest ragged-sofs->grid
  (are [ragged expected]
       (= expected (sut/ragged->grid ragged))
    [[]]           [[]]
    [[1]]          [[1]]
    [[1 2] [3] []] [[1 2] [3 nil] [nil nil]]
    [[3] [] [1 2]] [[3 nil] [nil nil] [1 2]]))

(deftest ragged-sofs-and-default->grid
  (are [ragged expected]
       (= expected (sut/ragged->grid :c ragged))
    [[]]           [[]]
    [[1]]          [[1]]
    [[1 2] [3] []] [[1 2] [3 :c] [:c :c]]
    [[3] [] [1 2]] [[3 :c] [:c :c] [1 2]]))

(deftest nil-constant-grids
  (are [m n expected]
       (= expected (sut/constant->grid m n))
    1  1  [[nil]]
    1  3  [[nil nil nil]]
    3  1  [[nil] [nil] [nil]]
    2  3  [[nil nil nil] [nil nil nil]]))

(deftest non-nil-constant-grids
  (are [m n expected]
       (= expected (sut/constant->grid m n :c))
    1  1  [[:c]]
    1  3  [[:c :c :c]]
    3  1  [[:c] [:c] [:c]]
    2  3  [[:c :c :c] [:c :c :c]]))

(deftest sequence->grid
  (are [m n s expected]
       (= expected (sut/sequence->grid m n s))
    0  0  (range)  []
    1  1  []       [[nil]]
    1  1  (range)  [[0]]
    2  3  (range)  [[0 1 2] [3 4 5]]
    2  3  [0 1]    [[0 1 nil] [nil nil nil]]))

(deftest custom-default-sequence->grid
  (are [m n s expected]
       (= expected (sut/sequence->grid m n :c s))
    0  0  (range)  []
    1  1  []       [[:c]]
    1  1  (range)  [[0]]
    2  3  (range)  [[0 1 2] [3 4 5]]
    2  3  [0 1]    [[0 1 :c] [:c :c :c]]))

(deftest buffering-grids
  (are [r c grid expected]
       (= expected (sut/buffer r c grid))
    0  0  []                []
    0  1  []                []
    1  0  []                [[] []]
    1  1  []                [[nil nil] [nil nil]]
    0  0  [[]]              [[]]
    0  1  [[]]              [[nil nil]]
    1  0  [[]]              [[] [] []]
    1  1  [[]]              [[nil nil] [nil nil] [nil nil]]
    0  0  [[1]]             [[1]]
    0  1  [[1]]             [[nil 1 nil]]
    1  0  [[1]]             [[nil] [1] [nil]]
    1  1  [[1]]             [[nil nil nil] [nil 1 nil] [nil nil nil]]
    0  0  [[1 2 3] [4 5 6]] [[1 2 3] [4 5 6]]
    1  2  [[1 2 3] [4 5 6]] [[nil nil nil nil nil nil nil]
                             [nil nil 1   2   3   nil nil]
                             [nil nil 4   5   6   nil nil]
                             [nil nil nil nil nil nil nil]]))

(deftest partitioning-grids
  (are [r c grid expected]
       (= expected (sut/partition r 1 c 1 grid))
    1  1  (sut/sequence->grid 1 1 (range)) [[[[0]]]]
    2  3  (sut/sequence->grid 3 4 (range)) [[[[0 1 2] [4 5 6]]  [[1 2 3] [5 6 7]]]
                                        [[[4 5 6] [8 9 10]] [[5 6 7] [9 10 11]]]]))

(deftest neighbours-tests
  (are [r c grid expected]
       (= expected (sut/neighbours r c grid))
    1  2  (sut/sequence->grid 3 5 (range)) [[[[0 1 2 3 4] [5 6 7 8 9] [10 11 12 13 14]]]]
    0  0  (sut/sequence->grid 2 2 (range)) [[[[0]] [[1]]]
                                            [[[2]] [[3]]]]
    1  1  (sut/sequence->grid 4 4 (range)) [[[[0  1  2] [4  5  6] [8  9 10]] [[1  2  3] [5  6  7] [9 10 11]]]
                                            [[[4  5  6] [8  9 10] [12 13 14]] [[5  6  7] [9 10 11] [13 14 15]]]]))

(deftest neighbours-all-tests
  (are [r c grid expected]
       (= expected (sut/neighbours-all r c grid))
    0  0  []                           []
    0  1  []                           []
    1  0  []                           []
    1  1  []                           []
    0  0  [[1]]                        [[[[1]]]]
    0  1  [[1]]                        [[[[nil 1 nil]]]]
    1  0  [[1]]                        [[[[nil] [1] [nil]]]]
    1  1  [[1]]                        [[[[nil nil nil] [nil 1 nil] [nil nil nil]]]]
    1  2  (sut/sequence->grid 3 2 (range)) [[[[nil nil nil nil nil] [nil nil 0 1 nil] [nil nil 2 3 nil]] [[nil nil nil nil nil] [nil 0 1 nil nil] [nil 2 3 nil nil]]]
                                        [[[nil nil 0 1 nil] [nil nil 2 3 nil] [nil nil 4 5 nil]] [[nil 0 1 nil nil] [nil 2 3 nil nil] [nil 4 5 nil nil]]]
                                        [[[nil nil 2 3 nil] [nil nil 4 5 nil] [nil nil nil nil nil]] [[nil 2 3 nil nil] [nil 4 5 nil nil] [nil nil nil nil nil]]]]))


