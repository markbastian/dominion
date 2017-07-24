(ns dominion.actions)

(defmulti action :name)

(defmethod action :cellar [m]
  (-> m
      (update :actions inc)))

(defmethod action :village [m]
  (-> m
      (update :actions + 2)))