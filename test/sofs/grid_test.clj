(ns sofs.grid-test
  (:require [sofs.grid :refer :all])
  (:require [midje.sweet :refer :all]))

(tabular
 (facts "about making nil grids"
        (make-constant-grid ?m ?n) => ?result)

 ?m ?n ?result
 1  1  [[nil]]
 1  3  [[nil nil nil]]
 3  1  [[nil] [nil] [nil]]
 2  3  [[nil nil nil] [nil nil nil]])

(tabular
 (facts "about making non-nil constant grids"
        (make-constant-grid ?m ?n :c) => ?result)

 ?m ?n ?result
 1  1  [[:c]]
 1  3  [[:c :c :c]]
 3  1  [[:c] [:c] [:c]]
 2  3  [[:c :c :c] [:c :c :c]])

#_(tabular
 (facts "about making grids from sequences"
        ()))

#_(tabular
 (facts "about padding uneven sequences of sequences to make a grid"))
