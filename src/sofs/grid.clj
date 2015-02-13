(ns sofs.grid
  "Functions manipulating a sequence of sequences as a grid."
  (:require [sofs.core :refer [longest-count]]))

(defn ragged->grid
  "Takes a (possibly uneven) sequence consisting of row sequences and pads the
  rows where necessary to make each row the same length. Defaults to padding
  with nil"
  ([grid] (ragged->grid grid nil))
  ([grid padding]
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
  ([s r c] (sequence->grid s r c nil))
  ([s r c cell]
   (take r (partition c (concat s (repeat cell))))))

(defn buffer
  "Add m rows of padding, and n entries of padding within each row. Padding
  defaults to nil."
  ([grid r c] (buffer grid r c nil))
  ([grid r c padding]
     (let [width (count (first grid))
           new-width (+ width (* 2 c))
           buffer-line (repeat new-width padding)
           buffer-lines (repeat r buffer-line)
           line-buffer (repeat c padding)]
       (concat buffer-lines
               (map #(concat line-buffer % line-buffer) grid)
               buffer-lines))))

(defn sub-grids
  "Takes a grid and returns a grid of all sub-grids of size r by c. Assumes
  that the grid is at least r by c to start with."
  [grid r c]
    (->> grid
    (map #(partition c 1 %))
    (partition r 1)
    (map #(apply map list %))))

(defn neighbours
  "Takes a grid and returns a grid or all subgrids that contain a cell plus
  r by c neighbours on each side. Assumes that the grid is at least 2r+1 by
  2c+1 to start with."
  [grid r c] (sub-grids grid (inc (* 2 r)) (inc (* 2 c))))

(defn neighbours-all
  "Takes a grid and returns a grid of subgrids of the cell plus neighbours;
  one subgrid for each entry in the original grid. Cells without sufficient
  neighbours will have their sub-grids padded by 'cell', by default nil."
  ([grid r c] (neighbours-all grid r c nil))
  ([grid r c cell] (neighbours (buffer grid r c cell) r c)))
