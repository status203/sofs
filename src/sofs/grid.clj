(ns sofs.grid
  "Functions manipulating a sequence of sequences as a grid."
  (:refer-clojure :exclude [partition])
  (:require [sofs.core :refer [longest-count]])
  (refer-clojure :exclude [partition map]))

(defn ragged->grid
  "Takes a (possibly uneven) sequence consisting of row sequences and pads the
  rows where necessary to make each row the same length. Defaults to padding
  with nil"
  ([grid] (ragged->grid nil grid))
  ([padding grid]
     (let [longest (longest-count grid)
           pad (repeat longest padding)]
       (map #(take longest (concat % pad))
            grid))))

(defn constant->grid
 "Creates a grid with r rows of c columns with all cells
 containing the same value. Default cell content is nil"
 ([r c] (constant->grid r c nil))
 ([r c cell] (repeat r (repeat c cell))))

(defn sequence->grid
  "Creates a grid with r rows of c columns, populating it from the supplied
  sequence. If the sequence terminates before finishing the grid then the rest
  of the cells are populated with a constant value that defaults to nil"
  ([r c s] (sequence->grid r c nil s))
  ([r c cell s]
   (take r (clojure.core/partition c (concat s (repeat cell))))))

(defn buffer
  "Add m rows of padding, and n entries of padding within each row. Padding
  defaults to nil."
  ([r c grid] (buffer r c nil grid))
  ([r c padding grid]
     (let [width (count (first grid))
           new-width (+ width (* 2 c))
           buffer-line (repeat new-width padding)
           buffer-lines (repeat r buffer-line)
           line-buffer (repeat c padding)]
       (concat buffer-lines
               (map #(concat line-buffer % line-buffer) grid)
               buffer-lines))))

(defn partition
  "Takes a grid and returns a grid of all sub-grids of size r by c. Assumes
  that the grid is at least r by c to start with."
  ([r c grid] (partition r r c c grid))
  ([r r-step c c-step grid]
    (->> grid
         (clojure.core/map #(clojure.core/partition c c-step %))
         (clojure.core/partition r r-step)
         (clojure.core/map #(apply clojure.core/map list %)))))

(defn neighbours
  "Takes a grid and returns a grid or all subgrids that contain a cell plus
  r by c neighbours on each side. Assumes that the grid is at least 2r+1 by
  2c+1 to start with."
  [r c grid] (partition (inc (* 2 r)) 1 (inc (* 2 c)) 1 grid))

(defn neighbours-all
  "Takes a grid and returns a grid of subgrids of the cell plus neighbours;
  one subgrid for each entry in the original grid. Cells without sufficient
  neighbours will have their sub-grids padded by 'cell', by default nil."
  ([r c grid] (neighbours-all r c nil grid))
  ([r c cell grid] (neighbours r c (buffer r c cell grid))))
