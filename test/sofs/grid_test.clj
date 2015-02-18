(ns sofs.grid-test
  (:refer-clojure :exclude [partition])
  (:require [sofs.grid :refer :all])
  (:require [midje.sweet :refer :all]))

(tabular
 (facts "about making ragged sofs into a grid"
        (ragged->grid ?ragged) => ?grid)

 ?ragged        ?grid
 [[]]           [[]]
 [[1]]          [[1]]
 [[1 2] [3] []] [[1 2] [3 nil] [nil nil]]
 [[3] [] [1 2]] [[3 nil] [nil nil] [1 2]])

(tabular
 (facts "about making ragged sofs into a grid with custom default cell"
        (ragged->grid :c ?ragged) => ?grid)

 ?ragged        ?grid
 [[]]           [[]]
 [[1]]          [[1]]
 [[1 2] [3] []] [[1 2] [3 :c] [:c :c]]
 [[3] [] [1 2]] [[3 :c] [:c :c] [1 2]])


(tabular
 (facts "about making nil constant grids"
        (constant->grid ?m ?n) => ?grid)

 ?m ?n ?grid
 1  1  [[nil]]
 1  3  [[nil nil nil]]
 3  1  [[nil] [nil] [nil]]
 2  3  [[nil nil nil] [nil nil nil]])

(tabular
 (facts "about making non-nil constant grids"
        (constant->grid ?m ?n :c) => ?grid)

 ?m ?n ?grid
 1  1  [[:c]]
 1  3  [[:c :c :c]]
 3  1  [[:c] [:c] [:c]]
 2  3  [[:c :c :c] [:c :c :c]])

(tabular
 (facts "about making grids from sequences"
        (sequence->grid ?m ?n ?s) => ?grid)

 ?m ?n ?s       ?grid
 0  0  (range)  []
 1  1  []       [[nil]]
 1  1  (range)  [[0]]
 2  3  (range)  [[0 1 2] [3 4 5]]
 2  3  [0 1]    [[0 1 nil] [nil nil nil]])

(tabular
 (facts "about making grids from sequences with custom exhaustion cell default"
        (sequence->grid ?m ?n :c ?s) => ?grid)

 ?m ?n ?s       ?grid
 0  0  (range)  []
 1  1  []       [[:c]]
 1  1  (range)  [[0]]
 2  3  (range)  [[0 1 2] [3 4 5]]
 2  3  [0 1]    [[0 1 :c] [:c :c :c]])

(tabular
 (facts "about buffering grids"
        (buffer ?r ?c ?grid) => ?result)

 ?r ?c ?grid             ?result
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
                          [nil nil nil nil nil nil nil]])

(tabular
 (facts "about partition"
        (partition ?r 1 ?c 1 ?grid) => ?result)

 ?r ?c ?grid                            ?result
 1  1  (sequence->grid 1 1 (range)) [[[[0]]]]
 2  3  (sequence->grid 3 4 (range)) [[ [[0 1 2] [4 5 6]] [[1 2 3] [5 6 7]] ]
                                         [ [[4 5 6] [8 9 10]] [[5 6 7] [9 10 11]] ]])

(tabular
 (facts "about neighbours"
        (neighbours ?r ?c ?grid) => ?result)

 ?r ?c ?grid                        ?result
 1  2  (sequence->grid 3 5 (range)) [[ [[0 1 2 3 4] [5 6 7 8 9] [10 11 12 13 14]] ]]
 0  0  (sequence->grid 2 2 (range)) [[ [[0]] [[1]] ]
                                     [ [[2]] [[3]] ]]
 1  1  (sequence->grid 4 4 (range)) [[ [[ 0  1  2] [ 4  5  6] [ 8  9 10]] [[ 1  2  3] [ 5  6  7] [ 9 10 11]] ]
                                     [ [[ 4  5  6] [ 8  9 10] [12 13 14]] [[ 5  6  7] [ 9 10 11] [13 14 15]] ]])

(tabular
 (facts "about neighbours-all with default padding"
        (neighbours-all ?r ?c ?grid) => ?result)

 ?r ?c ?grid                        ?result
 0  0  []                           []
 0  1  []                           []
 1  0  []                           []
 1  1  []                           []
 0  0  [[1]]                        [[ [[1]] ]]
 0  1  [[1]]                        [[ [[nil 1 nil]] ]]
 1  0  [[1]]                        [[ [[nil] [1] [nil]] ]]
 1  1  [[1]]                        [[ [[nil nil nil] [nil 1 nil] [nil nil nil]] ]]
 1  2  (sequence->grid 3 2 (range)) [[ [[nil nil nil nil nil] [nil nil 0 1 nil] [nil nil 2 3 nil]] [[nil nil nil nil nil] [nil 0 1 nil nil] [nil 2 3 nil nil]] ]
                                     [ [[nil nil 0 1 nil] [nil nil 2 3 nil] [nil nil 4 5 nil]] [[nil 0 1 nil nil] [nil 2 3 nil nil] [nil 4 5 nil nil]] ]
                                     [ [[nil nil 2 3 nil] [nil nil 4 5 nil] [nil nil nil nil nil]] [[nil 2 3 nil nil] [nil 4 5 nil nil] [nil nil nil nil nil]] ]]
 )
