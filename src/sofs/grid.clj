(ns sofs.grid
  "Functions manipulating a sequence of sequences."
  (:require [sofs.core :refer [longest-count]]))

(defn pad
  "Takes a grid vector consisting of row vectors and pads the rows where
  necessary to make each row the same length. Defaults to padding with
  nil"
  ([grid] (pad grid nil))
  ([grid padding]
     (let [longest (longest-count grid)]
       (map #(concat % (repeat (- longest (count %)) padding))
            grid))))

(defn buffer
  "Add n levels of  padding (defaults to nil) around a grid"
  ([n grid] (buffer n nil grid))
  ([n padding grid]
     (let [longest (longest-count grid)
           new-longest (+ longest (* 2 n))
           buffer-line (repeat new-longest padding)
           buffer-lines (repeat n buffer-line)
           line-buffer (repeat n padding)]
       (concat buffer-lines
               (map #(concat line-buffer % line-buffer) grid)
               buffer-lines))))

(defn neighbourhoods
  "Takes a grid vector consisting of row vectors, and returns a vector of
  each cell plus n neighbours around it. Any 'neightbours' outside of the
  grid will default to nil if not given"
  ([n grid] (neighbourhoods nil grid))
  ([n outside grid]
     (1)))
