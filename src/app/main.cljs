
(ns app.main
  (:require (app.lib :as lib)))

;;;
;; Any live cell with fewer than two live neighbors dies, as if by under population.
;; Any live cell with two or three live neighbors lives on to the next generation.
;; Any live cell with more than three live neighbors dies, as if by overpopulation.
;; Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
;;
;;
;; Briefly:
;;
;; Live:
;; if (< x 2) dead
;; if (<= 2 x 3) live
;; if (> x 3) dead
;;
;; Dead:
;; if (= x 3) live"
;;
;;
;; And finally:
;; Alive if and only if
;; (or (and alive? (= x 2))
;;     (= x 3))
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

(defn all->alive
  "Convert full grid to just a list of alive cells (coordinates)."
  [grid]
  (let [grid-coordinates (grid-coordinates grid)
        alive? (fn [[x, y]] (= 1 (get-in grid [x y])))]
    (filter alive? grid-coordinates)))

(defn alive->all
  "Convert a list of alive cells to a full grid."
  [livers grid]
  (let [mark-alive (fn [grid [x y]] (assoc-in grid [x y] 1))]
    (reduce mark-alive (zero-grid grid) livers)))

#_(= (all->alive blinker)
     '([2 1] [2 2] [2 3]))

(comment
  (= (alive->all (all->alive blinker) blinker)
     blinker))

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
  [[x y]]
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
#_(= (set (neighbors [4 4])) (set '([3 3] [3 4] [3 5] [5 3] [5 4] [5 5] [4 3] [4 5])))
#_(= (set (neighbors [0 0])) (set '([1 0] [1 1] [0 1])))
#_(map neighbors '([2 1] [2 2] [2 3]))


(defn zero-grid
  "Given a grid, make a grid of the same size, but of only zeroes.
  It's important to return a vector of vectors, no sequences."
  [grid]
  (let [seq00 (map #(map (constantly 0) %) grid)]
    (vec (map vec seq00))))

#_(zero-grid blinker)
#_(= (zero-grid [[1 1] [1 0]]) [[0 0] [0 0]])

(defn neighbors-grid
  "Replace each cell with the count of it's neighbors.
    
  Starting with the `grid` of zeroes,
    for each `cell`, which is alive:
    for each `neighbor`:
    inc count by 1
         
  This is like each alive cell visits all of its neighbors."
  [grid]
  (let [neighbors-visits (reduce concat (map neighbors (all->alive grid)))]
    (reduce (fn [acc [x y]] (update-in acc [x y] inc))
            (zero-grid grid)
            neighbors-visits)))

(comment
  (println (neighbors-grid blinker)))

elet [x fn next-ste (all->alive blinker))]



p
  "Calculates the next state of grid.
   Alive when and only when:
   (or (and alive? (= x 2))
       (= x 3))"
  [grid]
  (let [neighbors-grid (neighbors-grid grid)]
    ;; Return only alive ones.
    (for [[x y] (grid-coordinates grid)
          
          :let [alive? (= 1 (get-in grid [x y]))
                neighbors (get-in neighbors-grid [x y])]
          
          :when (or (and alive? (= neighbors 2))
                    (= neighbors 3))]
      [x y])))

#_(next-step blinker)

(defn next-step'
  [grid]
  (alive->all (next-step grid) grid))

;;; The blinker must blink!
;;; TEST
(= blinker
   (-> blinker
       next-step'
       next-step'))

(defn main! []
  (println "[main]: loading"))

(defn reload! []
  (println
   (next-step blinker)))

(main!)

