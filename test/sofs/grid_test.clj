(ns sofs.grid-test
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
        (ragged->grid ?ragged :c) => ?grid)

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
        (sequence->grid ?s ?m ?n) => ?grid)

 ?m ?n ?s       ?grid
 0  0  (range)  []
 1  1  []       [[nil]]
 1  1  (range)  [[0]]
 2  3  (range)  [[0 1 2] [3 4 5]]
 2  3  [0 1]    [[0 1 nil] [nil nil nil]])

(tabular
 (facts "about making grids from sequences with custom exhaustion cell default"
        (sequence->grid ?s ?m ?n :c) => ?grid)

 ?m ?n ?s       ?grid
 0  0  (range)  []
 1  1  []       [[:c]]
 1  1  (range)  [[0]]
 2  3  (range)  [[0 1 2] [3 4 5]]
 2  3  [0 1]    [[0 1 :c] [:c :c :c]])

(tabular
 (facts "about buffering grids"
        (buffer ?grid ?r ?c) => ?result)

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
 (facts "about sub-grids"
        (sub-grids ?grid ?r ?c) => ?result)

 ?r ?c ?grid                        ?result
 1  1  (sequence->grid (range) 1 1) [[[[0]]]]
 2  3  (sequence->grid (range) 3 4) [[ [[0 1 2] [4 5 6]] [[1 2 3] [5 6 7]] ]
                                       [[[4 5 6] [8 9 10]] [[5 6 7] [9 10 11]] ]])

(tabular
 (facts "about neighbours"
        (neighbours ?grid ?r ?c) => ?result)

 ?r ?c ?grid                        ?result
 1  2  (sequence->grid (range) 3 5) [[ [[0 1 2 3 4] [5 6 7 8 9] [10 11 12 13 14]] ]]
 0  0  (sequence->grid (range) 2 2) [[ [[0]] [[1]] ]
                                     [ [[2]] [[3]] ]]
 1  1  (sequence->grid (range) 4 4) [[ [[ 0  1  2] [ 4  5  6] [ 8  9 10]] [[ 1  2  3] [ 5  6  7] [ 9 10 11]] ]
                                     [ [[ 4  5  6] [ 8  9 10] [12 13 14]] [[ 5  6  7] [ 9 10 11] [13 14 15]] ]])

(tabular
 (facts "about neighbours-all with default padding"
        (neighbours-all ?grid ?r ?c) => ?result)

 ?r ?c ?grid                        ?result
 0  0  []                           []
 0  1  []                           []
 1  0  []                           []
 1  1  []                           []
 0  0  [[1]]                        [[ [[1]] ]]
 0  1  [[1]]                        [[ [[nil 1 nil]] ]]
 1  0  [[1]]                        [[ [[nil] [1] [nil]] ]]
 1  1  [[1]]                        [[ [[nil nil nil] [nil 1 nil] [nil nil nil]] ]]
 1  2  (sequence->grid (range) 3 2) [[ [[nil nil nil nil nil] [nil nil 0 1 nil] [nil nil 2 3 nil]] [[nil nil nil nil nil] [nil 0 1 nil nil] [nil 2 3 nil nil]] ]
                                     [ [[nil nil 0 1 nil] [nil nil 2 3 nil] [nil nil 4 5 nil]] [[nil 0 1 nil nil] [nil 2 3 nil nil] [nil 4 5 nil nil]] ]
                                     [ [[nil nil 2 3 nil] [nil nil 4 5 nil] [nil nil nil nil nil]] [[nil 2 3 nil nil] [nil 4 5 nil nil] [nil nil nil nil nil]] ]]
 )
