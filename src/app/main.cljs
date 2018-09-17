
(ns app.main
  (:require (app.lib :as lib)))

;;;
;; Any live cell with fewer than two live neighbors dies, as if by under population.
;; Any live cell with two or three live neighbors lives on to the next generation.
;; Any live cell with more than three live neighbors dies, as if by overpopulation.
;; Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
;;;

;;;
;; Live:
;; if (< x 2) dead
;; if (<= 2 x 3) live
;; if (> x 3) dead
;;
;; Dead:
;; if (= x 3) live
;;;

(def blinker
  "A simple pattern to play with."
  [[0 0 0 0 0]
   [0 0 0 0 0]
   [0 1 1 1 0]
   [0 0 0 0 0]
   [0 0 0 0 0]])


(defn grid-coordinates
  "All the coordinates for a given grid"
  [grid]
  (let [X (count (get grid 0))
        Y (count grid)]
    (for [x (range X)
         y (range Y)]
      [x y])))

#_(grid-coordinates [[1 1] [1 1]])

(defn alive-cells
  "Convert full grid to just a list of alive cells (coordinates)."
  [grid]
  (let [grid-coordinates (grid-coordinates grid)
        alive? (fn [[x, y]] (= 1 (get-in grid [x y])))]
    (filter alive? grid-coordinates)))

#_(livers blinker)

(defn render
  "Pretty print a grid as a table. A row under a row."    
  [grid]
  (reduce #(str %1 "\n" %2) grid))

(comment
  (println (render blinker)))

;;;
;;; The main programming challenge here is to translate from
;;; the current grid state to the next step's state.
;;;
;;; To apply the simple rules of the game, we should know two things
;;; for each cell: state (alive or dead) and count of neighbors.
;;;

(defn neighbors
  "Given a cell coordinates, return coordinates of it's neighbors.
  - Only non-negative coordinates.
  - Not the cell itself."
  [x y]
  (for [x' [(dec x) x (inc x)]
        y' [(dec y) y (inc y)] :when (and
                                      (not= [x' y'] [x y])
                                      (not (neg? x'))
                                      (not (neg? y')))]
    [x' y']))

;; TODO make a spec:
;;   1 input coordinates is non-negative
;;   2 result does not contain the input
;;   3 result does not contain negative numbers
#_(= (neighbors 4 4) '([3 3] [3 4] [3 5] [5 3] [5 4] [5 5] [4 3] [4 5]))
#_(= (neighbors 0 0) '([1 0] [1 1] [0 1]))


(defn zero-grid
  "Given a grid, make a grid of the same size, but of only zeroes."
  [grid]
  (map #(map (constantly 0) %) grid))

#_(zero-grid blinker)
#_(= (zero-grid [[1 1] [1 0]]) [[0 0] [0 0]])

(defn count-neighbors
  "Replace each cell with the count of it's neighbors.
    
  Starting with the `grid` of zeroes,
    for each `cell`, which are alive:
    for each `neighbor`:
    inc the count by 1
         
  That is, each alive cell visits each of its neighbors."
  [grid]
  (let [zero-grid (zero-grid grid)
        alive-cells (alive-cells grid)
        neighbors-visits (reduce concat (map neighbors alive-cells))
        do-visit (fn [x y] (update-in neighbor-grid [x y] inc))]
    (reduce do-visit neighbors-visits zero-grid)
    neighbors-visits))

(count-neighbors blinker)

(defn prepare-next-step
  "Prepares data to decide the next step.
   That is transform each cell to a tuple `[alive neighbors-count]`"
  [grid]
  ;; 
  (let []))

(defn next-step
  "Calculates the next state of grid.
   The naive approach is to create another table with tuples [state, neighbors];
   and then apply the rules to each cell."
  [grid]
  ;; 1 convert cells to [state, neighbors] tuples.

  (let [grid' grid]
    )

  ;;   Loop over cells. For alive cells increase the neighbor counter
  ;;   for each of their neighbors.
  )


;;; The next lines are the original example.

(def a 1)

(defonce b 2)

(defn main! []
  (println "[main]: loading"))

(defn reload! []
  (println "[main] reloaded lib:" lib/c lib/d)
  (println "[main] reloaded:" a b))

(main!)

