(ns sofs.grid
  "Functions manipulating a sequence of sequences as a grid."
  (:require [sofs.core :refer [longest-count]]))

(defn make-constant-grid
 "Creates an m row by n col grid as a sequence of sequence rows with all cells
 containing the same value. Default cell content is nil"
 ([m n] (make-constant-grid m n nil))
 ([m n cell] (repeat m (repeat n cell))))

(defn make-sequence-grid
  "Creates an m by n grid populating it from the supplied sequence. If the
  sequence terminates before finishing the grid then the rest of the cells
  are populated with a constant value that defaults to nil"
  ([m n s] (make-sequence-grid m n nil s))
  ([m n cell s]
   (take m (partition n (concat s (repeat cell))))))

(defn pad-to-grid
  "Takes a (possibly uneven) sequence consisting of row sequences and pads the
  rows where necessary to make each row the same length. Defaults to padding
  with nil"
  ([grid] (pad-to-grid grid nil))
  ([grid padding]
     (let [longest (longest-count grid)
           pad (repeat longest padding)]
       (map #(take longest (concat % pad)))
            grid)))

(defn buffer
  "Add n levels of padding (defaults to nil) around a grid"
  ([grid n] (buffer n nil grid))
  ([n padding grid]
     (let [longest (longest-count grid)
           new-longest (+ longest (* 2 n))
           buffer-line (repeat new-longest padding)
           buffer-lines (repeat n buffer-line)
           line-buffer (repeat n padding)]
       (concat buffer-lines
               (map #(concat line-buffer % line-buffer) grid)
               buffer-lines))))

(defn sub-grids
  "Takes a grid sequence consisting of row sequences, and returns a
  sequence of all sub-grids of size n each cell plus n neighbours around it. Any 'neightbours' outside of the
  grid will default to nil if not given"
  ([grid n] (sub-grids n nil grid))
  ([n outside grid]
     (1)))
